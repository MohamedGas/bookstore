package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.InventoryAuditImpl;
import com.dreamers.gsim.DAO.SupplierImpl;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InventoryAuditController {
    @Autowired
    private InventoryAuditImpl inventoryAuditDAOimpl;

    /**
     * Constructor injection
     * @param inventoryAuditDAOimpl
     */
    public InventoryAuditController(InventoryAuditImpl inventoryAuditDAOimpl) {
        this.inventoryAuditDAOimpl = inventoryAuditDAOimpl;
    }

    /**
     * Get all suppliers
     * @return
     */
    @GetMapping("/inventoryAudits")
    public ResponseEntity<?> getAllInventoryAudits() {
        List<InventoryAudit> inventoryaudits = inventoryAuditDAOimpl.getAll();
        return ResponseEntity.ok(inventoryaudits);
    }

    /**
     * Get state by adjustment_type
     * @param
     * @return
     */
    @GetMapping("/inventoryAudits/{adjustment_type_id}")
    public ResponseEntity<?> getInventoryAuditById(@RequestParam("adjustment_type_id") String adjustment_type_id) {
        List<InventoryAudit> inventoryAuditByType = inventoryAuditDAOimpl.getByAdjustmentType(adjustment_type_id);
        return ResponseEntity.ok(inventoryAuditByType);
    }

    /**
     * Add InventoryAudit
     * @param
     * @return
     */
    @PostMapping("/inventoryAudits/add-damaged/{inventory_id}{reason}{quantity_adjusted}")
    public ResponseEntity<?> addInventoryAuditDamaged(@RequestParam("inventory_id") String inventory_id,
                                                      @RequestParam("reason") String reason,
                                                      @RequestParam("quantity_adjusted") String quantity_adjusted) {
        inventoryAuditDAOimpl.add(inventory_id,"4",reason,quantity_adjusted);
        return ResponseEntity.ok(null);
    }

    /**
     * Add InventoryAudit
     * @param
     * @return
     */
    @PostMapping("/inventoryAudits/add-stale/{inventory_id}{reason}{quantity_adjusted}")
    public ResponseEntity<?> addInventoryAuditStale(@RequestParam("inventory_id") String inventory_id,
                                                      @RequestParam("reason") String reason,
                                                      @RequestParam("quantity_adjusted") String quantity_adjusted) {
        inventoryAuditDAOimpl.add(inventory_id,"3",reason,quantity_adjusted);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/inventoryAudits/add-refund/{inventory_id}{reason}{quantity_adjusted}")
    public ResponseEntity<?> addInventoryAuditRefund(@RequestParam("inventory_id") String inventory_id,
                                                     @RequestParam("reason") String reason,
                                                     @RequestParam("quantity_adjusted") String quantity_adjusted) {
        inventoryAuditDAOimpl.add(inventory_id,"5",reason,quantity_adjusted);
        return ResponseEntity.ok(null);
    }


}
