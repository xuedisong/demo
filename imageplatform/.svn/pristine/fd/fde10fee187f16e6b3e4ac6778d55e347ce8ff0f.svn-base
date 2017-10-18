package com.cloudhua.imageplatform.aop;

import com.cloudhua.imageplatform.service.log.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangchao on 2017/8/22.
 */
@Aspect
@Component
@Order(Integer.MAX_VALUE)
public class NoJsonDataInterceptor {//不是Json数据时登陆无需验证身份环绕，但需在前面运行以下代码，拦截器

    private static Logger logger = Logger.getInst(NoJsonDataInterceptor.class);

    @Pointcut("execution(* com.cloudhua.imageplatform.controller..*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "&& @annotation(com.cloudhua.imageplatform.controller.NoJsonData)")
    private void controllerPointcutWithNoJsonData() {
    }


    @Before("controllerPointcutWithNoJsonData()")

    public void processNoJsonData() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();//获取请求的属性
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;//转化成servlet请求属性
        HttpServletRequest request = sra.getRequest();//获得请求
        request.setAttribute("NoJsonData", true);//请求《——在请求里键值对说明不是Json数据
    }
}
