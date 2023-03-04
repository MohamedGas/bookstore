package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public record SalesBook  (int id, ProductCatalog product, int quantity, LocalDate dateOfSale) {
}
