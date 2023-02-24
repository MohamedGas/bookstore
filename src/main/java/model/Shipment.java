package model;

public record Shipment (int id, Order order, Shipper shipper, String trackingNumber) {
}
