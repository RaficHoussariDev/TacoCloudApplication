package com.example.tacocloud.repositories.order;

import com.example.tacocloud.models.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}