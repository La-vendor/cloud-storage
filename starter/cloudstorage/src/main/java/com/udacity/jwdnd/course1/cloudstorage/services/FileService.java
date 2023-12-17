package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;


    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void addFile(MultipartFile multipartFile, Integer userId) {
        File file = new File(multipartFile, userId);
        fileMapper.insert(file);
    }

    public List<File> getFiles(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public File getFileByName(Integer userId, String fileName){
        return fileMapper.getFileByName(userId, fileName);
    }
}
