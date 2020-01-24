package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Order;
import com.smallshop.shop.dao.entity.OrderStatus;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.repository.OrderRepository;
import com.smallshop.shop.dao.repository.UserRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }
    public Order getOrderById (Long id) {
        return orderRepository.findById(id).orElseThrow(NotFound::new);
    }
    public List<Order> getAllByUserId (Long userId) {
        return orderRepository.findAllByUser_UserId(userId);
    }
    public Order saveOrder (User user, Date date, String comment) {
        Order newOrder = new Order();
        newOrder.setDateCreation(date);
        newOrder.setUser(user);
        newOrder.setComment(comment);
        newOrder.setOrderStatus(OrderStatus.ACTIVE);
        return orderRepository.save(newOrder);
    }
}
