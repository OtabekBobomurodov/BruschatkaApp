package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.ClientModel;
import com.example.bruschatkaappspring.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Page<Client> findAllByNameContainingIgnoreCaseOrProductContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(String name, String productName, String phoneNumber, Pageable pageable);


}
