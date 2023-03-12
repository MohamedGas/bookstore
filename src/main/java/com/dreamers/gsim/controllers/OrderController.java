package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderImpl orderDAOimpl;

    /**
     * Constructor injection
     * @param orderDAOimpl
     */
    public OrderController(OrderImpl orderDAOimpl) {
        this.orderDAOimpl = orderDAOimpl;
    }

    /**
     * Add shipment
     * @param
     * @return
     */
    @PostMapping(value = "/orders/add/{supplier_id}")
    public ResponseEntity<?> addOrder(@RequestParam("supplier_id") String supplier_id) {
        orderDAOimpl.add(supplier_id,"1");
        return ResponseEntity.ok(null);
    }

    /**
     * Update shipment
     * @param
     * @return
     */
    @PutMapping(value = "/orders/update-to-shipping/{order_id}{shipper_id}{tracking_number}")
    public ResponseEntity<?> updateShipping(@RequestParam("order_id") String order_id,
                                            @RequestParam("shipper_id") String shipper_id,
                                            @RequestParam("tracking_number") String tracking_number) {
        orderDAOimpl.update(order_id, "2", shipper_id, tracking_number);
        return ResponseEntity.ok(null);
    }
    /**
     * Update shipment
     * @param
     * @return
     */
    @PutMapping(value = "/orders/update-to-delivered/{order_id}")
    public ResponseEntity<?> updateDelivered(@RequestParam("order_id") String order_id) {
        orderDAOimpl.update(order_id, "3", null,null);
        return ResponseEntity.ok(null);
    }

    /**
     * Cancel shipment
     * @param
     * @return
     */
    @PutMapping(value = "/orders/cancel/{order_id}")
    public ResponseEntity<?> cancelOrder(@RequestParam("order_id") String order_id) {
        orderDAOimpl.update(order_id,"4",null,null);
        return ResponseEntity.ok(null);
    }
}
