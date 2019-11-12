package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findUserByEmail (String email);
        User findUserByFirstName (String firstName);

}
