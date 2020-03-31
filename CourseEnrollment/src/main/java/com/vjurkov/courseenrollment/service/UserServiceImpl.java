package com.vjurkov.courseenrollment.service;

import com.vjurkov.courseenrollment.dao.UserRepository;
import com.vjurkov.courseenrollment.model.User;
import com.vjurkov.courseenrollment.utils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> getAllLecturers() {
        return userRepository.findAllByAuthority("LECTURER");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public void registerUser(String username, String password, boolean isLecturer) {

        String hash = bCryptPasswordEncoder.encode(password);
        UserFactory userFactory = new UserFactory();
        User user = isLecturer ?
                userFactory.getLecturerUser(username, hash) :
                userFactory.getStudentUser(username, hash);

        userRepository.save(user);
    }


}
