package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.service.CategoryService;
import com.smallshop.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    @Qualifier("basePath")
    private String basePath;
    @Autowired
    @Qualifier("productPath")
    private String productPath;

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
        log.info("Added new product: " + newProduct.toString());
        return "redirect:/user/profile";
    }

    @PostMapping("uploadProductPhoto/{id}")
    public String uploadPhoto (@PathVariable("id") Long id,
                               @RequestParam("file")MultipartFile file,
                               @ModelAttribute("product") Product product) {
        String uploadName = file.getOriginalFilename();
        try {
            File transferFile = new File(productPath+"/"+uploadName);
            file.transferTo(transferFile);
            log.info("Saved into {}", transferFile.getPath());
            product.setPhoto(uploadName);
            productService.updateProduct(product);
        } catch (IOException e) {
            log.error("Error saving file", e);
        }
       return "redirect:/product/all";
    }

    @GetMapping("deleteProduct/{id}")
    public String deleteProduct (@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        Product productToDelete = productService.getProductById(id);
        if (productToDelete != null) {
            productService.delete(id);
            log.info("Deleted product: " + productToDelete.toString());
        }
        return "redirect:/product/all";
    }

}