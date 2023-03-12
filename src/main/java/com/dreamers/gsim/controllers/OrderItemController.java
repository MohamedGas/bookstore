package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.OrderImpl;
import com.dreamers.gsim.DAO.OrderItemImpl;
import model.Order;
import model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController {
    @Autowired
    private OrderItemImpl orderItemDAOimpl;

    /**
     * Constructor injection
     * @param orderItemDAOimpl
     */
    public OrderItemController(OrderItemImpl orderItemDAOimpl) {
        this.orderItemDAOimpl = orderItemDAOimpl;
    }

    /**
     * Get orderItems
     * @param id
     * @return
     */
    @GetMapping(value = "/orderItems/{order_id}")
    public ResponseEntity<?> getOrderItemByOrderId(String id) {
        List<OrderItem> orderItems = orderItemDAOimpl.getAllById(id);
        return ResponseEntity.ok(orderItems);
    }

    /**
     * Add orderItem
     * @param
     * @return
     */
    @PostMapping(value = "/orderItems/add/{order_id}{sku}{quantity}")
    public ResponseEntity<?> addOrderItem(@RequestParam String order_id, @RequestParam String sku, @RequestParam String quantity) {
        orderItemDAOimpl.add(order_id, sku, quantity);
        return ResponseEntity.ok(null);
    }
}
