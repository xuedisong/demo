package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Test;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangchao on 2017/8/14.
 */
@Mapper
@Repository
public interface TestMapper {

    @Select("select * from `test` where `id`=#{id}")
    public Test getTestById(@Param("id") long id);

    @Select("select * from `test` where `name`=#{name}")
    public Test getTestByName(@Param("name") String name);

    @Insert("insert into `test` (`name`) values (#{test.name})")
    @SelectKey(statement = "select last_insert_id()", keyProperty="test.id", before=false, resultType=long.class)
    public int insert(@Param("test") Test test);

    @Select("select * from `test`")
    List<Test> getALl();//————————————————————————ALl
}
