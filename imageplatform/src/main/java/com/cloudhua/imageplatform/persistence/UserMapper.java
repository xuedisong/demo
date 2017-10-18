package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangchao on 2017/8/20.
 */
@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO `user` (`name`, `nickName`, `email`, `phone`, `password`, `position`, `status`, `order`, `attr`, `ctime`, `mtime`)" +
            " VALUES(#{user.name}, #{user.nickName}, #{user.email}, #{user.phone}, #{user.password}, #{user.position}, #{user.status}, " +
            " #{ user.order}, #{user.attr}, #{user.ctime}, #{user.mtime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "user.uid", before = false, resultType = long.class)
    int addUser(@Param("user") User user);

    @Update("UPDATE `user` SET `status`=0 WHERE `uid`=#{uid}")//将#与@Param对应
    int deleteUserByUid(@Param("uid") long uid);

    @Update("UPDATE `user` SET `status`=0 WHERE `name`=#{name}")
    int deleteUserByName(@Param("name") String name);

    @Update("UPDATE `user` SET `mtime`=#{mtime} WHERE `uid`=#{uid}")
    int setUserMtime(@Param("uid") Long uid,@Param("mtime")long mtime);

    @Update("Update `user` SET `nickName`=#{nickName} WHERE `uid`=#{uid}")
    int setUserNickName(@Param("uid") long uid, @Param("nickName") String nickName);

    @Update("UPDATE `user` SET `email`=#{email} WHERE `uid`=#{uid}")
    int setEmail(@Param("uid") Long uid,@Param("email")String email);

    @Update("UPDATE `user` SET `phone`=#{phone} WHERE `uid`=#{uid}")
    int setPhone(@Param("uid") Long uid,@Param("phone")String phone);

    @Update("UPDATE `user` SET `password`=#{password} WHERE `uid`=#{uid}")
    int setPassword(@Param("uid") Long uid,@Param("password")String password);

    @Select("SELECT * FROM `user` WHERE `uid`=#{uid}")
    User getUserById(@Param("uid") long uid);

    @Select("SELECT * FROM `user` WHERE `name`=#{name}")
    User getUserByName(@Param("name") String name);

    @Select("SELECT * FROM `user` WHERE `email`=#{email}")
    List<User> getUsersByEmail(@Param("email") String email);

    @Select("SELECT * FROM `user` WHERE `phone`=#{phone}")
    List<User> getUserByPhone(@Param("phone") String phone);

    @Select("SEKECT * FROM `user` WHERE `status`=#{status}")
    List<User> getUserByStatus(@Param("status") String status);

    @Select("SELECT *  FROM `user` WHERE name like '%#{name}%'")//like,模糊查找
    List<User> searchUserByName(@Param("name") String name);

    @Select("SELECT *  FROM `user` WHERE name like '%#{nickName}%'")
    List<User> searchUserByNickName(@Param("nickName") String nickName);

}
