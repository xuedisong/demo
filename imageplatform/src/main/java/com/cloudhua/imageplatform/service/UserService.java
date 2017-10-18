//用户登录登出的业务逻辑，token,httpEnv
package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.aop.AuthedInterceptor;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.domain.LoginInfo;
import com.cloudhua.imageplatform.domain.Token;
import com.cloudhua.imageplatform.domain.User;
import com.cloudhua.imageplatform.persistence.TokenMapper;
import com.cloudhua.imageplatform.persistence.UserMapper;
import com.cloudhua.imageplatform.service.event.EventHub;
import com.cloudhua.imageplatform.service.event.EventType;
import com.cloudhua.imageplatform.service.event.TokenEvent;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.ParamException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import com.cloudhua.imageplatform.service.exception.UnInitializeException;
import com.cloudhua.imageplatform.service.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangchao on 2017/8/22.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenMapper tokenMapper;

    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    /**
     * 检查用户账号密码
     *
     * @param httpEnv
     * @param name
     * @param password
     * @param tokenAge
     * @return
     * @throws UnInitializeException
     * @throws LogicalException
     */
    public LoginInfo checkUserAndPsw(HttpEnv httpEnv, String name, String password, long tokenAge) throws UnInitializeException, LogicalException {
        if (httpEnv == null || name == null || password == null) {
            throw new IllegalArgumentException("method params is null. httpEnv:" + httpEnv + " name:" + name + " password:" + password);
        }
        TokenEvent tokenEvent = new TokenEvent(name, password);//用用户名和密码，新建实例对象tokenEvent
        EventHub.getInst().dispatchEvent(EventType.PRE_LOGIN, httpEnv, tokenEvent);//将tokenEvent拿httpEnv,EventType作用
        if (!tokenEvent.isContinue()) {
            throw new LogicalException(tokenEvent.getBreakStatus(), tokenEvent.getBreakMessage());
        }
        Token token = tokenEvent.getToken();//将tokenEvent的属性Token拿出来
        User resultUser = null;
        if (token == null) {//如果token是空
            // 使用默认的账号密码登录验证
            resultUser = this.userMapper.getUserByName(name);
            if (resultUser == null) {
                throw new LogicalException(StatusCode.USER_NOT_FOUND, "user not found. name:" + name);
            }
            // 检查用户的密码是否正确
            if (AuthUtil.checkPassword(password, resultUser.getPassword())) {//如果用户存在数据库中，将用户的密码与输入密码对比，如果正确
                token = new Token(resultUser.getUid(), tokenAge, httpEnv.getClientIp(), "", httpEnv.getDevice());//则新建一个token
                tokenMapper.addToken(token);//并在映射关系中加入新的token
            }
            tokenEvent.setToken(token);//tokenEvent都处理下这个空token
        } else {//如果一开始token不为空
            resultUser = this.userMapper.getUserById(token.getUid());//则通过密令的UID来检索到User
            if (resultUser == null) {//如果密令UID没有对应User,就输出没找到Uid的User
                throw new LogicalException(StatusCode.USER_NOT_FOUND, "token user not found. uid:" + token.getUid());
            }
        }
        resultUser.setPassword("");//经过上面的校验，如果程序还能执行，说明token完好，有对应的User，User密码设为空——————————————？
        LoginInfo loginInfo = new LoginInfo(token, resultUser);//登录信息由token和User共同新建
        tokenEvent.setLoginInfo(loginInfo);//然后再设置该tokenEvent的登录信息
        EventHub.getInst().dispatchEvent(EventType.POST_LOGIN, httpEnv, tokenEvent);//然后再加上httpEnv和tokenEvent关系一下
        if (!tokenEvent.isContinue()) {
            throw new LogicalException(tokenEvent.getBreakStatus(), tokenEvent.getBreakMessage());
        }

        return loginInfo;//如果能继续，返回一个登陆信息
    }

    public void logout(HttpEnv httpEnv) throws LogicalException, UnInitializeException {
        if (httpEnv == null) {
            throw new IllegalArgumentException("method params is null.");
        }
        String token = AuthedInterceptor.getRequestToken(httpEnv);//由http EVENT新建token
        if (token == null) {
            throw new ParamException("token not found");
        }

        TokenEvent tokenEvent = new TokenEvent();//新建TokenEvent,并用不是空的token初始化它
        Token t = this.tokenMapper.getTokenInfo(token);
        tokenEvent.setToken(t);
        EventHub.getInst().dispatchEvent(EventType.PRE_LOGOUT, httpEnv, tokenEvent);//logput
        if (!tokenEvent.isContinue()) {
            throw new LogicalException(tokenEvent.getBreakStatus(), tokenEvent.getBreakMessage());
        }

        this.tokenMapper.deleteToken(token);//在映射关系中删除掉token

        EventHub.getInst().dispatchEvent(EventType.POST_LOGOUT, httpEnv, tokenEvent);//POST
        if (!tokenEvent.isContinue()) {
            throw new LogicalException(tokenEvent.getBreakStatus(), tokenEvent.getBreakMessage());
        }
    }
}
