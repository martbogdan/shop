package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.*;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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

    @GetMapping("/user/orders")
    public String getOrdersByUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);
        List<Order> orders = orderService.getAllByUserId(user.getUserId());
        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getDateCreation().compareTo(o1.getDateCreation());
            }
        });
        model.addAttribute("orders", orders);
        return "user-orders";
    }

    @GetMapping("/orderDetails")
    public String getOrderDetails(@RequestParam Long id, Model model) {
        Order order = orderService.getOrderById(id);
        List<OrderItems> orderItems = orderItemsService.getItemsByOrderId(order.getId());
        double sum = 0.00;
        for (OrderItems orderItem : orderItems) {
            sum = sum + (orderItem.getOrderPrice() * orderItem.getQuantity());
        }
        double roundedSum = round(sum, 2);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("sumOfOrderProducts", roundedSum);
        return "order-details";
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
