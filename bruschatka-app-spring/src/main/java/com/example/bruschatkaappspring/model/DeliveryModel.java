package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryModel {
    private Integer id;
    private Integer amount;
    private LocalDate date;
    private String clientName;
    private String clientPhone;
    private String productName;
    private String address;
}
