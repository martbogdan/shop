package com.smallshop.shop.dao.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private String categoryDescription;
    @ToString.Exclude
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + id +
//                ", categoryName='" + categoryName + '\'' +
//                ", categoryDescription='" + categoryDescription + '\'' +
//                ", products=" + products +
//                '}';
//    }
}
