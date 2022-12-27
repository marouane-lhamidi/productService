package com.example.productservice.common.config.impl;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.productservice.common.CurrentUser;
import com.example.productservice.common.config.Config;
import com.example.productservice.dto.token.Roles;
import com.example.productservice.dto.token.TokenDTO;
import com.example.productservice.common.exception.HttpUnauthorizedException;
import com.example.productservice.common.utiles.Constants;
import com.example.productservice.common.utiles.Utiles;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.keycloak.RSATokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequestScope
public class ConfigImpl implements Config {

    public static final String REALM_ACCESS = "realm_access";
    public static final String PROTOCOL_OPENID_CONNECT_CERTS = "/protocol/openid-connect/certs";
    @Autowired
    CurrentUser currentUser;

    public static  Certificate certificate;

    @Value("${path.privatekey}")
    private String privatekeyPath;

    @Value("${key.password}")
    private String password;

    @Value("${key.alias}")
    private String alias;

    @Value("${keycloak.serverUrls}")
    private String SERVER_URLS;

    @Value("${keycloak.serverUrl}")
    private String SERVER_URL;



    @Override
    public HashMap<String, String> verifySignaturekeycloakAndLoadPayload(TokenDTO tokenDTO) throws Exception {
        var accessWithoutBearer = tokenDTO.getAccessToken().replace("Bearer","").trim();
        try {

            var iss = Utiles.decodeJwt(accessWithoutBearer)
                    .get(Constants.ISS)
                    .split("/");
            if(iss.length == 0) throw new HttpUnauthorizedException("Invalid AccesToken");
            var realm= iss[iss.length -1];
            var serverUrl = (iss[0].startsWith(Constants.HTTPS))
                    ? SERVER_URLS : SERVER_URL;
            RSAPublicKey publicKey = loadPublicKey(JWT.decode(accessWithoutBearer));
            RSATokenVerifier.verifyToken(
                    accessWithoutBearer,
                    publicKey,
                    serverUrl
                            + Constants.REALM + realm,
                    true,
                    true );
        }catch (Exception ex){
            throw new HttpUnauthorizedException(ex.getMessage());
        }
        var base64Url = new Base64(true);
        var payload = new HashMap<String, String>();
        String[] jwtTokenValues = accessWithoutBearer.split("\\.");
        JsonObject jsonPayloadObject = parseBase64(base64Url,jwtTokenValues[1]);
        jsonPayloadObject.entrySet().stream().forEach(element->{
            if(!element.getValue().isJsonNull() && !element.getKey().equals("realm_access")  && !element.getKey().equals("resource_access") ){
                payload.put(element.getKey(),element.getValue().getAsString() );
            }else {
                payload.put(element.getKey(),null );
            }
        });

        currentUser.setAccesToken(tokenDTO.getAccessToken());
        currentUser.setEmail(payload.get("email"));
        return payload;
    }

    @Override
    public void checkroles(String[] paramsNames, Object[] args,String[] eligiblRoles) throws HttpUnauthorizedException {

        int idx = Arrays.asList(paramsNames).indexOf(Constants.ACCESSTOKEN);
        if (idx < 0 || Objects.isNull(args[idx]))
            throw new HttpUnauthorizedException("Invalid Access Token");
        var accessToken = args[idx].toString();
        var accessWithoutBearer = accessToken.replace("Bearer","").trim();
        String[] chunks   = accessWithoutBearer.split("\\.");

        var json = new Gson();

        var  realmRoles = json.fromJson(
                String.valueOf(new JSONObject( new String(
                        Base64.decodeBase64(chunks[1]))  ).get(REALM_ACCESS)), Roles.class
        );

        var roles = realmRoles.getRoles().stream().filter(realmRole -> Arrays.asList(eligiblRoles).contains(realmRole) ).collect(Collectors.toList());
        if(roles.size() == 0)
            throw new HttpUnauthorizedException("Invalid Roles");

        currentUser.setRoles(realmRoles.getRoles());

    }

    @Override
    public RSAPublicKey publickeyFromKeystore() {

        load();
        return (RSAPublicKey) certificate.getPublicKey();
    }

    public void load() {

        try{
            if(this.certificate == null) {
                var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(new FileInputStream(
                                privatekeyPath),
                        password.toCharArray());
                this.certificate = keyStore.getCertificate(alias);
            }
        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex){
            //throw Helpers.raiseTokenVerificationFailed();
            System.out.println("");
        }
    }



    private RSAPublicKey loadPublicKey(DecodedJWT token) throws JwkException, MalformedURLException {

        final String url = getKeycloakCertificateUrl(token);
        JwkProvider provider = new UrlJwkProvider(new URL(url));

        return (RSAPublicKey) provider.get(token.getKeyId()).getPublicKey();
    }
    private String getKeycloakCertificateUrl(DecodedJWT token) {
        return token.getIssuer() + PROTOCOL_OPENID_CONNECT_CERTS;
    }
    private static JsonObject parseBase64(Base64 base64Url, String value) {
        return JsonParser.parseString(
                new String(base64Url.decode(value.getBytes()))).getAsJsonObject();
    }
}
