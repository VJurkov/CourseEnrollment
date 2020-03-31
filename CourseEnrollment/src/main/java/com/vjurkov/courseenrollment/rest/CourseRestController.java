package com.vjurkov.courseenrollment.rest;

import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.model.User;
import com.vjurkov.courseenrollment.security.SecurityUtils;
import com.vjurkov.courseenrollment.service.CourseService;
import com.vjurkov.courseenrollment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


//rest kontroler  vrača json - reprezentacija objekta u stringu
@RestController
// ova klasa ce vratitti json
//path root kontrolera
@RequestMapping(path="/api/course", produces="application/json")
public class CourseRestController {

    //umjesto instanciranja
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;


    @GetMapping
    //mapira metodu na get rikvest /api/course
    public Iterable<Course> findAll(){

        return SecurityUtils.isLecturer() ? courseService.getAllCourses() : courseService.getAllCourses(SecurityUtils.getUsername());
    }
    @GetMapping("/all")
    //mapira metodu na get rikvest /api/course
    public Iterable<Course> findAllCourses(){
        return  courseService.getAllCourses();
    }

    //zna pretvoriti objekt u json, kao ovdje course objekt koji je instanca te klasa i to onda postam vidi
    @GetMapping("/{id}") //get s parametrom id
    public ResponseEntity<Course> findOne(@PathVariable Long id){
        //ako je nastavnik onda daj bilo koji kors po id-u ako je student onda daj ono u sta je student enrollan
        Optional<Course> course = SecurityUtils.isLecturer() ? courseService.findById(id) : courseService.findByIdAndEnroll(id, SecurityUtils.getUsername());
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //post je za spremanje u
    //koju ce http status vratiti metoda ako uspije
    @ResponseStatus(HttpStatus.CREATED)
    //ta metoda očekuje da upišemo json
    @PostMapping(consumes="application/json")
    // radim post mapping na api/course
    @PreAuthorize("hasAuthority('LECTURER')")
    public Course save(@Valid @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    //update
    @PutMapping(value = "/{id}", consumes="application/json")
    @PreAuthorize("hasAuthority('LECTURER')")
    public ResponseEntity<Course> update(@PathVariable Long id, @Valid @RequestBody Course updatedCourse){
        //vreper oko neke klase tako da klasa može biti null
        Optional<Course> course = courseService.findById(id);
        Optional<User> lecturer = userService.findByUsername(updatedCourse.getLecturer().getUserName());
        if(lecturer.isPresent()){
            course.ifPresent(value -> {
                value.setName(updatedCourse.getName());
                value.setLecturer(lecturer.get());

                courseService.addCourse(value);
            });
            return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LECTURER')")
    public void delete(@PathVariable Long id){
        boolean exists = courseService.exists(id);
        if(exists){
            courseService.deleteCourse(id);
        }
    }
}