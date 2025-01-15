package com.keola.microservice.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("address")
    private String address;
    @Column("password")
    private String password;

}