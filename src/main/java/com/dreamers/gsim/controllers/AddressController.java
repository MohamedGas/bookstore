package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.AddressImpl;
import model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {
    @Autowired
    private AddressImpl addressDAOimpl;

    /**
     * Constructor injection
     * @param addressDAOimpl
     */
    public AddressController(AddressImpl addressDAOimpl) {
        this.addressDAOimpl = addressDAOimpl;
    }

    /**
     * Get all addresses
     * @return
     */
    @GetMapping("/addresses")
    public ResponseEntity<?> getAllAddresses() {
        List<Address> addresses = addressDAOimpl.getAll();
        return ResponseEntity.ok(addresses);
    }

    /**
     * Get address by id
     * @param id
     * @return
     */
    @GetMapping("/addresses/id/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable String id) {
        Optional<Address> addressById = addressDAOimpl.getById(id);
        return ResponseEntity.ok(addressById);
    }

    /**
     * Add address
     * @param
     * @return
     */
    @PostMapping(value = "/addresses/add")
    public ResponseEntity<?> addAddress(@RequestBody Address address) {
        addressDAOimpl.add(address);
        return ResponseEntity.ok(address);
    }

    /**
     * Update address
     * @param id
     * @return
     */

    @PutMapping(value = "/addresses/update/{id}")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @PathVariable String id) {
        addressDAOimpl.update(address, id);
        return ResponseEntity.ok(address);
    }

    /**
     * Delete address
     * @param id
     * @return
     */
    @DeleteMapping(value = "/addresses/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable String id) {
        addressDAOimpl.delete(id);
        return ResponseEntity.ok("Address deleted");
    }
}
