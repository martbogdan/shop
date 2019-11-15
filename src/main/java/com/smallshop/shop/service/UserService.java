package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.entity.UserRole;
import com.smallshop.shop.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    public User getUserByEmail (String email) {
//        return userRepository.findUserByEmail(email);
//    }
    public Optional<User> getUserByUsername (String username){
        return userRepository.findUserByUsername(username);
    }
    public Optional<User> getUserByEmail (String email) {
        return userRepository.findUserByEmail(email);
    }
    public User getUserByName (String firstName) {
        return userRepository.findUserByFirstName(firstName);
    }

    public User createUser (User user){
        user.setActive(true);
        user.setRole(Collections.singleton(UserRole.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createAdmin (User user) {
        user.setActive(true);
        user.setRole(Collections.singleton(UserRole.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
