package com.cloudhua.imageplatform.aop;

import com.cloudhua.imageplatform.config.RightConfig;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.domain.Token;
import com.cloudhua.imageplatform.persistence.RoleRightMapper;
import com.cloudhua.imageplatform.persistence.TokenMapper;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.RespJsonErrorMsg;
import com.cloudhua.imageplatform.service.exception.RightException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import com.cloudhua.imageplatform.service.log.Logger;
import com.cloudhua.imageplatform.service.utils.RightUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 部门权限验证
 * Created by lixin on 2017/9/14.
 */
@Aspect
@Component
@Order(Integer.MAX_VALUE)
public class DeptRightInterceptor {

    @Autowired
    private TokenMapper tokenMapper;
    private static Logger logger = Logger.getInst(AuthedInterceptor.class);
    @Autowired
    private RoleRightMapper roleRightMapper;

    @Pointcut("execution(* com.cloudhua.imageplatform.controller.DeptController.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void checkDeptRightPointcut() {
    }
    @Around("checkDeptRightPointcut()")
    public void checkRight(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();
        Object[] args = pjp.getArgs();//获得连接点的参数列表
        HttpEnv httpEnv = null;
        for (Object obj : args) {
            if (obj instanceof HttpEnv) {
                httpEnv = (HttpEnv) obj;
                break;
            }
        }
        String language = request.getHeader("X-Language");
        if (language == null) {
            language = "zh";
        }
        String token = AuthedInterceptor.getRequestToken(httpEnv);
        //以上获取token

        //权限校验
        Token t = tokenMapper.getTokenInfo(token);
        if (token != null) {
            RightUtil.chechRight(t.getUid(),RightConfig.DEPT_MANAGE);
            try {
                pjp.proceed(args);
            } catch (Throwable excep) {
                try {
                    RespJsonErrorMsg.sendErrorMsg(language, response, StatusCode.PERMISSION_DENIED);
                } catch (IOException e) {
                    logger.error("role has not right, response client error msg failed.", e);
                }
            }
        }else
            throw new RightException("no right","no right");
    }
}
