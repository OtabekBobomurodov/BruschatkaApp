package com.example.bruschatkaappspring.model.entity;

import com.example.bruschatkaappspring.model.enums.IngredientName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private IngredientName name;

    @Column(nullable = false)
    private Integer amount;
}
