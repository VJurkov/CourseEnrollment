package com.vjurkov.courseenrollment.service;

import com.vjurkov.courseenrollment.dao.StudentEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    StudentEnrollmentRepository studentEnrollmentRepository;

    @PreAuthorize("hasAuthority('STUDENT')")
    @Override
    public void enrollStudent(Long courseId, String studentId) {

        studentEnrollmentRepository.enrollStudent(courseId, studentId);

    }
}
