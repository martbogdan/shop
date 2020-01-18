package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart>findByUser (User user);
    Cart findByUserAndProduct (User user, Product product);
}
