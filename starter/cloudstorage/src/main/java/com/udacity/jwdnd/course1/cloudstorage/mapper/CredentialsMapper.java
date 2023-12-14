package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getUserCredentials(Integer userid);

    @Select ("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials getCredentialsById(Integer credentialId);



    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            "VALUES(#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET username = #{username}, password = #{password} WHERE credentialid = #{credentialId}")
    void update(Credentials credentials);


}
