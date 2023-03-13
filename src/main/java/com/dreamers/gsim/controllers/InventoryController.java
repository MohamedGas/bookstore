package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.InventoryImpl;
import model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryImpl inventoryDAOimpl;

    public InventoryController(InventoryImpl inventoryDAOimpl) {
        this.inventoryDAOimpl = inventoryDAOimpl;
    }

    /**
     * Get all inventory
     * @return
     */
    @GetMapping("")
    public ResponseEntity<List<Inventory>> getInventory() {
        List<Inventory> inventory = inventoryDAOimpl.getInventory();
        return ResponseEntity.ok(inventory);
    }

    /**
     * Get inventory by sku
     * @param sku
     * @return
     */
    @GetMapping("/{sku}")
    public ResponseEntity<Optional<Inventory>> getInventoryBySku(@PathVariable String sku) {
        Optional<Inventory> inventory = inventoryDAOimpl.getInventoryBySku(sku);
        return ResponseEntity.ok(inventory);
    }

//    /**
//     * Re-up the stock
//     */
//    @PutMapping("/re-up")
//    public void reUpTheStock() {
//        inventoryDAOimpl.reUpTheStock();
//    }
//    /**
//     * Re-up the stock individualy by sku id
//     */
//
//    @PutMapping("/re-up/{id}")
//    public void reUpTheStockIndvividualy(@PathVariable String sku) {
//        inventoryDAOimpl.reUpTheStockIndvividualy(sku);
//    }

    /**
     * Get inventory running out
     * @return
     */
    @GetMapping("/running-out")
    public ResponseEntity<List<Inventory>> runnigOutInventory() {
        List<Inventory> inventory = inventoryDAOimpl.runnigOutInventory();
        return ResponseEntity.ok(inventory);
    }




}