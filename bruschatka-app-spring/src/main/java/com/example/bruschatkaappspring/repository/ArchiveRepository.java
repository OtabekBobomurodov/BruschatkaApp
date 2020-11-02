package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Archive;
import com.example.bruschatkaappspring.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Integer> {
    Page<Archive> findAllByNameContainingIgnoreCaseOrProductContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(String name, String productName, String phoneNumber, Pageable pageable);

}
