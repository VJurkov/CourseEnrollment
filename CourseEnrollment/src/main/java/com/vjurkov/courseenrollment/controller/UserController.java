package com.vjurkov.courseenrollment.controller;

import com.vjurkov.courseenrollment.model.UserLogin;
import com.vjurkov.courseenrollment.service.FileService;
import com.vjurkov.courseenrollment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @GetMapping
    public String register(){
        return "register";
    }

    @PostMapping
    public String register(UserLogin userLogin){
        if(userLogin.getIsLecturer() == null){
            userLogin.setIsLecturer(false);
        }
        fileService.saveFile(userLogin.getImage(),userLogin.getUsername());
        userService.registerUser(userLogin.getUsername(), userLogin.getPassword(), userLogin.getIsLecturer());
        return "redirect:/login";
    }
}
