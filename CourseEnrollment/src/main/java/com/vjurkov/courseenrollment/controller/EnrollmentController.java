package com.vjurkov.courseenrollment.controller;

import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.model.StudentEnrollment;
import com.vjurkov.courseenrollment.service.CourseService;
import com.vjurkov.courseenrollment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/enroll")
public class EnrollmentController {

    @Autowired
    CourseService courseService;
    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public String index (Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Course> courses = courseService.getNonEnrolledCourses(username);
        if(courses.size() <= 0){
            return "redirect:/home";
        }
        model.addAttribute("enrollment", new StudentEnrollment());
        model.addAttribute("courses", courseService.getNonEnrolledCourses(username));

        return "enroll";
    }

    @PostMapping
    public  String createNewEnrollment(StudentEnrollment studentEnrollment)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        studentEnrollment.setStudentId(username);

        enrollmentService.enrollStudent(studentEnrollment.getCourseId(),studentEnrollment.getStudentId());

        return "redirect:/home";
    }
}
