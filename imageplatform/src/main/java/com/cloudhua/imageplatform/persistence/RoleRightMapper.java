package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangchao on 2017/8/17.
 */
@Mapper
@Repository
public interface RoleRightMapper {
    @Insert("INSERT INTO `role` (`name`, `displayName`, `right`, `attr`) VALUES (#{role.name}, #{role.displayName}, #{role.right}, #{role.attr})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "role.roleId", before = false, resultType = long.class)
    int addRole(@Param("role")Role role);

    @Delete("DELETE FROM `role` WHERE `roleId`=#{roleId}")
    int deleteRoleById(@Param("roleId") long roleId);

    @Delete("DELETE FROM `role` WHERE `name` = #{name}")
    int deleteRoleByName(@Param("name") String name);

    @Update("UPDATE `role` SET `name`=#{role.name}, `displayName`=#{role.displayName}, attr=#{role.attr} WHERE `roleId`=#{role.roleId}")
    int updateRole(@Param("role") Role role);

    @Select("SELECT * FROM `role` WHERE `roleId`=#{roleId}")
    Role getRoleById(@Param("roleId") long roleId);

    @Select("SELECT * FROM `role` WHERE `name`=#{name}")
    Role getRoleByName(@Param("name") String name);

    @Insert("INSERT INTO `role_user` (`roleId`, `uid`) VALUES (#{roleId}, #{uid})")
    int addUserRole(@Param("uid") long uid, @Param("roleId") long roleId);

    @Delete("DELETE FROM `role_user` WHERE `roleId`=#{roleId} AND `uid`=#{uid}")
    int deleteRoleUser(@Param("roleId") long roleId, @Param("uid") long uid);

    @Delete("DELETE FROM `role_user` WHERE `uid`=#{uid}")
    int deleteUserAllRole(@Param("uid") long uid);

    @Delete("DELETE FROM `role_user` WHERE `roleId`=#{roleId}")
    int deleteRoleAllUser(@Param("roleId") long roleId);

    @Select("SELECT `uid` FROM `role_user` WHERE `roleId`=#{roleId}")
    List<Long> getRoleAllUserId(@Param("roleId") long roleId);

    @Select("SELECT `roleId` FROM `role_user` WHERE `uid`=#{uid}")
    List<Long> getUserAllRoleId(@Param("uid") long uid);

    @Select("SELECT `role.name` as `name`, `role`.`displayName` as `displayName`, `role`.`right` as `right`, `role`.`attr` as `attr` from `role_user` left join `role` on `role_user`.roleId = `role`.`roleId` WHERE `uid`=#{uid}")
    List<Role> getUserAllRole(@Param("uid") long uid);

    @Select("SELECT SUM(`role`.`size`) from `role_user` left join `role` on `role_user`.roleId = `role`.`roleId` WHERE `uid`=#{uid}")
    long getUserRight(@Param("uid") long uid);
}
