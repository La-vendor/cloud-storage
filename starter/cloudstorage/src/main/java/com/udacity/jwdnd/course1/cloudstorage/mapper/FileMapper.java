package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE username = #{username}")
    List<File> getFilesByUsername(String username);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid, filedata)" +
            "VALUES(#{fileName},#{contentType},#{fileSize},#{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(User user);

    @Delete("DELETE FROM FILES WHERE id = #{id}")
    void delete(Integer id);
}
