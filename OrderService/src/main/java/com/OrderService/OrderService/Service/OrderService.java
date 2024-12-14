package com.OrderService.OrderService.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.OrderService.OrderService.Entity.OrderItem;
import com.OrderService.OrderService.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("null")
    public OrderItem createOrder(OrderItem orderItem){
        String productService = "http://localhost:8082/API/Products/" + orderItem.getProductID();
        ResponseEntity<Product> productData = restTemplate.getForEntity(productService, Product.class);

        if (productData.getStatusCode() == HttpStatus.OK){
            Product product = productData.getBody();
            double productPrice = product.getPrice();
            double requiredAmount = productPrice * orderItem.getQuantity();

            if (orderItem.getTotalAmount() < requiredAmount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto total es insuficiente para comprar el producto");
            }
        String checkStockUrl = "http://localhost:8082/API/Products/" + orderItem.getProductID() + "/checkStock/" + orderItem.getQuantity(); 
        ResponseEntity<String> stockResponse = restTemplate.getForEntity(checkStockUrl, String.class); 
        if (stockResponse.getStatusCode() == HttpStatus.OK) { 
            String newStock = "http://localhost:8082/API/Products/AddNewStock?id=" + orderItem.getProductID() + "&Stock=-" + orderItem.getQuantity(); 
            restTemplate.postForObject(newStock, null, Boolean.class); 
            orderItem.setStatus("Created"); return orderRepository.save(orderItem); 
        } 
        else{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no disponible");} 
        }
        else { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"); }
    }

    public OrderItem getOrderById (Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));
    }
}
