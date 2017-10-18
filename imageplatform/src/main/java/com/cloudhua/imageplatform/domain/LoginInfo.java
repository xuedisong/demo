package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/22.
 */
public class LoginInfo {
    private Token token;

    private User user;

    public LoginInfo(Token token, User user) {
        this.token = token;
        this.user = user;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
