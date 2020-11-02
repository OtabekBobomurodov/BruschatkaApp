package com.example.bruschatkaappspring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(nullable = false)
    private Integer clientId;

    @Column(nullable = false)
    private String productName;

    public Delivery(Integer amount, LocalDate deliveryDate, Integer clientId, String productName) {
        this.amount = amount;
        this.deliveryDate = deliveryDate;
        this.clientId = clientId;
        this.productName = productName;
    }
}
