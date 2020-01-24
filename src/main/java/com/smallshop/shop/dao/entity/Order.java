package com.smallshop.shop.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_creation")
    private Date dateCreation;
    private String comment;
    @ManyToOne
    private User user;
    private OrderStatus orderStatus;
}
