package com.keola.microservice.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_customer")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String address;
    private String password;

}