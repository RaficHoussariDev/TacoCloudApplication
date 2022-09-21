package com.example.tacocloud.repositories.ingredient;

import com.example.tacocloud.models.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepositoryImpl implements IngredientRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return this.jdbcTemplate.query(
                "SELECT ID, NAME, TYPE FROM INGREDIENT",
                this::mapRowToIngredient
        );
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = this.jdbcTemplate.query(
                "SELECT ID, NAME, TYPE FROM INGREDIENT WHERE ID = ?",
                this::mapRowToIngredient,
                id
        );

        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        this.jdbcTemplate.update(
                "INSERT INTO INGREDIENT (ID, NAME, TYPE) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }
}
