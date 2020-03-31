package com.vjurkov.courseenrollment.model;

import com.vjurkov.courseenrollment.utils.ClonePrototype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity // mapiranje u bazi
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course implements ClonePrototype<Course> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courseId")
    private long courseId;
    @Column(name = "name")
    @NotEmpty(message = "{validation.exercise.name.notEmpty}")
    @Size(min = 2, max = 90, message = "{validation.exercise.name.size}")
    private String name;

    @ManyToOne
    @JoinColumn(name = "username",nullable = false)
    private User lecturer;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "StudentEnrollment", joinColumns = @JoinColumn(name = "course"),inverseJoinColumns = @JoinColumn(name="student"))
    private List<User> students;

    //prototype pattern
    @Override
    public Course clone() {
        Course course = new Course();
        course.setName(this.name);
        course.setLecturer(this.lecturer);
        return course;
    }
}
