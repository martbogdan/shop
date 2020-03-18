package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.repository.CartRepository;
import com.smallshop.shop.exceptions.NotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CartService {
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart getCartByUserAndProduct(User user, Product product) {
        return cartRepository.findByUserAndProduct(user, product);
    }

    public Cart getCartRawById(Long id) {
        return cartRepository.findById(id).orElseThrow(NotFound::new);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    public void delete(Long id) {
        Optional<Cart> toDelete = cartRepository.findById(id);
        if (toDelete.isPresent()) {
            cartRepository.delete(toDelete.get());
        }
    }

    public Cart save(User user, Product product) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(1);
        return cartRepository.save(cart);
    }

    public Cart updateQuantity(Cart cart, String qty) {
        Cart cartDB = cartRepository.findById(cart.getId()).orElseThrow(NotFound::new);
        if (qty==null || qty.equals("")){
            cartDB.setQuantity(cartDB.getQuantity());
        } else  {
            try {
                int q = (int) Double.parseDouble(qty);
                cartDB.setQuantity(Math.abs(q));
            }catch (NumberFormatException e){
                log.error(e.getMessage());
//                e.printStackTrace();
                cartDB.setQuantity(cartDB.getQuantity());
            }
        }

        return cartRepository.save(cartDB);
    }
}
