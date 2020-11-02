package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Archive;
import com.example.bruschatkaappspring.model.entity.Client;
import com.example.bruschatkaappspring.model.entity.Payment;
import com.example.bruschatkaappspring.model.entity.PaymentArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentArchiveRepository extends JpaRepository<PaymentArchive, Integer> {
    @Query("select COALESCE(sum(amount), 0) from PaymentArchive where clientPhone=?1")
    Integer findAmountByArchive(String phone);

    List<PaymentArchive> findAllByClientPhone(String phone);
}
