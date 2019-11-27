package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.service.CategoryService;
import com.smallshop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    public String getAllProducts (Model model){
        model.addAttribute("products", productService.getAll());
        return "products";
    }
    @GetMapping("/single/{productId}")
    public String getOneProduct (@PathVariable("productId") Long productId, Model model){
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("category", product.getCategory());
        return "single-product";
    }
    @GetMapping("/categories/{categoryId}")
    public String getAllByCategory (@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("categoryProducts", productService.getAllByCategory(category.getId()));
        System.out.println(productService.getAllByCategory(category.getId()));
        return "products-category";
    }


}
