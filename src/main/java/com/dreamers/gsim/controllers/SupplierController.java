package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.SupplierImpl;
import model.State;
import model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class SupplierController {
    @Autowired
    private SupplierImpl supplierDAOimpl;

    /**
     * Constructor injection
     * @param supplierDAOimpl
     */
    public SupplierController(SupplierImpl supplierDAOimpl) {
        this.supplierDAOimpl = supplierDAOimpl;
    }

    /**
     * Get all suppliers
     * @return
     */
    @GetMapping("/suppliers")
    public ResponseEntity<?> getAllSuppliers() {
        List<Supplier> suppliers = supplierDAOimpl.getAll();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Get supplier by id
     * @param id
     * @return
     */
    @GetMapping("/suppliers/id/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable String id) {
        Optional<Supplier> supplierById = supplierDAOimpl.getById(id);
        return ResponseEntity.ok(supplierById);
    }

    /**
     * Get supplier by name
     * @param name
     * @return
     */
    @GetMapping("/suppliers/name/{name}")
    public ResponseEntity<?> getSupplierByName(@PathVariable String name) {
        Optional<Supplier> supplierByName = supplierDAOimpl.getByName(name);
        return ResponseEntity.ok(supplierByName);
    }

    /**
     * Get supplier by manager
     * @param manager
     * @return
     */
    @GetMapping("/suppliers/manager/{manager}")
    public ResponseEntity<?> getSupplierByManager(@PathVariable String manager) {
        Optional<Supplier> supplierByManager = supplierDAOimpl.getByManager(manager);
        return ResponseEntity.ok(supplierByManager);
    }

    /**
     * Add supplier
     * @param
     * @return
     */
    @PostMapping(value = "/suppliers/add")
    public ResponseEntity<?> addSupplier(@RequestParam("address_id") String address_id,
                                         @RequestParam("name") String name,
                                         @RequestParam("email") String email,
                                         @RequestParam("manager") String manager,
                                         @RequestParam("contact_number") String contact_number) {
        supplierDAOimpl.add(address_id,name,email,manager,contact_number);
        return ResponseEntity.ok(null);
    }

    /**
     * Update supplier
     * @param id
     * @return
     */

    @PutMapping(value = "/suppliers/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable String id,
                                            @RequestParam("address_id") String address_id,
                                            @RequestParam("name") String name,
                                            @RequestParam("email") String email,
                                            @RequestParam("manager") String manager,
                                            @RequestParam("contact_number") String contact_number) {
        supplierDAOimpl.update(id,address_id,name,email,manager,contact_number);
        return ResponseEntity.ok(null);
    }

    /**
     * Delete supplier
     * @param id
     * @return
     */
    @DeleteMapping(value = "/suppliers/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable String id) {
        supplierDAOimpl.delete(id);
        return ResponseEntity.ok("Supplier deleted");
    }
}
