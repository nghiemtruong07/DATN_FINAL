package com.PCThanhCong.controllers;

import com.PCThanhCong.entity.Order;
import com.PCThanhCong.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/orders")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private IOrderService service;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable("userId") Integer userId){
        Order order = service.getOrderByUserId(userId);
        order = service.updateOrderAmount(order.getOrderItems().toArray().length , order);
        return  new ResponseEntity<>(order , HttpStatus.OK);
    }
}
