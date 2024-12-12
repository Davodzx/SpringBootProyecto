package com.InventoryService.InventoryService.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.InventoryService.InventoryService.Entity.Inventory;
import com.InventoryService.InventoryService.Service.InventoryService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("API/Inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    
    @PostMapping("/AddStock")
    public ResponseEntity<Inventory> addStock(@Valid @RequestParam Long productID, @RequestParam int quantity){
        Inventory inventory = inventoryService.addStock(productID, quantity);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/isInStock")
    public boolean isInStock(@RequestParam Long productID, @RequestParam int quantity){
        return inventoryService.isInStock(productID, quantity);
        
    }

    @PostMapping("/reduceStock")
    public ResponseEntity<Boolean> reduceStock(@RequestParam Long productID, @RequestParam int quantity){
        boolean AStock = inventoryService.reduceStock(productID, quantity);
        if(!AStock){
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok(true);
    }   

    @GetMapping("/getStock")
    public ResponseEntity<Integer> getStock (@RequestParam Long productID){
        return ResponseEntity.ok(inventoryService.getStock(productID));
    }

    @DeleteMapping("/RemoveStock")
    public ResponseEntity<Void> removeStock(@RequestParam Long productID){
        inventoryService.RemoveStock(productID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
