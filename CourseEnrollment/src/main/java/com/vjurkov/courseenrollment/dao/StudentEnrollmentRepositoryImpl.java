package com.vjurkov.courseenrollment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentEnrollmentRepositoryImpl implements StudentEnrollmentRepository {

    JdbcTemplate template;
    SimpleJdbcInsert insert;
    @Autowired
    public StudentEnrollmentRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("student_enrollment");
    }

    @Override
    public void enrollStudent(Long courseId, String studentId) {

        Map<String, Object> values = new HashMap<>();

        values.put("course", courseId);
        values.put("student", studentId);

        insert.execute(values);
    }
}
