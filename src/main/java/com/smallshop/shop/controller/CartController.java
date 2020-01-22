package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.exceptions.NotFound;
import com.smallshop.shop.service.CartService;
import com.smallshop.shop.service.ProductService;
import com.smallshop.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Security;
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
    private UserService userService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public String cart (/*@AuthenticationPrincipal User user,*/ Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);
        List<Product> products = new ArrayList<>();
        Iterable<Cart> cartRows = cartService.getCartByUser(user);
        for (Cart cart : cartRows) {
            products.add(cart.getProduct());
        }
        model.addAttribute("cartProducts", products);
        model.addAttribute("cartRows", cartRows);
        return "cart";
     }

     @PostMapping(params = "deleteProduct")
     public String deleteProduct (/*@AuthenticationPrincipal User user,*/ @RequestParam Long id, Model model){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String userName = authentication.getName();
         User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);
        cartService.delete(getCartByUserAndProductId(user, id));
        cart(model);
        model.addAttribute("message", "Product has been removed from the cart");
        return "cart";
     }


     private Cart getCartByUserAndProductId (User user, Long id){
        Product product = productService.getProductById(id);
        Cart cart = cartService.getCartByUserAndProduct(user, product);
        return cart;
     }
}
