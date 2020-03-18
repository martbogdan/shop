package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.*;
import com.smallshop.shop.exceptions.NotFound;
import com.smallshop.shop.service.OrderItemsService;
import com.smallshop.shop.service.OrderService;
import com.smallshop.shop.service.ProductService;
import com.smallshop.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
    @Autowired
    private ProductService productService;

    @PostMapping("/order")
    public String createOrder(@RequestParam String comment, @RequestParam OrderDeliveryCompany odc, @RequestParam String address, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);

        Order order = orderService.saveOrder(user, new Date(), comment, odc, address);
        orderItemsService.saveOrderItems(user, order);
        return "redirect:/user/orders";
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

    @GetMapping("/orderDetails/{orderId}")
    public String getOrderDetails(@PathVariable("orderId") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        List<OrderItems> orderItems = orderItemsService.getItemsByOrderId(order.getId());

        double roundedSum = sumOfOrderItems(orderItems);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("sumOfOrderProducts", roundedSum);
        model.addAttribute("order", order);
        return "order-details";
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private static double sumOfOrderItems (List<OrderItems> orderItems) {
        double sum = 0.00;
        for (OrderItems orderItem : orderItems) {
            sum = sum + (orderItem.getOrderPrice() * orderItem.getQuantity());
        }
        double roundedSum = round(sum, 2);
        return roundedSum;
    }
    @GetMapping("/clients-orders")
    public String getClientOrders (@RequestParam(defaultValue = "ACTIVE") OrderStatus orderStatus , Model model){
        Set<OrderStatus> orderStatuses = new TreeSet<>();
        orderStatuses.add(OrderStatus.ACTIVE); orderStatuses.add(OrderStatus.DONE);
        orderStatuses.add(OrderStatus.CANCELED); orderStatuses.add(OrderStatus.POSTPONED);
        orderStatuses.add(OrderStatus.SENT); orderStatuses.add(OrderStatus.ALL);
        model.addAttribute("orderStatuses", orderStatuses);

        List<Order> orderList = null;
        if (orderStatus.equals(OrderStatus.ALL)){
            orderList = orderService.getAllOrders();
        } else {
            orderList = orderService.getAllByStatus(orderStatus);
        }

        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getDateCreation().compareTo(o2.getDateCreation());
            }
        });
        model.addAttribute("allOrders", orderList);
        return "clients-orders";
    }
    @PostMapping("/clients-orders")
    public String addAdminComment (@RequestParam String adminComment, @RequestParam Long id,Model model){
        Order orderToUpdate = orderService.getOrderById(id);
        orderToUpdate.setAdminComment(adminComment);
        orderService.updateOrder(orderToUpdate);
        return "redirect:/clients-orders";
    }
    @PostMapping("/clients-orders/status")
    public String updateStatus (@RequestParam OrderStatus orderStatus, @RequestParam Long id,Model model){
        Order orderToUpdate = orderService.getOrderById(id);
        orderToUpdate.setOrderStatus(orderStatus);
        if (orderStatus.equals(OrderStatus.DONE)){
            List<OrderItems> orderItems = orderItemsService.getItemsByOrderId(id);
            for (OrderItems item : orderItems){
                Product product = productService.getProductById(item.getProduct().getId());
                product.setQuantity(product.getQuantity()-item.getQuantity());
                productService.updateProduct(product);
            }
        }
        orderService.updateOrder(orderToUpdate);
        return "redirect:/clients-orders";
    }
    @GetMapping("/print-order/{orderId}")
    public String getPrintOrderPage (@PathVariable("orderId") Long id, Model model) {
        List<OrderItems> orderItems = orderItemsService.getItemsByOrderId(id);
        model.addAttribute("currentDate", new Date());
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("orderItems", orderItems);
        double itemsSum = sumOfOrderItems(orderItems);
        model.addAttribute("sumOfOrderProducts", itemsSum);
        model.addAttribute("listSize", orderItems.size());
        return "print-order-page";
    }
}
