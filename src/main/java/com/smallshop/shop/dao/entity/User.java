package com.smallshop.shop.dao.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Size(min = 1, max = 100)
    private String username;
    @NotBlank(message = "Must not be blank")
    @Size(min = 1, max = 100)
    private String firstName;
    @NotBlank(message = "Must not be blank")
    @Size(min = 1, max = 100)
    private String lastName;
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be a valid email address")
    private String email;
    @NotBlank(message = "Must not be blank")
    @Size(min = 10, max = 12)
    private String phoneNumber;
    @NotBlank(message = "Must not be blank")
    @Size(min = 1, max = 100)
    private String password;
    private boolean active;
    private Date dateRegistration;

    private String photo;
    private Date dob;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

}
