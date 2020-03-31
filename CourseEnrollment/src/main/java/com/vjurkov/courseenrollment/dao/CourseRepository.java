package com.vjurkov.courseenrollment.dao;

import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//jparepository po imenu metode stvori i sql koji ce poslati na bazu
public interface CourseRepository extends JpaRepository <Course, Long>{

    //po imenu metode skuzi sta ce napraviti u upitu
    List<Course> findAllByStudentsContains(User user);
    List<Course> findAllByStudentsNotContaining(User user);
    Course findByCourseIdAndStudentsContains(Long id, User user);
}
