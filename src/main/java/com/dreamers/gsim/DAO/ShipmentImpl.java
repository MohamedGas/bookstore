package com.dreamers.gsim.DAO;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShipmentImpl  implements DAO<Shipment>{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ShipmentImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Shipment> getAll() {
        String sql = "SELECT *, shipment.id AS shipment_id, shipper.id AS shipper_id " +
                "FROM shipment " +
                "INNER JOIN shipper ON shipper.id = shipment.shipper_id";
        List<Shipment> shipments = jdbcTemplate.query(sql, (res, rowNum) -> {
            Shipper shipper = new Shipper(res.getInt("shipper_id"), res.getString("name"));
            Order order = new Order(res.getInt("order_id"),null,null,null);
            Shipment shipment = new Shipment(res.getInt("shipment_id"),
                    order,
                    shipper,
                    res.getString("tracking_number"));
            return shipment;
        });

        return shipments;
    }

    @Override
    public Optional<Shipment> getById(String id) {
        String sql = "SELECT *, shipment.id AS shipment_id, shipper.id AS shipper_id " +
                "FROM shipment " +
                "INNER JOIN shipper ON shipper.id = shipment.shipper_id " +
                "WHERE order_id = ?";
        Shipment shipment = jdbcTemplate.queryForObject(sql, new Object[]{Integer.valueOf(id)}, (res, rowNum) -> {
            Shipper shipper = new Shipper(res.getInt("shipper_id"), res.getString("name"));
            Order order = new Order(res.getInt("order_id"),null,null,null);
            Shipment shipment1 = new Shipment(res.getInt("shipment_id"),
                    order,
                    shipper,
                    res.getString("tracking_number"));
            return shipment1;
        });

        return Optional.ofNullable(shipment);
    }

    @Override
    public Optional<Shipment> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Shipment>> getBySpecialCriteria(String category) {
        return Optional.empty();
    }

    @Override
    public void add(Shipment shipment) {
        String sql = "INSERT INTO shipment (order_id, shipper_id, tracking_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                Integer.valueOf(shipment.order().id()),
                Integer.valueOf(shipment.shipper().id()),
                shipment.trackingNumber());
    }

    @Override
    public void update(Shipment product, String id) {

    }

    @Override
    public void delete(String id) {

    }
}
