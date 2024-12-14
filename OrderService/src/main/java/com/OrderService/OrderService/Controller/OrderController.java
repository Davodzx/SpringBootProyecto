package com.OrderService.OrderService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderService.OrderService.Entity.OrderItem;
import com.OrderService.OrderService.Service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/API/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder (@RequestBody @Valid OrderItem orderItem){
        try{
            orderService.createOrder(orderItem);
            return new ResponseEntity<>("Orden creada correctamente", HttpStatus.CREATED);}
            catch (Exception e) { return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); }}

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderById(@PathVariable long id){
        OrderItem order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
