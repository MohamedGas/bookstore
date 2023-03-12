package com.dreamers.gsim.DAO;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@Component
public class InventoryAuditImpl implements DAO<InventoryAudit>{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public InventoryAuditImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<InventoryAudit> getAll() {
        String sql =    "SELECT *, inventoryaudit.id AS inventoryaudit_id " +
                "FROM inventoryaudit " +
                "INNER JOIN inventory ON inventory.id = inventoryaudit.inventory_id " +
                "INNER JOIN \"Order\" ON \"Order\".id = inventoryaudit.order_id " +
                "INNER JOIN adjustmenttype ON adjustmenttype.id = inventoryaudit.adjustment_type";

        List<InventoryAudit> inventoryAudits = jdbcTemplate.query(sql, (res, rowNum) -> {
            ProductCatalog productCatalog = new ProductCatalog(res.getString("sku"),null,null,null,null,null);
            Location location = new Location(res.getInt("location_id"), null);
            Inventory inventory = new Inventory(res.getInt("inventory_id"),
                                                productCatalog,
                                                location,
                                                res.getInt("quantity"),
                                                res.getTimestamp("expiration_date"));
            Supplier supplier = new Supplier(res.getInt("supplier_id"),null,null,null,null,-1);
            OrderStatus orderStatus = new OrderStatus(res.getInt("order_status_id"),null);
            Order order = new Order(res.getInt("order_id"),
                                    supplier,
                                    orderStatus,
                                    res.getTimestamp("order_date"));
            AdjustmentType adjustmentType = new AdjustmentType(res.getInt("adjustment_type"),res.getString("name"));
            InventoryAudit inventoryaudit = new InventoryAudit(res.getInt("inventoryaudit_id"),
                                                                inventory,
                                                                order,
                                                                adjustmentType,
                                                                res.getString("reason"),
                                                                res.getInt("quantity_adjusted"),
                                                                res.getTimestamp("date"));
            return inventoryaudit;
        });
        return inventoryAudits;
    }

    @Override
    // No-op implementation of getByName()
    public Optional<InventoryAudit> getById(String id) {
        // Do nothing
        return Optional.empty();
    }

    public List<InventoryAudit> getByAdjustmentType(String id) {
        String sql =    "SELECT *, inventoryaudit.id AS inventoryaudit_id " +
                "FROM inventoryaudit " +
                "INNER JOIN adjustmenttype ON adjustmenttype.id = inventoryaudit.adjustment_type " +
                "WHERE adjustment_type = " + id;

        List<InventoryAudit> inventoryAudits = jdbcTemplate.query(sql, (res, rowNum) -> {
            Inventory inventory = new Inventory(res.getInt("inventory_id"),
                    null,
                    null,
                    -1,
                    null);
            Order order = new Order(res.getInt("order_id"),
                    null,
                    null,
                    null);
            AdjustmentType adjustmentType = new AdjustmentType(res.getInt("adjustment_type"),res.getString("name"));
            InventoryAudit inventoryaudit = new InventoryAudit(res.getInt("inventoryaudit_id"),
                    inventory,
                    order,
                    adjustmentType,
                    res.getString("reason"),
                    res.getInt("quantity_adjusted"),
                    res.getTimestamp("date"));
            return inventoryaudit;
        });
        return inventoryAudits;
    }

    @Override
    // No-op implementation of getByName()
    public Optional<InventoryAudit> getByName(String name) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    // No-op implementation of getBySpecialCriteria()
    public Optional<List<InventoryAudit>> getBySpecialCriteria(String category) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    public void add(InventoryAudit product) {

    }

    public void add(String inventory_id, String adjustment_type, String reason, String quantity_adjusted) {
        String sql = "INSERT INTO inventoryaudit (inventory_id, order_id, adjustment_type, reason, quantity_adjusted, date) VALUES (?, null, ?, ?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql,
                Integer.valueOf(inventory_id),
                Integer.valueOf(adjustment_type),
                reason,
                Integer.valueOf(quantity_adjusted));

        if(adjustment_type.equals("3") || adjustment_type.equals("4") ) {
            String sql2 = "UPDATE inventory SET quantity=quantity-" + quantity_adjusted +" WHERE id=" + inventory_id;
            jdbcTemplate.update(sql2);
        }
        else if(adjustment_type.equals("5")) {
            String sql2 = "UPDATE inventory SET quantity=quantity+" + quantity_adjusted +" WHERE id=" + inventory_id;
            jdbcTemplate.update(sql2);
        }
    }

    @Override
    public void update(InventoryAudit product, String id) {

    }

    @Override
    public void delete(String id) {

    }
}
