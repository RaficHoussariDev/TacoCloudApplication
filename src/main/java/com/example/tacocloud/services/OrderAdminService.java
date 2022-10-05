//package com.example.tacocloud.services;
//
//import com.example.tacocloud.models.TacoOrder;
//import com.example.tacocloud.repositories.order.OrderRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OrderAdminService {
//    private final OrderRepository orderRepository;
//
//    @Autowired
//    public OrderAdminService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }
//
//    // If this method is being called by more than one controller, using this annotation is better than tracking all the URIs that are calling this method
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteAllOrders() {
//        this.orderRepository.deleteAll();
//    }
//
//    // The method will be called and the response will be fetched, but the response will only be shown if the condition inside the annotation will be met
//    @PostAuthorize("hasRole('ADMIN') || returnObject.getUser().getUsername() == authentication.name")
//    public TacoOrder getOrder(long id) {
//        return new TacoOrder();
//    }
//}
