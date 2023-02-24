package model;

import java.sql.Timestamp;

public record SalesBook  (int id, ProductCatalog product, int quantity, Timestamp dateOfSale) {
}
