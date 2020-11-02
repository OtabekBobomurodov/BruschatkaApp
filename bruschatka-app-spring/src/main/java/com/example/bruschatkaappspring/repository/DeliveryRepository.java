package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Archive;
import com.example.bruschatkaappspring.model.entity.Client;
import com.example.bruschatkaappspring.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {


    @Query("select coalesce(sum(amount),0) from Delivery where clientId=?1")
    Integer deliveryAmount(Integer id);

    @Query("from Delivery where deliveryDate=?1")
    List<Delivery> findAllByProductionDate(LocalDate date);
}
