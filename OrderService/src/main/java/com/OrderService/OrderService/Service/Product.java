package com.OrderService.OrderService.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id; 
    private String name; 
    private String description; 
    private String category; 
    private double price;
}
