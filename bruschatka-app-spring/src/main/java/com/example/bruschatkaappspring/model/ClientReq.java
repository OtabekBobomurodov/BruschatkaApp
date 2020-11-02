package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReq {
    private String name, phoneNumber, address, productName;
    private Integer productId, amount, price;
    private LocalDate orderDate = LocalDate.now();
}
