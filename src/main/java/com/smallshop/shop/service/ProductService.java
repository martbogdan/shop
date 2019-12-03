package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.repository.ProductRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product getProductById (Long id){
        return productRepository.findById(id).orElseThrow(NotFound::new);
    }
    public Product getProductByName (String productName) {
        return productRepository.findProductByName(productName);
    }
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    public List<Product> getAllByCategory (Long id){
        return productRepository.findAllByCategory_Id(id);
    }
    public Product createProduct (Product product){
        return productRepository.save(product);
    }
    public Product createProduct (String prodName, Double price, String description, Integer quantity, Category category) {
        Product product = new Product();
        product.setName(prodName);
        product.setPrice(price);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setCategory(category);
        return productRepository.save(product);
    }
    public Product updateProduct (Product product) {
        Product productDB = productRepository.findById(product.getId()).get();
        if (!StringUtils.isEmpty(product.getName())) {
            productDB.setName(product.getName());
        }
        if (!StringUtils.isEmpty(product.getCategory())) {
            productDB.setCategory(product.getCategory());
        }
        if (!StringUtils.isEmpty(product.getDescription())) {
            productDB.setDescription(product.getDescription());
        }
        if (!StringUtils.isEmpty(product.getPrice())) {
            productDB.setPrice(product.getPrice());
        }
        if (!StringUtils.isEmpty(product.getQuantity())) {
            productDB.setQuantity(product.getQuantity());
        }
        if (!StringUtils.isEmpty(product.getPhoto())) {
            productDB.setPhoto(product.getPhoto());
        }
        return productRepository.save(productDB);
    }
}
