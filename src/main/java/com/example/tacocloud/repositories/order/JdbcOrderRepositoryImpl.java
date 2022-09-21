package com.example.tacocloud.repositories.order;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Taco;
import com.example.tacocloud.models.TacoOrder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class JdbcOrderRepositoryImpl implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    @Autowired
    public JdbcOrderRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        String query = "INSERT INTO Taco_Order"
                + " (delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatementCreatorFactory statement = new PreparedStatementCreatorFactory(
                query,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );

        statement.setReturnGeneratedKeys(true); // because we will need to fetch the saved order’s ID
        order.setPlacedAt(new Date());

        PreparedStatementCreator creator = statement.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            long tacoId = saveTaco(orderId, tacos.indexOf(taco) + 1, taco);
            log.info("Saved taco with id of: {}", tacoId);
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        String query = "INSERT INTO TACO"
                + " (name, created_at, taco_order, taco_order_key)"
                + " VALUES (?, ?, ?, ?)";

        PreparedStatementCreatorFactory statement = new PreparedStatementCreatorFactory(
                query,
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
        statement.setReturnGeneratedKeys(true);

        PreparedStatementCreator creator = statement.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long tacoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
        for(Ingredient ingredient: ingredients) {
            String query = "INSERT INTO Ingredient_Ref"
                    + " (ingredient, taco, taco_key)"
                    + " VALUES (?, ?, ?)";
            jdbcOperations.update(query, ingredient.getId(), tacoId, ingredients.indexOf(ingredient) + 1);
        }
    }
}
