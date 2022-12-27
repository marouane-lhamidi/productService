package com.example.productservice.common.config;

import com.example.productservice.dto.token.TokenDTO;
import com.example.productservice.common.exception.HttpUnauthorizedException;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

public interface Config {

    public HashMap<String, String> verifySignaturekeycloakAndLoadPayload(TokenDTO tokenDTO) throws Exception;
    public void checkroles(String[] paramsNames, Object[] args,String[] role) throws HttpUnauthorizedException;

    public RSAPublicKey publickeyFromKeystore();
}