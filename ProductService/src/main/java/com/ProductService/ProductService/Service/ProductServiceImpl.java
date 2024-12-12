package com.ProductService.ProductService.Service;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ProductService.ProductService.Entity.Product;
import com.ProductService.ProductService.Repository.ProductRepository;



@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductServiceImpl(ProductRepository productRepository, RestTemplate restTemplate){
        this.productRepository = productRepository;
        this.restTemplate = restTemplate; 
    }

    @Override
    public Product createProduct(Product product){
       if(productRepository.findByName(product.getName()) != null){
        throw new RuntimeException("El producto ya existe con el nombre: " + product.getName());
       }
       return productRepository.save(product);
    }

    @Override
    public Product getProductById (Long id){
        return productRepository.findById(id).orElseThrow( () -> new RuntimeException("Producto no encontrado con ID" + id));
    }

    @Override
    public Product updateProduct(Long id, Product product){
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());;
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct (long productID){
        productRepository.deleteById(productID);
    }

    @Override
    public boolean checkProductStock(Long productID, int quantity){
        String invetoryServiceAccess = "http://localhost:8082/API/Inventory/isInStock?productID="+ productID +"&quantity="+quantity;

        @SuppressWarnings("null")
        boolean isInStock = restTemplate.getForObject(invetoryServiceAccess,boolean.class);

        return isInStock;
    }

    @Override
    public boolean addToInventory(Long productID, int Stock) {
        String inventoryServiceAddStock = "http://localhost:8082/API/Inventory/AddStock?productID="+ productID + "&quantity="+Stock;

        restTemplate.postForEntity(inventoryServiceAddStock, null, Void.class, productID, Stock);
        return true;
    }

    @Override
    public boolean removeInventory(long productID) {
        String invetorySerivceRemoveStock = "http://localhost:8082/API/Inventory/RemoveStock?productID="+ productID;
        restTemplate.delete(invetorySerivceRemoveStock);
        return true;
    }


}
