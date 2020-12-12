package com.pinartekes.api.dto;

public class AuthToken {
    private String token;

    private AuthUserDto user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthUserDto getUser() {
        return user;
    }

    public AuthToken() {
        super();
    }

    public AuthToken(String token, AuthUserDto user) {
        super();
        this.token = token;
        this.user = user;
    }

    public void setUser(AuthUserDto user) {
        this.user = user;
    }
}
