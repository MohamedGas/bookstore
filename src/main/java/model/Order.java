package model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public record Order (int id, Supplier supplier, OrderStatus orderStatus, Shipper shipper, Timestamp orderDate, BigDecimal orderTotal) {

}
