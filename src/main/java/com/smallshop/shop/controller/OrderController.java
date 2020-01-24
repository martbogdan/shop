package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Order;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.exceptions.NotFound;
import com.smallshop.shop.service.OrderItemsService;
import com.smallshop.shop.service.OrderService;
import com.smallshop.shop.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@Slf4j
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemsService orderItemsService;

    @PostMapping("/order")
    public String createOrder(@RequestParam String comment, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);

        Order order = orderService.saveOrder(user, new Date(), comment);
        orderItemsService.saveOrderItems(user, order);
        return "index";
    }
}
