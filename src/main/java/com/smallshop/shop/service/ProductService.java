package com.smallshop.shop.service;

import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.repository.ProductRepository;
import com.smallshop.shop.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product getProductById (Long id){
        return productRepository.findById(id).orElseThrow(NotFound::new);
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
}
