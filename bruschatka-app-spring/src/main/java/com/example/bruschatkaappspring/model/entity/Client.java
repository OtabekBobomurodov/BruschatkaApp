package com.example.bruschatkaappspring.model.entity;

import com.example.bruschatkaappspring.model.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 13)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate orderDate;


    public Client(String name, String product, Integer amount, Integer price, String phoneNumber, String address, LocalDate orderDate) {
        this.name = name;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderDate = orderDate;
    }
}
