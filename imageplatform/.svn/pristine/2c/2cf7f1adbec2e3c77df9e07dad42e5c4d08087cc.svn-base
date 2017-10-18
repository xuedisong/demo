package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Sysconfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchao on 2017/8/17.
 */
@Mapper
@Repository
public interface SysconfigMapper {

    @Insert("INSERT INTO `sysconfig` (`key`, `value`, `attr`) VALUES (#{sysconfig.key}, #{sysconfig.key}, #{sysconfig.attr})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "sysconfig.id", before = false, resultType = long.class)
    int addSysconfig(@Param("sysconfig")Sysconfig sysconfig);

    @Delete("DELETE FROM `sysconfig` WHERE `id`=#{id}")
    int deleteSysconfigById(@Param("id") long id);

    @Delete("DELETE FROM `sysconfig` WHERE `key`=#{key}")
    int deleteSysconfigBy(@Param("key") String key);

    @Update("UPDATE `sysconfig` SET `value`=#{value} WHERE `id`=#{id}")
    int updateSysconfigById(@Param("id") long id, @Param("value") String value);

    @Update("UPDATE `sysconfig` SET `value`=#{value} WHERE `key`=#{key}")
    int updateSysconfigByKey(@Param("key") long key, @Param("value") String value);

    @Select("SELECT * FROM `sysconfig` WHERE `id`=#{id}")
    Sysconfig getSysconfigById(@Param("id") long id);

    @Select("SELECT * FROM `sysconfig` WHERE `key`=#{key}")
    Sysconfig getSysconfigByKey(@Param("key") String key);
}
