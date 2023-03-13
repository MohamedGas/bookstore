package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.AddressImpl;
import com.dreamers.gsim.DAO.InventoryAuditImpl;
import com.dreamers.gsim.DAO.ShipmentImpl;
import model.Address;
import model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShipmentController {
    @Autowired
    private ShipmentImpl shipmentDAOimpl;

    /**
     * Constructor injection
     * @param shipmentDAOimpl
     */
    public ShipmentController(ShipmentImpl shipmentDAOimpl) {
        this.shipmentDAOimpl = shipmentDAOimpl;
    }

    /**
     * Get all shipments
     * @return
     */
    @GetMapping("/shipments")
    public ResponseEntity<?> getAllShipments() {
        List<Shipment> shipments = shipmentDAOimpl.getAll();
        return ResponseEntity.ok(shipments);
    }

    /**
     * Get shipment by id
     * @param
     * @return
     */
    @GetMapping("/shipments/{order_id}")
    public ResponseEntity<?> getShipmentByOrderId(@RequestParam("order_id") String order_id) {
        Optional<Shipment> shipmentByOrderId = shipmentDAOimpl.getById(order_id);
        return ResponseEntity.ok(shipmentByOrderId);
    }

}
