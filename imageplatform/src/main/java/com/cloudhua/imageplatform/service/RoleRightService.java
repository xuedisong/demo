//业务逻辑层。用到缓冲注解
package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.domain.Role;
import com.cloudhua.imageplatform.persistence.RoleRightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchao on 2017/8/21.
 */
@CacheConfig(cacheNames = "role_right")
@Service

public class RoleRightService {

    @Autowired
    private RoleRightMapper roleRightMapper;

    @CachePut(key = "'role_' + #role.roleId")//每次都将将结果放入缓存
    public void addRole(Role role) {
        roleRightMapper.addRole(role);
    }

    @Cacheable(key = "'role_' + #roleId")//缓存可用，就不执行
    public Role getRoleById(long roleId) {
        return roleRightMapper.getRoleById(roleId);
    }

    @CacheEvict(allEntries = true)
    public void deleteRole(long roleId) {
        this.roleRightMapper.deleteRoleById(roleId);
    }

    @CachePut(key = "'role_' + #role.roleId")
    public void setRole(Role role) {
        this.roleRightMapper.updateRole(role);
    }

    @Cacheable(key = "'role_users_'+#roleId")
    public List<Long> getRoleUserId(long roleId) {
        return this.roleRightMapper.getRoleAllUserId(roleId);
    }

    public int addUserRole(long roleId, long uid) {
        return roleRightMapper.addUserRole(uid, roleId);
    }

    @Cacheable(key = "'user_roles_'+#uid")
    public List<Role> getUserAllRole(long uid) {
        List<Long> userRoleIds = this.roleRightMapper.getUserAllRoleId(uid);
        List<Role> roles = new ArrayList<>();
        for (Long roleId: userRoleIds
             ) {
            Role role = this.getRoleById(roleId);
            roles.add(role);
        }
        return roles;
    }
}
