package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.service.CategoryService;
import com.smallshop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/addProduct")
    public String addNewProduct (@ModelAttribute("newProduct") Product newProduct, RedirectAttributes model){
        Product productDB = productService.getProductByName(newProduct.getName());
        if (productDB != null) {
            model.addFlashAttribute("product_error", "Product already exists");
            return "redirect:/user/profile";
        }
        model.addAttribute("newProduct", productService.createProduct(
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getDescription(),
                newProduct.getQuantity(),
                newProduct.getCategory()));
        model.addFlashAttribute("product_error", "Product added successfully");
        return "redirect:/user/profile";
    }


}
