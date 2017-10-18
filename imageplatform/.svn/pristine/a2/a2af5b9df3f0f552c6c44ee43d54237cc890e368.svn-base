package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchao on 2017/8/15.
 * typeMapper映射关系
 */
@Mapper
@Repository("typerMapper")
public interface TypeMapper {
    @Select("SELECT * FROM `type` WHERE `typeId`=#{typeId}")
    Type getTypeById(@Param("typeId") long typeId);

    @Select("SELECT * FROM `type` WHERE `name`=#{name}")
    Type getTypeByName(@Param("name") String name);

    @Insert("INSERT INTO `type` WHERE `name`=#{type.name}")
    @SelectKey(statement = "select last_insert_id()", keyProperty="type.typeId", before=false, resultType=long.class)
    int insertType(@Param("type") Type type);

    @Update("UPDATE `type` SET `name`=#{type.name} WHERE `typeId`=#{type.typeId}")
    int updateType(@Param("type") Type type);

    @Delete("DELETE FROM `type` WHERE `typeId`=#{typeId}")
    int deleteTypeById(@Param("typeId") long typeId);

    @Delete("DELETE FROM `type` WHERE `name`=#{name}")
    int delteTypeByName(@Param("name") String name);
}
