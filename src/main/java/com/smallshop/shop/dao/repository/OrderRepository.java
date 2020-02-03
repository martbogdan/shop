package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.Order;
import com.smallshop.shop.dao.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_UserId (Long userId);
    List<Order> findAllByOrderStatus (OrderStatus status);
}
