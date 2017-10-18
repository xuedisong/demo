package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.ImageplatformApplication;
import com.cloudhua.imageplatform.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yangchao on 2017/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ImageplatformApplication.class)
public class RoleRightServiceTest {

    @Autowired
    private RoleRightService roleRightService;

    @Test
    public void test() {
        String name = "role" + System.currentTimeMillis();
        Role role = new Role(name, "displayName", null, 110L);
        roleRightService.addRole(role);
        System.out.println("add:" + role.getName());

        Role r1 = roleRightService.getRoleById(role.getRoleId());
        System.out.println("get r1:" + r1.getName());

        Role r2 = roleRightService.getRoleById(role.getRoleId());
        System.out.println("get r2:" + r2.getName());
    }
}
