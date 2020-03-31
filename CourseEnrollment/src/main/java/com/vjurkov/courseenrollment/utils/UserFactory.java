package com.vjurkov.courseenrollment.utils;

import com.vjurkov.courseenrollment.model.User;
//unutra je builder pattern
//factory klasa, dvije helper metode koje rade novog studenta ili predavaca
//ti useri se rade preko builder patterna
public class UserFactory {

    public User getLecturerUser(String username, String password){

        return new User.Builder()
                .withUsername(username)
                .withPassword(password)
                .asLecturer()
                .build();
    }

    public User getStudentUser(String username, String password){

        return new User.Builder()
                .withUsername(username)
                .withPassword(password)
                .asStudent()
                .build();
    }

}
