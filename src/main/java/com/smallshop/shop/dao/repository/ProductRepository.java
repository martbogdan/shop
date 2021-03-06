package com.smallshop.shop.dao.repository;

import com.smallshop.shop.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id (Long id);
    Product findByName (String productName);
}
