package com.example.bruschatkaappspring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private PayType payType;

    public Payment(Integer amount, LocalDate paymentDate, Client client, PayType payType) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.client = client;
        this.payType = payType;
    }
}
