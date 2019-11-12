package com.smallshop.shop.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_creation")
    private LocalDateTime dateCreation;
    private String comment;
    @ManyToOne
    private User user;
}
