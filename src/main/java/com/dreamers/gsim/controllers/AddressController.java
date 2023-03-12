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
    @GetMapping("/addresses/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable String id) {
        Optional<Address> addressById = addressDAOimpl.getById(id);
        return ResponseEntity.ok(addressById);
    }

    /**
     * Add address
     * @param
     * @return
     */
    @PostMapping(value = "/addresses/add/{state_id}{street_address}{city}{postal_code}")
    public ResponseEntity<?> addAddress(@RequestParam("state_id") String state_id,
                                        @RequestParam("street_address") String street_address,
                                        @RequestParam("city") String city,
                                        @RequestParam("postal_code") String postal_code) {
        addressDAOimpl.add(state_id,street_address,city,postal_code);
        return ResponseEntity.ok(null);
    }

    /**
     * Update address
     * @param id
     * @return
     */

    @PutMapping(value = "/addresses/update/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable String id,
                                           @RequestParam("state_id") String state_id,
                                           @RequestParam("street_address") String street_address,
                                           @RequestParam("city") String city,
                                           @RequestParam("postal_code") String postal_code) {
        addressDAOimpl.update(id,state_id,street_address,city,postal_code);
        return ResponseEntity.ok(null);
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
