package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository <OrderItems, Long> {
}
