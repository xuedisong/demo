package com.cloudhua.imageplatform.persistence;

import com.cloudhua.imageplatform.domain.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangchao on 2017/8/20.
 */
@Mapper
@Repository
public interface TokenMapper {

    @Insert("INSERT INTO `token` (`token`, `uid`, `expires`, `ctime`, `clientIp`, `agent`, `device`) VALUES (#{token.token}, #{token.uid}, #{token.expires}, #{token.ctime}, #{token.clientIp}, #{token.agent}, #{token.device})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "token.id", before = false, resultType = long.class)
    int addToken(@Param("token") Token token);

    //token的删法也有好几种，id,token,uid,
    @Delete("DELETE FROM `token` WHERE `id`=#{id}")
    int deleteTokenById(@Param("id") long id);

    @Delete("DELETE FROM `token` WHERE `token`=#{token}")
    int deleteToken(@Param("token") String token);

    @Delete("DELETE FROM `token` WHERE `uid`=#{uid}")
    int deleteUserToken(@Param("uid") long uid);

    @Select("SELECT * FROM `token` WHERE `id`=#{id}")
    Token getTokenInfoById(@Param("id") long id);

    @Select("SELECT * FROM `token` WHERE `token`=#{token}")
    Token getTokenInfo(@Param("token") String token);

    @Select("SELECT * FROM `token` WHERE `uid`=#{uid}")
    List<Token> getUserTokens(@Param("uid") long uid);
}
