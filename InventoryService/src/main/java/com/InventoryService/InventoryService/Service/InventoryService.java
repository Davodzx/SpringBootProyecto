package com.InventoryService.InventoryService.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InventoryService.InventoryService.Entity.Inventory;
import com.InventoryService.InventoryService.InventoryRepository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory addStock(long productID, int quantity){
        Inventory inventory = inventoryRepository.findByProductID(productID).orElse(new Inventory());
        inventory.setProductID(productID);
        inventory.setQuantity(Optional.ofNullable(inventory.getQuantity()).orElse(0) + quantity);
        return inventoryRepository.save(inventory);
    }

    public boolean isInStock(Long productID, int quantity){
        return inventoryRepository.findByProductID(productID).map(invetory -> invetory.getQuantity() >= quantity).orElse(false);
    }

    public boolean reduceStock(Long productID, int quantity){
        Inventory inventory = inventoryRepository.findByProductID(productID).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if(inventory.getQuantity() <quantity){
            return false;
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
        return true;
    }

    public int getStock (Long productID){
        return inventoryRepository.findByProductID(productID).map(Inventory::getQuantity).orElse(0);
    }

    @Transactional
    public void RemoveStock(Long productID){
        inventoryRepository.deleteByProductID(productID);
    }
    

}
