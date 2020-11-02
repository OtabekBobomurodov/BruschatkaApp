package com.example.bruschatkaappspring.repository;

import com.example.bruschatkaappspring.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
