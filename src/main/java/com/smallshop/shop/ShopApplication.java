package com.smallshop.shop;

import com.smallshop.shop.dao.entity.Category;
import com.smallshop.shop.dao.entity.Product;
import com.smallshop.shop.dao.entity.User;
import com.smallshop.shop.dao.entity.UserRole;
import com.smallshop.shop.dao.repository.CategoryRepository;
import com.smallshop.shop.dao.repository.ProductRepository;
import com.smallshop.shop.dao.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo (UserRepository userRepository, CategoryRepository categoryRepository,
//								   ProductRepository productRepository){
//		return (args) -> {
//			User u = new User();
//			u.setUsername("bob");
//			u.setPassword("q");
//			u.setActive(true);
//			u.setRole(Collections.singleton(UserRole.USER));
//			u.setFirstName("bob");
//			u.setLastName("bob");
//			u.setEmail("b@b");
//			userRepository.save(u);
//
//			User u1 = new User();
//			u1.setUsername("q");
//			u1.setPassword("q");
//			u1.setActive(true);
//			u1.setRole(Collections.singleton(UserRole.USER));
//			u1.setFirstName("q");
//			u1.setLastName("q");
//			u1.setEmail("q@q");
//			userRepository.save(u1);
//
//			Category c = new Category();
//			c.setCategoryName("Fruits");
//			c.setCategoryDescription("All fruits");
//			c.setProducts(productRepository.findAllByCategory_Id(c.getId()));
//			categoryRepository.save(c);
//
//			Product p = new Product();
//			p.setName("Banana");
//			p.setPrice(30.00);
//			p.setDescription("Yellow banana");
//			p.setCategory(c);
//			p.setQuantity(100);
//			productRepository.save(p);
//
//			Product ap = new Product();
//			ap.setName("Apple");
//			ap.setPrice(15.99);
//			ap.setDescription("Red apple");
//			ap.setCategory(c);
//			ap.setQuantity(150);
//			productRepository.save(ap);
//		};
//	}

}
