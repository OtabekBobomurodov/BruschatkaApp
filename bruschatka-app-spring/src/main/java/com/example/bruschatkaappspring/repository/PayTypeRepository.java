package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.PayType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayTypeRepository extends JpaRepository<PayType, Integer> {
}
