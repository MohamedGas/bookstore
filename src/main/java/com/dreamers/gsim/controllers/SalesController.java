package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.SalesImpl;
import model.InventoryAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesImpl salesDAOimpl;

    public SalesController(SalesImpl salesDAOimpl) {
        this.salesDAOimpl = salesDAOimpl;
    }

/**
     * Get best sellers
     */
    @GetMapping("/best-sellers")
    public ResponseEntity<?> getBestSellerFromDateRange(@RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDate startDate,
                                                        @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDate endDate) {
      return ResponseEntity.ok(salesDAOimpl.getBestSellers(startDate, endDate));
    }

    @PostMapping("/add/{sku}{quantity}")
    public ResponseEntity<?> addSalesBook(@RequestParam("sku") String sku, @RequestParam("quantity") String quantity) {
        salesDAOimpl.add(sku,quantity);
        return ResponseEntity.ok(null);
    }



}
