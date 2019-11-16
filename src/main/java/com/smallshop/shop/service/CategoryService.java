package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.repository.CategoryRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCatagories () {
        return categoryRepository.findAll();
    }

    public Category getCategoryById (Long id) {
        return categoryRepository.findById(id).orElseThrow(NotFound::new);
    }

    public Category getCategoryByName (String name){
        return categoryRepository.findCategoryByCategoryName(name);
    }

    public Category createNewCategory (Category category) {
        return categoryRepository.save(category);
    }

    public Category createNewCategory (String categoryName, String description) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryDescription(description);
        return categoryRepository.save(category);
    }

    private Category updateCategory (Category category) {
        Category categoryDB = categoryRepository.findById(category.getId()).orElseThrow(NotFound::new);
        if (!StringUtils.isEmpty(category.getCategoryName())){
            categoryDB.setCategoryName(category.getCategoryName());
        }
        if (!StringUtils.isEmpty(category.getCategoryDescription())){
            categoryDB.setCategoryDescription(category.getCategoryDescription());
        }
        return categoryDB;
    }

    public Category createUpdate (Category category) {
        Category categoryToSave = category.getId() == null ? createNewCategory(category) : updateCategory(category);
        return categoryRepository.save(categoryToSave);
    }
}
