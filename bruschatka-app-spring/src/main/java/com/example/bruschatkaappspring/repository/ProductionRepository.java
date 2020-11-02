package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Integer> {
    @Query("select name, sum(amount) as amount, productionDate from Production where productionDate=?1 group by name, productionDate")
    List<?> findAllByProductionDate(LocalDate date);

    @Query("select coalesce(sum(amount),0) from Production where productionDate=?1")
    Integer totalAmount(LocalDate date);
}
