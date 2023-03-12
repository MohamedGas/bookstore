package com.dreamers.gsim.DAO;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderItemImpl implements DAO<OrderItem> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OrderItemImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    public List<OrderItem> getAllById(String id) {
        String sql = "SELECT * " +
                "FROM orderitem " +
                "WHERE order_id = " + id;
        List<OrderItem> orderItems = jdbcTemplate.query(sql, (res, rowNum) -> {
            ProductCatalog productCatalog = new ProductCatalog(res.getString("sku"),null,null,null,null,null);
            Order order = new Order(res.getInt("order_id"),null,null,null);
            OrderItem orderitem = new OrderItem(res.getInt("id"),
                                            order,
                                            productCatalog,
                                            res.getInt("quantity"));
            return orderitem;
        });

        return orderItems;
    }

    @Override
    public Optional<OrderItem> getById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<OrderItem> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<List<OrderItem>> getBySpecialCriteria(String category) {
        return Optional.empty();
    }

    @Override
    public void add(OrderItem orderItem) {

    }

    public void add(String order_id, String sku, String quantity) {
        String sql = "INSERT INTO orderitem (order_id, sku, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                Integer.valueOf(order_id),
                sku,
                Integer.valueOf(quantity));
    }

    @Override
    public void update(OrderItem product, String id) {

    }

    @Override
    public void delete(String id) {

    }
}
