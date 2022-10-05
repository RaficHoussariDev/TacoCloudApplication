//package com.example.tacocloud.controllers;
//
//import com.example.tacocloud.services.OrderAdminService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//    private final OrderAdminService orderAdminService;
//
//    @Autowired
//    public AdminController(OrderAdminService orderAdminService) {
//        this.orderAdminService = orderAdminService;
//    }
//
//    @PostMapping("/deleteOrders")
//    public String deleteAllOrders() {
//        this.orderAdminService.deleteAllOrders();
//        return "redirect:/admin";
//    }
//}
