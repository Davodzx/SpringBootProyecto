package com.ProductService.ProductService.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProductService.ProductService.Entity.Product;
import com.ProductService.ProductService.Repository.ProductRepository;
import com.ProductService.ProductService.Service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/API/Products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository){
        this.productService = productService;
        this.productRepository = productRepository;
    }

   @PostMapping
   public ResponseEntity<Product> createdProduct (@Valid @RequestBody Product product) {
       Product createProduct = productService.createProduct(product);

       productService.addToInventory(createProduct.getId(), 10);
       return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
   }

   @GetMapping("findByName")
   public Product getProductName (@RequestParam String name){
        return productRepository.findByName(name);
   }
   
   @PutMapping("/{id}")
   public ResponseEntity<Product> updateProduct(@Valid @PathVariable Long id, @RequestBody Product product){
        Product updateProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updateProduct);
   }

   @GetMapping("AllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @DeleteMapping()
    public ResponseEntity<Void> deleteProduct(@RequestParam long ProductID){
        productService.deleteProduct(ProductID);

        boolean deleteStock = productService.removeInventory(ProductID);
        if(!deleteStock){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   
    @GetMapping("/{id}/checkStock/{quantity}")
    public ResponseEntity<String> checkStock(@PathVariable Long id, @PathVariable int quantity){
        boolean available = productService.checkProductStock(id, quantity);
        if (available){
            return ResponseEntity.ok("Se cuenta con stock");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se cuenta con stock");
        }
    }

    @PostMapping("/AddNewStock")
    public boolean AddStock(@RequestParam Long id, @RequestParam int Stock) {
        productService.addToInventory(id, Stock);
        return true;
    }
    
    

}