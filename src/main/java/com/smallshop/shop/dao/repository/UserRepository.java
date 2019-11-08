package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
