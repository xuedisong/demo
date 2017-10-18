package com.cloudhua.imageplatform.service.utils;

import com.cloudhua.imageplatform.domain.Role;
import com.cloudhua.imageplatform.domain.User;
import com.cloudhua.imageplatform.persistence.RoleRightMapper;
import com.cloudhua.imageplatform.persistence.UserMapper;
import com.cloudhua.imageplatform.service.RoleRightService;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.RightException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by yangchao on 2017/8/21.
 * 权限检查类，检查用户是否有操作权限
 */
@Component
public class RightUtil {

    @Autowired
    private RoleRightService roleRightService;

    private static RightUtil rightUtil;

    @PostConstruct
    public void init(){
        rightUtil = this;
        rightUtil.roleRightService = this.roleRightService;
    }
    public static void chechRight(long uid, long rightValue) throws LogicalException{
        // 获取用户角色
        List<Role> userRoles = rightUtil.roleRightService.getUserAllRole(uid);
        boolean hasRight = false;
        for (Role userRole: userRoles
                ) {
            if ((userRole.getRight() & rightValue) == rightValue) {
                hasRight = true;
                break;
            }
        }
        if (!hasRight) {
            throw new RightException(StatusCode.PERMISSION_DENIED, StatusCode.PERMISSION_DENIED);
        }
    }
}
