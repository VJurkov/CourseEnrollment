package com.vjurkov.courseenrollment.controller;

import com.vjurkov.courseenrollment.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")

public class HomeController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = authentication.getAuthorities().toArray()[0].toString();
        if(role.equals("STUDENT")){
            model.addAttribute("allCourses", null);
            model.addAttribute("studentCourses", courseService.getAllCourses(username));
        } else {
            model.addAttribute("studentCourses", null);
            model.addAttribute("allCourses", courseService.getAllCourses());
        }


        return "index";
    }

}
