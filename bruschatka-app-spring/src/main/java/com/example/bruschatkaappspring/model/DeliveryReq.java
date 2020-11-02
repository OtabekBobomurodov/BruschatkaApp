package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryReq {
    private Integer id;
    private Integer amount;
    private LocalDate deliveryDate = LocalDate.now();

    private String productName;

}
