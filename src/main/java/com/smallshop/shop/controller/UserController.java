package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("basePath")
    private String basePath;

    @ModelAttribute("user")
    public User activeUser (Authentication authentication) {
        return userService.getUserByUsername(authentication.getName()).get();
    }

    @GetMapping("profile")
    public String userProfile() {
        return "user-profile";
    }
}
