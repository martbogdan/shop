package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.Order;
import com.smallshop.shop.dao.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository <OrderItems, Long> {
    List<OrderItems> findByOrder (Order order);
}
