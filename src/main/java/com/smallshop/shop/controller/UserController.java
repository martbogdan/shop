package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.entity.UserRole;
import com.smallshop.shop.exceptions.NotFound;
import com.smallshop.shop.service.CategoryService;
import com.smallshop.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    @Qualifier("basePath")
    private String basePath;

    @ModelAttribute("user")
    public User activeUser (Authentication authentication) {
        return userService.getUserByUsername(authentication.getName()).get();
    }

    @GetMapping("profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(NotFound::new);
        if(user.getRole().equals(Collections.singleton(UserRole.ADMIN))) {
            model.addAttribute("categories", categoryService.getAllCatagories());
            return "admin-page";
        }
        return "user-profile";
    }
    @PostMapping("uploadPhoto")
    public String uploadPhoto (@RequestParam("file")MultipartFile file, @ModelAttribute("user") User user){
        String uploadName = file.getOriginalFilename();

        try {
            File transferFile = new File(basePath+"/"+uploadName);
            file.transferTo(transferFile);
            log.info("Saved into {}", transferFile.getPath());
            user.setPhoto(uploadName);
            userService.updateUser(user);
        } catch (IOException e) {
            log.error("Error saving file", e);
        }
        return "redirect:/user/profile";
    }
}