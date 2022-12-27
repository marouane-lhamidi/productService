package com.example.productservice.dto.token;

public class TokenDTO {

    /** The acces token. */
    private String accessToken;

    /** The refresh token. */
    private String refreshToken;

    /** The expires in. */
    private long expiresIn;

    /** The refresh expires in. */
    private long refreshExpiresIn;

    /** The token type. */
    private String tokenType;

    /**
     * Gets the acces token.
     *
     * @return the acces token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Gets the refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Gets the expires in.
     *
     * @return the expires in
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Gets the refresh expires in.
     *
     * @return the refresh expires in
     */
    public long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the acces token.
     *
     * @param accesToken the new acces token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param refreshToken the new refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Sets the expires in.
     *
     * @param expiresIn the new expires in
     */
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Sets the refresh expires in.
     *
     * @param refreshExpiresIn the new refresh expires in
     */
    public void setRefreshExpiresIn(long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the new token type
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
