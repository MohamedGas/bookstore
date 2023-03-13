package model;

import java.math.BigDecimal;

public record OrderItem  (int id, Order order, ProductCatalog product, int quantity) {

}
