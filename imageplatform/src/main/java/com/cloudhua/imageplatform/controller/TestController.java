package com.cloudhua.imageplatform.controller;

import com.alibaba.fastjson.JSON;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.domain.Test;
import com.cloudhua.imageplatform.persistence.TestMapper;
import com.cloudhua.imageplatform.service.exception.ParamException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yangchao on 2017/8/14.
 */
@Controller//控制层（WEB）组件，直接在网页上访问相应链接，来进test用户的增删查改的WEB层逻辑
@RequestMapping("/test")//允许网页访问"com/cloudhua/imageplatform/controller/TestController/test"来在网页上运行该类
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping(value = "/insert/{name}", method = RequestMethod.GET)//允许网页访问"com/cloudhua/imageplatform/controller/TestController/test/insert/"来在网页上运行该方法，GET请求

    @NoNeedAuth
    /**
     * before：“NoJsonData”，再环绕：“processs”拦截，最后“authed”
     * NoJsonData:request.sat("NoJsonData",true)
     * process:没做什么
     * authed:通过的话，说明连接点中参数中有合法的token信息。但是，这些参数中本来就没有HttpEven，所以感觉通过不了啊
     */
    public void insert(@PathVariable(name = "name") String name, HttpServletResponse response) throws IOException {//插入操作，string，请求，响应，从数据库中的test用户进行插入操作
        Test test = new Test();
        test.setName(name);
        testMapper.insert(test);//对应数据库的插入操作
        response.getWriter().write(test.toString());
        response.getWriter().flush();//等读写内存中的数据完全流尽，再关闭流
        response.getWriter().close();
    }

    @RequestMapping(value = "/select/{name}", method = RequestMethod.GET)
    @NoJsonData
    @NoNeedAuth
    public void select(@PathVariable(name = "name") String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Test test = testMapper.getTestByName(name);
        response.getWriter().write(test.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @NoJsonData
    @NoNeedAuth
    public void getAll(HttpEnv httpEnv, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Test> list = testMapper.getALl();
        ObjectMapper mapper = new ObjectMapper();
        String test = mapper.writeValueAsString(list);
        response.getWriter().write(test);
        response.getWriter().flush();
        response.getWriter().close();
    }

    // {"users":["yangchao", "lixin"]}
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @NoNeedAuth
    public void userList(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, ParamException {
        List<String> userNames = httpEnv.paramGetList("users", true, true, String.class);
        for (String name: userNames
             ) {
            System.out.println("name:" + name);
        }
    }

    @RequestMapping(value="/test4",method=RequestMethod.POST)
    public boolean test4(@RequestBody Map<String, String> fieldValueList) {
        System.out.println(":"+ JSON.toJSONString(fieldValueList));
        return true;
    }

}