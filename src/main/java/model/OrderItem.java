package model;

import java.math.BigDecimal;

public record OrderItem  (int id, Order order, String product, int quantity, BigDecimal unitPrice) {

}
