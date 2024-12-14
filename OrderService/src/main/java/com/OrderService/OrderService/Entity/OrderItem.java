package com.OrderService.OrderService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private Long ProductID;

    @Min(value=1)
    private int quantity;

    @NotBlank
    private String status;

    @NotBlank
    private String nameClient;

    @Email
    @NotBlank
    private String emailClient;

    @NotBlank
    private String addressClient;
    
    @NotBlank
    private String phoneClient;

    @NotBlank
    private String paymentMethod;

    @Min(value = 0)
    private double totalAmount;
}
