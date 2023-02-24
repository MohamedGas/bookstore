package model;

import java.sql.Timestamp;


public record InventoryAudit (int id, Inventory inventory, Order order, AdjustmentType adjustmentType, String reason, int quantityAdjusted, Timestamp date){}