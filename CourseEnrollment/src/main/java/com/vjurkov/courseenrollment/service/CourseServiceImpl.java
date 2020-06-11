package com.vjurkov.courseenrollment.service;

import com.vjurkov.courseenrollment.dao.CourseRepository;
import com.vjurkov.courseenrollment.dao.UserRepository;
import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//servis zove repositorij a mi zovemo servis iz kontrolera, to je korisno ako se rade komplekse stvari, kao kad bi htjeli znati
// trenutno ulogiranog usera
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Course> getAllCourses(String username) {
        Optional<User> student = userRepository.findById(username);
        if (!student.isPresent()){
            return null;
        }

       return courseRepository.findAllByStudentsContains(student.get());
    }

    //imamo ekstra logiku koju ne moramo pisati u kontroleru, kontroler ne mora znati logiku
    @Override
    public List<Course> getNonEnrolledCourses(String username) {
        Optional<User> student = userRepository.findById(username);
        if (!student.isPresent()){
            return null;
        }

        return courseRepository.findAllByStudentsNotContaining(student.get());
    }

    @Override
    public List<Course> getAllCourses() {

        return courseRepository.findAll();

    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository
                .findAll()
                .stream()
                .filter((course -> course.getCourseId()==id))
                .findFirst();
    }

    @Override
    public Optional<Course> findByIdAndEnroll(Long id, String username) {
        Optional<User> student = userRepository.findById(username);
        if (!student.isPresent()){
            return Optional.empty();
        }
        return Optional.of(courseRepository.findByCourseIdAndStudentsContains(id,student.get()));
    }

    @Override
    public boolean exists(Long id) {
        return courseRepository.existsById(id);
    }

    @Override
    public void deleteCourse(Long id) {

        courseRepository.deleteById(id);
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }


}
