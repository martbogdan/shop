package com.smallshop.shop.dao.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    @Column(name = "description",length = 255000)
    @Size(max = 255000)
    private String description;

    @ToString.Exclude
    @ManyToOne
    private Category category;

    private String photo;
}
