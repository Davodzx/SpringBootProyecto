package com.ProductService.ProductService.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProductService.ProductService.Entity.Product;


@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{

    Product findByName (String name);
    
} 