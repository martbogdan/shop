package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Cart;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.exceptions.NotFound;
import com.smallshop.shop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private DeliveryCompanyService deliveryCompanyService;

    @GetMapping()
    public String cart(/*@AuthenticationPrincipal User user,*/ Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);
        List<Product> products = new ArrayList<>();
        List<Cart> cartRows = cartService.getCartByUser(user);
        for (Cart cart : cartRows) {
            products.add(cart.getProduct());
        }
        double sum=0.00;
        for (Cart cart : cartRows) {
           sum = sum + (cart.getProduct().getPrice()*cart.getQuantity());
        }
        double roundedSum = UtilService.round(sum,2);
        String roundedSumStr = String.valueOf(roundedSum);
        if (roundedSumStr.substring(roundedSumStr.indexOf(".")).length()<3){
            roundedSumStr = roundedSumStr+"0";
        }
        model.addAttribute("cartProducts", products);
        model.addAttribute("cartRows", cartRows);
        model.addAttribute("sumOfCartProducts", roundedSumStr);
        model.addAttribute("cartSize", products.size());
        model.addAttribute("delivCompanies", deliveryCompanyService.getAllCompanies());
        log.info("work");
        return "cart";
    }

    //     @PostMapping(params = "deleteProduct")
//     public String deleteProduct (/*@AuthenticationPrincipal User user,*/ @RequestParam Long id, Model model){
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         String userName = authentication.getName();
//         User user = userService.getUserByUsername(userName).orElseThrow(NotFound::new);
//        cartService.delete(getCartByUserAndProductId(user, id));
//        cart(model);
//        model.addAttribute("message", "Product has been removed from the cart");
//        return "cart";
//     }
    @GetMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam Long id) {
        Cart deletedCart = cartService.getCartRawById(id);
        if (deletedCart != null) {
            cartService.delete(deletedCart.getId());
        }
        return "forward:/cart";
    }

    @GetMapping("/updateCartRaw/{rawId}")
    public String updateCartQuantity(@PathVariable("rawId") Long id, @RequestParam Integer qty) {
        Cart updateCart = cartService.getCartRawById(id);
        cartService.updateQuantity(updateCart, qty);
        log.info("updated");
        return "redirect:/cart";
    }


    private Cart getCartByUserAndProductId(User user, Long id) {
        Product product = productService.getProductById(id);
        Cart cart = cartService.getCartByUserAndProduct(user, product);
        return cart;
    }
}
