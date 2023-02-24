package com.dreamers.gsim.controllers;

import com.dreamers.gsim.DAO.ProductCatalogImpl;
import model.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductCatalogController {
    @Autowired
    private ProductCatalogImpl productCatalogDAOimpl;

    /**
     * Constructor injection
     * @param productCatalogDAOimpl
     */
    public ProductCatalogController(ProductCatalogImpl productCatalogDAOimpl) {
        this.productCatalogDAOimpl = productCatalogDAOimpl;
    }

    /**
     * Get all products
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        List<?> products = productCatalogDAOimpl.getAll();
        return ResponseEntity.ok(products);

    }

    /**
     * Get product by id
     * @param id
     * @return
     */
    @GetMapping("/products/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Optional<ProductCatalog> productById = productCatalogDAOimpl.getById(id);
        return ResponseEntity.ok(productById);
    }

    /**
     * Get product by name
     * @param name
     * @return
     */
    @GetMapping("/products/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        Optional<ProductCatalog> product = productCatalogDAOimpl.getByName(name);
        return ResponseEntity.ok(product);
    }

    /**
     * Get product by category
     * @param category
     * @return
     */
    @GetMapping("/products/category/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category) {
        Optional<List<ProductCatalog>> product = productCatalogDAOimpl.getBySpecialCriteria(category);
        return ResponseEntity.ok(product);
    }

    /**
     * Add product
     * @param product
     * @return
     */
    @PostMapping(value = "/products/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductCatalog product) {
        productCatalogDAOimpl.add(product);
        return ResponseEntity.ok(product);
    }

/**
     * Delete product
     * @param id
     * @return
     */

    @PutMapping(value = "/products/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductCatalog product, @PathVariable String id) {
        productCatalogDAOimpl.update(product, id);
        return ResponseEntity.ok(product);
    }

    /**
     * Delete product
     * @param id
     * @return
     */
    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productCatalogDAOimpl.delete(id);
        return ResponseEntity.ok("Product deleted");
    }

}
