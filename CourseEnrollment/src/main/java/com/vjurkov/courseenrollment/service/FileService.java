package com.vjurkov.courseenrollment.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    String saveFile(MultipartFile multipartFile, String username);
    File getFile(String fileName);

}
