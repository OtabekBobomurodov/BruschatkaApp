package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository <Stock, Integer> {
    Stock findByName(String name);
}
