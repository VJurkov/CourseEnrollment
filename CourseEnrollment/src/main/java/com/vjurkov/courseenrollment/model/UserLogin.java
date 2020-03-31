package com.vjurkov.courseenrollment.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserLogin {

    private String username;
    private String password;
    private Boolean isLecturer;
    private MultipartFile image;
}
