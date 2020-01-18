package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.repository.CartRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartByUser (User user){
        return cartRepository.findByUser(user);
    }
    public Cart getCartByUserAndProduct (User user, Product product){
        return cartRepository.findByUserAndProduct(user, product);
    }

    public void delete (Cart cart){
        cartRepository.delete(cart);
    }
    public Cart save (User user, Product product){
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(1);
        return cartRepository.save(cart);
    }
    public Cart updateQuantity (Cart cart){
        Cart cartDB = cartRepository.findById(cart.getId()).orElseThrow(NotFound::new);
        if (!StringUtils.isEmpty(cart.getQuantity())){
            cartDB.setQuantity(cart.getQuantity());
        }
        return cartDB;
    }
}
