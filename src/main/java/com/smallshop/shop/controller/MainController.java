package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    UserService userService;

//    @ModelAttribute("user")
//    public User activeUser (Authentication authentication){
//        return userService.getUserByUsername(authentication.getName()).get();
//    }

    @GetMapping("/")
    public String getMainPage (@RequestParam(name = "email", required = false, defaultValue = "products")
                               String email, Model model){
        model.addAttribute("email", email);
        return "index";
    }
}
