package com.platzi.pizza.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer idCustomer;

    @Column(nullable = false, length = (60))
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String phoneNumber;
}
