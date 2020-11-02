package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.ClientModel;
import com.example.bruschatkaappspring.model.entity.Client;
import com.example.bruschatkaappspring.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("select COALESCE(sum(amount), 0) from Payment where client=?1")
    Integer findAmountByClient(Client client);

    List<Payment> findAllByClientId(Integer id);


    void deleteAllByClientId(Integer id);

}
