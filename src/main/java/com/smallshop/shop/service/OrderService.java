package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Order;
import com.smallshop.shop.dao.entity.OrderDeliveryCompany;
import com.smallshop.shop.dao.entity.OrderStatus;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.repository.OrderRepository;
import com.smallshop.shop.dao.repository.UserRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<Order> getAllByStatus (OrderStatus status) {
        return orderRepository.findAllByOrderStatus(status);
    }
    public Order getOrderById (Long id) {
        return orderRepository.findById(id).orElseThrow(NotFound::new);
    }
    public List<Order> getAllByUserId (Long userId) {
        return orderRepository.findAllByUser_UserId(userId);
    }
    public Order saveOrder (User user, Date date, String comment, OrderDeliveryCompany odc, String address) {
        Order newOrder = new Order();
        newOrder.setDateCreation(date);
        newOrder.setUser(user);
        newOrder.setComment(comment);
        newOrder.setOrderStatus(OrderStatus.ACTIVE);
        newOrder.setOrderDeliveryCompany(odc);
        newOrder.setDeliveryAddress(address);
        return orderRepository.save(newOrder);
    }
    public Order updateOrder (Order order){
        Order orderDB = orderRepository.findById(order.getId()).orElseThrow(NotFound::new);
        if (!StringUtils.isEmpty(order.getDeliveryAddress())) {
            orderDB.setDeliveryAddress(order.getDeliveryAddress());
        }
        if (!StringUtils.isEmpty(order.getOrderDeliveryCompany())){
            orderDB.setOrderDeliveryCompany(order.getOrderDeliveryCompany());
        }
        if (!StringUtils.isEmpty(order.getOrderStatus())){
            orderDB.setOrderStatus(order.getOrderStatus());
        }
        if (!StringUtils.isEmpty(order.getAdminComment())){
            orderDB.setAdminComment(order.getAdminComment());
        }
        return orderRepository.save(orderDB);
    }
}
