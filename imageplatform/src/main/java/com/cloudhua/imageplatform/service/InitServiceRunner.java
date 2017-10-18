package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.service.event.EventHub;
import com.cloudhua.imageplatform.service.utils.ThreadScheduler;
import com.cloudhua.imageplatform.service.utils.UfaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by yangchao on 2017/8/22.
 */
@Component
public class InitServiceRunner implements CommandLineRunner {//服务初始化
    @Value("${ufa.center}")
    private static String uctrAddr;

    @Value("${ufa.access.id}")
    private static String uctrAccessId;

    @Value("${ufa.access.sec}")
    private static String ucctrAccessSec;

    @Override//不写也行，写上编译器会检查不是重写会报错
    public void run(String... args) throws Exception {//动态参数，能传递多个String..初始化
        ThreadScheduler.init(10);
        EventHub.init();
        UfaUtils.init(uctrAddr, uctrAccessId, ucctrAccessSec);
    }
}
