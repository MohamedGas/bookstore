package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.OrderImpl;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderImpl OrderImpl;

    @PostMapping("supplier/{sid}/add/{sku}/{quantity}")
    public void addOrder(@RequestBody Order order, @PathVariable("sid") int sid, @PathVariable("sku") String sku, @PathVariable("quantity") int quantity){
        OrderImpl.addOrder(order, sid, sku, quantity);
    }

    @DeleteMapping("cancel/{id}")
    public String cancelOrder(@PathVariable("id") int id){
        OrderImpl.cancelOrder(id);
        return "Order " + id + " has been cancelled";
    }
}
