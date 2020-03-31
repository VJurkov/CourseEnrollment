package com.vjurkov.courseenrollment.controller;

import com.vjurkov.courseenrollment.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping
    public ResponseEntity<byte[]> serveImage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String filename = username + ".png";
        File file = fileService.getFile(filename);
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
