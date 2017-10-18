package com.cloudhua.imageplatform.service.event;

import com.cloudhua.imageplatform.domain.LoginInfo;
import com.cloudhua.imageplatform.domain.Token;

/**
 * Created by yangchao on 2017/8/22.
 */
public class TokenEvent extends Event {

    private String name;

    private String password;

    private Token token;

    private LoginInfo loginInfo;

    public TokenEvent() {
    }

    public TokenEvent(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
