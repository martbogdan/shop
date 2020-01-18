package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.service.CartService;
import com.smallshop.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public String cart (@AuthenticationPrincipal User user, Model model){
        List<Product> products = new ArrayList<>();
        Iterable<Cart> cartRows = cartService.getCartByUser(user);
        for (Cart cart : cartRows) {
            products.add(cart.getProduct());
        }
        model.addAttribute("products", products);
        return "cart";
     }

     @PostMapping(params = "deleteProduct")
     public String deleteProduct (@AuthenticationPrincipal User user, @RequestParam Long id, Model model){
        cartService.delete(getCartByUserAndProductId(user, id));
        cart(user, model);
        model.addAttribute("message", "Product has been removed from the cart");
        return "cart";
     }


     private Cart getCartByUserAndProductId (User user, Long id){
        Product product = productService.getProductById(id);
        Cart cart = cartService.getCartByUserAndProduct(user, product);
        return cart;
     }
}
