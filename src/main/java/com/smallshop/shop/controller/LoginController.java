package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final String EMAIL_ALREADY_USED_MESSAGE ="There is already a user registered with the email provided";
    private static final String SUCCESSFULLY_REGISTERED_MESSAGE = "User has been registered successfully";

    @Autowired
    UserService userService;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser (@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || userExists(user, bindingResult)){
            return "registration";
        }
        user = userService.createUser(user);
        model.addAttribute("successMessage", SUCCESSFULLY_REGISTERED_MESSAGE);
        return "redirect:/login";
    }

    private boolean userExists (User user, BindingResult bindingResult){
        boolean result = userService.getUserByUsername(user.getUsername()).isPresent();
        if (result) {
            bindingResult.rejectValue("email", "error.user", EMAIL_ALREADY_USED_MESSAGE);
        }
        return result;
    }
}
