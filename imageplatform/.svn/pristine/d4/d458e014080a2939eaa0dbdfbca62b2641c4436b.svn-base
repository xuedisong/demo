package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Tower;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangchao on 2017/8/15.
 */
@Mapper
@Repository
public interface TowerMapper {

    @Insert("INSERT INTO `tower` (`name`, `parentId`, `status`, `typeId`, `owerId`, `longitude`, `latitude`, `attr`) VALUES (#{tower.name}, #{tower.parentId}, #{tower.status}, #{tower.typeId}, #{tower.owerId}, #{tower.longitude}, #{tower.latitude}, #{tower.attr})")
    @SelectKey(statement = "select last_insert_id()", keyProperty="tower.towerId", before=false, resultType=long.class)
    int insertTower(@Param("tower")Tower tower);

    @Delete("DELETE FROM `tower` WHERE `towerId=#{towerId}`")
    int deleteTower(@Param("towerId") long towerId);

    @Update("UPDATE `tower` SET `name`=#{tower.name}, `parentId`=#{tower.parentId}, `status`=#{tower.status}, `typeId`=#{tower.typeId}, `owerId`=#{tower.owerId}, `longitude`=#{tower.longitude}, `latitude`=#{tower.latitude}, `attr`=#{tower.attr} WHERE `towerId`=#{tower.towerId}")
    int updateTower(@Param("tower") Tower tower);

    @Select("SELECT * FROM `tower` WHERE `towerId`=#{towerId}")
    Tower getTowerById(@Param("towerId") long towerId);

    @Select("SELECT * FROM `tower` WHERE `parentId`=#{parentId} AND `name`=#{name}")
    Tower getTowerByName(@Param("parentId") long parentId, @Param("name") String name);

    @Select("SELECT * FROM `tower` WHERE `status`=#{status}")
    List<Tower> getTowerByStatus(@Param("status") int status);
}
