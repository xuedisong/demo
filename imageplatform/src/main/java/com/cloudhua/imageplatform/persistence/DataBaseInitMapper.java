package com.cloudhua.imageplatform.persistence;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchao on 2017/8/17.
 */
@Mapper//这个Mapper接口相当于mapper.xml——ID值分别定位其中的sql语句，在test利用测试类反射 加载两层配置文件，执行，然后结果给到完全隔离的java对象
@Repository
public interface DataBaseInitMapper {//Mybatis是持久层框架：将接口和Java的POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录。先想好sql查询语句,再想返回的结果集要有相应的java对象

    @Select("#{sql}")//等价于写了方法体
    int execSql(@Param("sql") String sql);

    @Select("SELECT COUNT(1) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=database()")
    int getTableCount();

    /**
     * 注：dbversion代表的是数据库对应的svn版本号，如果dbversion低于当前svn版本号则需要执行mysql-update.sql文件中
     * 版本在dbversion和当前版本号之间的数据库语句
     * @return
     */
    @Select("SELECT `value` FROM `sysconfig` WHERE `key`='dbversion'")
    String getDBVersionConfig();

    @Update("UPDATE `sysconfig` SET `value`=#{value} WHERE `key`=#{key}")
    int updateConfig(@Param("key") String key, @Param("value") String value);

    @Insert("INSERT INTO `sysconfig` (`value`, `key`) VALUES (#{value}, #{key})")
    int addConfig(@Param("key") String key, @Param("value") String value);
}
