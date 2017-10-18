package com.cloudhua.imageplatform.controller;

import com.cloudhua.imageplatform.aop.AuthedInterceptor;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.domain.LoginInfo;
import com.cloudhua.imageplatform.domain.Token;
import com.cloudhua.imageplatform.persistence.TokenMapper;
import com.cloudhua.imageplatform.service.UserService;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.ParamException;
import com.cloudhua.imageplatform.service.exception.UnInitializeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

/**
 * Created by yangchao on 2017/8/22.
 */
@RestController//相当于@ResponseBody ＋ @Controller
@RequestMapping("/token")//返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；将Controller的方法返回的对象，放在body数据区
public class TokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenMapper tokenMapper;

    /**
     * 用户登录接口
     * @param httpEnv
     */
    @RequestMapping(method = RequestMethod.POST)
    @NoNeedAuth
    public void login(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws LogicalException, UnInitializeException {
        String username = httpEnv.paramGetString("name", true);
        String password = httpEnv.paramGetString("password", true);
        Long tokenAge = httpEnv.paramGetLong("tokenAge", false);
        if (tokenAge == null) {
            tokenAge = Token.defaultTokenAge;
        }
        LoginInfo loginInfo = this.userService.checkUserAndPsw(httpEnv, username, password, tokenAge);
        httpEnv.getJsonResult().put("loginInfo", loginInfo);
        Token token = loginInfo.getToken();
        Cookie cookie = new Cookie("token", token.getToken());
        // 关闭浏览器时cookie失效
        cookie.setMaxAge(-1);
        httpEnv.getResponse().addCookie(cookie);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void checkLogin(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws ParamException {
        //
    }

    /**
     * 登出账户
     * @param httpEnv
     * @param requestBody
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void logout(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws LogicalException, UnInitializeException {//如果是DELETE请求，则登出操作
        this.userService.logout(httpEnv);
    }
}
