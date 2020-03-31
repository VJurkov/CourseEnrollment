package com.vjurkov.courseenrollment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    ServletContext context;

    @Autowired
    File nullFile;

    @Override
    public String saveFile(MultipartFile multipartFile, String username) {
        File root = new File(context.getRealPath("resources/uploads"));
        File fileToSave = new File(context.getRealPath("resources/uploads") +"/"+ username+".png");
        if(!root.exists()){
            root.mkdirs();
        }
        try {
            multipartFile.transferTo(fileToSave);
            return username+".png";
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file");
        }
    }
// ako file ne postoji vrača null file, a taj file je definiran u null configu
    @Override
    public File getFile(String fileName) {
        File fileToGet = new File(context.getRealPath("resources/uploads") +"/"+ fileName);

        if(fileToGet.exists()){
            return fileToGet;
        }else{
            return nullFile;
        }
    }
}
