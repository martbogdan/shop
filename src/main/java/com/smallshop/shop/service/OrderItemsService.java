package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.*;
import com.smallshop.shop.dao.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemsService {
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    @Autowired
    private CartService cartService;

    public List<OrderItems> saveOrderItems(User user, Order order) {
        List<Cart> carts = cartService.getCartByUser(user);
        List<OrderItems> orderItems = new ArrayList<>();
        if (carts.size() > 0) {
            for (Cart cart : carts) {
                orderItems.add(saveOneItem(order, cart.getProduct(), cart.getQuantity()));
                cartService.delete(cart.getId());
            }

        }
        return orderItemsRepository.saveAll(orderItems);
    }

    private OrderItems saveOneItem(Order order, Product product, Integer quantity) {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setOrderPrice(product.getPrice());
        return orderItemsRepository.save(orderItem);
    }

    public List<OrderItems> getItemsByOrder(Order order) {
        return orderItemsRepository.findByOrder(order);
    }

    public List<OrderItems> getItemsByOrderId(Long orderId) {
        return orderItemsRepository.findAllByOrderId(orderId);
    }
}
