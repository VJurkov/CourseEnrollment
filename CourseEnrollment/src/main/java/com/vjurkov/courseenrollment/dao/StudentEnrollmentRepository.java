package com.vjurkov.courseenrollment.dao;

public interface StudentEnrollmentRepository {

    void enrollStudent(Long courseId, String studentId);
}
