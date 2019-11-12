package com.smallshop.shop.controller;

import com.smallshop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/all")
    public String getAllProducts (Model model){
        model.addAttribute("products", productService.getAll());
        return "products";
    }
}
