package com.vjurkov.courseenrollment.service;


import com.vjurkov.courseenrollment.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllLecturers();

    Optional<User> findByUsername(String username);

    void registerUser(String username, String password, boolean isLecturer);
}
