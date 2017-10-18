package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by yangchao on 2017/8/15.
 */
@Mapper
@Repository
public interface ImageMapper {

    @Select("SELECT * FROM `image` WHERE imgId=#{imgId}")
    Image getImageById(@Param("imgId") long imgId);

    @Select("SELECT * FROM `image` WHERE towerId=#{towerId}")
    Image getImageByTowerId(@Param("towerId") long towerId);

    @Select("SELECT * FROM `image` WHERE creator=#{creator} AND stat > 0 LIMIT #{start}, #{limit}")
    Image getImageByCreator(@Param("creator") long creator, @Param("start") long start, @Param("limit") long limit);

    @Insert("INSERT INTO `image` (`name`, `formatName`, `size`, `length`, `width`, `stor`, `stat`, `ctime`, `creator`, `towerId`, `attr`) VALUES (#{image.name}, #{image.formatName}, #{image.size}, #{image.length}, #{image.width}, #{image.stor}, #{image.stat}, #{image.ctime}, #{image.creator}, #{image.towerId}, #{image.attr})")
    @SelectKey(statement = "select last_insert_id()", keyProperty="image.imgId", before=false, resultType=long.class)
    int insertImage(@Param("image") Image image);

    @Update("UPDATE `image` SET `name`=#{image.name}, `formatName`=#{image.formatName}, `size`=#{image.size}, `length`=#{image.length}, `width`=#{image.width}, `stor`=#{image.stor}, `stat`=#{image.stat}, `ctime`=#{image.ctime}, `creator`=#{image.creator}, `towerId`=#{image.towerId}, `attr`=#{image.attr} WHERE `imgId`=#{image.imgId}")
    int upadteImage(@Param("image") Image image);

    /**
     * 逻辑删除image信息，将stat状态设置成负数 -image.stat
     * @param imgId 图片id
     * @param stat 删除状态，设置为原图片stat的负数
     * @return
     */
    @Update("UPDATE `image` SET `stat`=#{deleteStat} WHERE `imgId`=#{imgId}")
    int deleteImageById(@Param("imgId") long imgId, @Param("deleteStat") int stat);

}
