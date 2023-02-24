package model;

import java.sql.Timestamp;


public record Inventory (int id, ProductCatalog product, Location location, int quantity, Timestamp expirationDate) {
}