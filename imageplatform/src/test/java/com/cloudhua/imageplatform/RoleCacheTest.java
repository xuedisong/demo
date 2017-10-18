package com.cloudhua.imageplatform;

import com.cloudhua.imageplatform.ImageplatformApplication;
import com.cloudhua.imageplatform.domain.Role;
import com.cloudhua.imageplatform.persistence.RoleRightMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yangchao on 2017/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ImageplatformApplication.class)
public class RoleCacheTest {

    @Autowired
    private RoleRightMapper roleRightMapper;

    @Before
    public void before() {
        String name = "role" + System.currentTimeMillis();
        Role role = new Role(name, "displayName", null, 110L);
        int a = roleRightMapper.addRole(role);
    }

    @Test
    public void test() {
        Role role = roleRightMapper.getRoleById(1L);
        System.out.println("1:" + role.getName());

        role = roleRightMapper.getRoleById(1L);
        System.out.println("2:" + role.getName());

        role.setName("test_role");
        roleRightMapper.updateRole(role);
        role = roleRightMapper.getRoleById(1L);
        System.out.println("3:" + role.getName());


    }
}
