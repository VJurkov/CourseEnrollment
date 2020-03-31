package com.vjurkov.courseenrollment.dao;

import com.vjurkov.courseenrollment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByAuthority(String authority);
}
