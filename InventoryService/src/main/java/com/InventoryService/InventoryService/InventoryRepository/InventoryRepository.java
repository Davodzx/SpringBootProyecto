package com.InventoryService.InventoryService.InventoryRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.InventoryService.InventoryService.Entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductID(long productID);
    void deleteByProductID(long productID);
} 
