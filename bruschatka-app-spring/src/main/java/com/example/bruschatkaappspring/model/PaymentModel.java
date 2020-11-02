package com.example.bruschatkaappspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private Integer amount, totalPayment;
    private LocalDate paymentDate;
    private String payTypeName;
}
