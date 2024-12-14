package com.OrderService.OrderService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderService.OrderService.Entity.OrderItem;

public interface OrderRepository  extends JpaRepository<OrderItem, Long>{
    
} 
