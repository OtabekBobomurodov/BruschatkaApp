package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientModel {
    private Integer id;
    private String name;
    private String productName;
    private Integer amount;
    private Integer price;
    private String phoneNumber;
    private String address;
    private Integer payments;
    private Integer balance;
    private Integer deliveredAmount;
    private LocalDate orderDate = LocalDate.now();
}
