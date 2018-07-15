package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annottions.LazyBean;


@LazyBean
public class Token {

    private String accessToken;
    private String refreshToken;

    public static Token dummy() {
        return new Token()
                .setAccessToken("123abc")
                .setRefreshToken("abc123");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Token setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Token setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
