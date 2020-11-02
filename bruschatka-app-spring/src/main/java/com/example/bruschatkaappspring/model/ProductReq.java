package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    private Integer id;
    private String name;
    private Integer amount;
    private LocalDate productionDate = LocalDate.now();
}
