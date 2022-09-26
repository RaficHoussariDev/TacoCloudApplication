package com.example.tacocloud.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be a least 5 characters long")
    private String name;

    @ManyToMany()
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt = new Date();

    private void addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
