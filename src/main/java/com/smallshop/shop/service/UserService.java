package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.entity.UserRole;
import com.smallshop.shop.dao.repository.UserRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getUserByUsername (String username){
        return userRepository.findUserByUsername(username);
    }
    public Optional<User> getUserByEmail (String email) {
        return userRepository.findUserByEmail(email);
    }
    public User getUserByName (String firstName) {
        return userRepository.findUserByFirstName(firstName);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFound::new);
    }

    public List<User> getAllUsers () {
        return userRepository.findAll();
    }

    public User createUser (User user){
        user.setActive(true);
        user.setRole(Collections.singleton(UserRole.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateRegistration(new Date());
        return userRepository.save(user);
    }

    public User createAdmin (User user) {
        user.setActive(true);
        user.setRole(Collections.singleton(UserRole.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser (User user) {
        User userDB = userRepository.findById(user.getUserId()).orElseThrow(NotFound::new);
        if (!StringUtils.isEmpty(user.getFirstName())) {
            userDB.setFirstName(user.getFirstName());
        }
        if (!StringUtils.isEmpty(user.getLastName())) {
            userDB.setLastName(user.getLastName());
        }
        if (!StringUtils.isEmpty(user.getEmail())) {
            userDB.setEmail(user.getEmail());
        }
        if (!StringUtils.isEmpty(user.getDob())) {
            userDB.setDob(user.getDob());
        }
        if (!StringUtils.isEmpty(user.getPhoto())) {
            userDB.setPhoto(user.getPhoto());
        }
        if (!StringUtils.isEmpty(user.getRole())) {
            userDB.setRole(user.getRole());
        }
        if (!StringUtils.isEmpty(user.getUsername())) {
            userDB.setUsername(user.getUsername());
        }
        if (!StringUtils.isEmpty(user.getPhoneNumber())) {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }
        userRepository.save(userDB);
        return userDB;
    }

    public User updateUserRole (Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFound::new);
        User user1 = new User();
        user1.setUserId(user.getUserId());
        user1.setUsername(user.getUsername());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setPassword(user.getPassword());
        user1.setActive(true);
        user1.setDateRegistration(user.getDateRegistration());
        user1.setPhoto(user.getPhoto());
        user1.setDob(user.getDob());
        user1.setOrders(user.getOrders());
        if (user.getRole().equals(Collections.singleton(UserRole.USER))) {
            user1.setRole(Collections.singleton(UserRole.ADMIN));
        } else if (user.getRole().equals(Collections.singleton(UserRole.ADMIN))) {
            user1.setRole(Collections.singleton(UserRole.USER));
        }
        return userRepository.save(user1);
    }
}