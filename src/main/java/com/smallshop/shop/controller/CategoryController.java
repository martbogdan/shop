package com.smallshop.shop.controller;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String getCategoryList (Model model) {
        model.addAttribute("categories", categoryService.getAllCatagories());
        model.addAttribute("newCategory",new Category());
        return "categories";
    }

    @PostMapping("/addCategory")
    public String addCategory (@ModelAttribute("newCategory") Category newCategory, RedirectAttributes model) {

        Category categoryDB = categoryService.getCategoryByName(newCategory.getCategoryName());
        if (categoryDB != null){
            model.addFlashAttribute("category_error", "Category already exists");
            return "redirect:/user/profile";
        }

            model.addAttribute("newCategory", categoryService.createNewCategory(newCategory.getCategoryName(), newCategory.getCategoryDescription()));
            model.addFlashAttribute("category_error", "Category added successfully");

        return "redirect:/user/profile";
    }
}
