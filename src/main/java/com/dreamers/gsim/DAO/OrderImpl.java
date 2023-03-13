package com.dreamers.gsim.DAO;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderImpl implements DAO<Order> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private OrderItemImpl orderItemImpl;
    private InventoryImpl inventoryImpl;

    public OrderImpl(JdbcTemplate jdbcTemplate, OrderItemImpl orderItemImpl, InventoryImpl inventoryImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderItemImpl = orderItemImpl;
        this.inventoryImpl = inventoryImpl;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Optional<Order> getById(String id) {
        return Optional.empty();
    }


    @Override
    public Optional<Order> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Order>> getBySpecialCriteria(String category) {
        return Optional.empty();
    }

    @Override
    public void add(Order order) {

    }

    public void add(String supplier_id, String order_status_id) {
        String sql = "INSERT INTO \"Order\" (supplier_id, order_status_id, order_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql,
                Integer.valueOf(supplier_id),
                Integer.valueOf(order_status_id));
    }

    @Override
    public void update(Order product, String id) {

    }


    /*
        If orderstatus is delivered, add inventoryaudit and update inventory
     */
    public void update(String order_id, String order_status_id, String shipper_id, String tracking_number) {
        String sql = "UPDATE \"Order\" SET order_status_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                            Integer.valueOf(order_status_id),
                            Integer.valueOf(order_id));

        if(order_status_id.equals("2")) {
            String sql1 = "INSERT INTO shipment (order_id, shipper_id, tracking_number) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql1, Integer.valueOf(order_id), Integer.valueOf(shipper_id), tracking_number);
        }

        if(order_status_id.equals("3")) {
            List<OrderItem> orderItems = orderItemImpl.getAllById(order_id);
            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem orderItem = orderItems.get(i);
                String orderId = String.valueOf(orderItem.order().id());
                String sku = orderItem.product().sku();
                Optional<Inventory> inventory = inventoryImpl.getInventoryBySku(sku);
                String inventoryId = String.valueOf(inventory.get().id());
                String quantity = String.valueOf(orderItem.quantity());

                String sql2 = "INSERT INTO inventoryaudit (inventory_id, order_id, adjustment_type, reason, quantity_adjusted, date) " +
                        "VALUES (" + inventoryId + ", " + orderId + ", 1, \'restock\', " + quantity + ", CURRENT_TIMESTAMP)";
                jdbcTemplate.update(sql2);

                String sql3 = "UPDATE inventory SET quantity=quantity+" + quantity + "WHERE id=" + inventoryId;
                jdbcTemplate.update(sql3);

            }
        }
    }

    @Override
    public void delete(String id) {
    }
}
