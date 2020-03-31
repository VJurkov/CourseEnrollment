package com.vjurkov.courseenrollment.controller;

import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.service.CourseService;
import com.vjurkov.courseenrollment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    // For this type of autowiring, class type is used. So there should
    // be only one bean configured for this type in the spring bean configuration file.
    //By default spring bean autowiring is turned off. Spring bean autowire default value is
    // “default” that means no autowiring is to be performed. autowire value “no” also have the same behavior.
    //beans that will be injected in other beans using ref attribute
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public String index (Model model)
    {
        model.addAttribute("course", new Course());
        model.addAttribute("lecturers", userService.getAllLecturers());

        return "addCourse";
    }

    @PostMapping
    public  String createNewCourse(@Valid Course course, Errors errors,Model model)
    {
        if(errors.hasErrors()){
            model.addAttribute("course", new Course());
            model.addAttribute("lecturers", userService.getAllLecturers());

            return "addCourse";
        }
        courseService.addCourse(course);
        return "redirect:/home";
    }
// zove prototype, tu ga koristim
    @PostMapping("/cloneCourse")
    public String cloneCourse(Long courseId){
        Optional<Course> course = courseService.findById(courseId);
        Course cloned  = course.get().clone();
        courseService.addCourse(cloned);
        return "redirect:/home";
    }
}
