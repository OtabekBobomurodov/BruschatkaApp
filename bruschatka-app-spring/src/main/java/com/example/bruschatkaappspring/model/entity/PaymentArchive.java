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
@Table(name = "paymentArchive")
public class PaymentArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    private PayType payType;

    public PaymentArchive(Integer amount, LocalDate paymentDate, String clientName, String clientPhone, PayType payType) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.payType = payType;
    }
}
