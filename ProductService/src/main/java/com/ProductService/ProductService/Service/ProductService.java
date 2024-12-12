package com.ProductService.ProductService.Service;

import java.util.List;



import com.ProductService.ProductService.Entity.Product;

public interface ProductService {

    Product createProduct (Product product);
    Product getProductById (Long id);
    Product updateProduct (Long id, Product product);
    void deleteProduct (long ProductID);
    List<Product> getAllProducts();
    boolean checkProductStock(Long productID, int quantity);
    boolean addToInventory(Long productID, int Stock);
    boolean removeInventory(long productID);

}
