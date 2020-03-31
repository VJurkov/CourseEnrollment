package com.vjurkov.courseenrollment.service;

import com.vjurkov.courseenrollment.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses(String username);
    List<Course> getNonEnrolledCourses(String username);
    List<Course> getAllCourses();
    Optional<Course> findById(Long id);
    Optional<Course> findByIdAndEnroll(Long id,String username);
    boolean exists (Long id);
    void deleteCourse(Long id);

    Course addCourse(Course course);
}
