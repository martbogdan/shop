package com.smallshop.shop.dao.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "Must not be blank")
    private String username;
    @NotBlank(message = "Must not be blank")
    private String firstName;
    @NotBlank(message = "Must not be blank")
    private String lastName;
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be a valid email address")
    private String email;
    @NotBlank(message = "Must not be blank")
    private String password;
    private boolean active;

    private String photo;
    private Date dob;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

}
