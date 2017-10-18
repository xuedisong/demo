package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Position;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchao on 2017/8/20.
 */
@Mapper
@Repository
public interface PositionMapper {

    @Insert("INSERT INTO `position` (`name`, `exattr`) VALUES (#{name}, #{attr})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "position.position", before = false, resultType = long.class)
    int addPosition(@Param("position")Position position);

    @Delete("DELETE FROM `position` WHERE `position`=#{position}")
    int deletePosition(@Param("position") long position);

    @Delete("DELETE FROM `position` WHERE `name`=#{name}")
    int deletePositionByName(@Param("name") String name);

    @Update("UPDATE `position` SET `name`=#{name} WHERE `position`=#{position}")
    int updatePosition(@Param("position") long position, @Param("name") String name);

    @Select("SELECT * FROM `position` WHERE `position`=#{position}")
    Position getPosition(@Param("position") long position);

    @Select("SELECT * FROM `position` WHERE `name`=#{name}")
    Position getPositionByName(@Param("name") String name);
}
