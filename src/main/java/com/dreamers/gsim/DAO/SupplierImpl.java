package com.dreamers.gsim.DAO;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class SupplierImpl implements DAO<Supplier>{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AddressImpl addressImpl;
    private StateImpl stateImpl;

    public SupplierImpl(JdbcTemplate jdbcTemplate, AddressImpl addressImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressImpl = addressImpl;
    }

    @Override
    public List<Supplier> getAll() {
        String sql = "SELECT * , supplier.id AS supplier_id, state.id AS state_id, supplier.name AS supplier_name, state.name AS state_name " +
                     "FROM supplier " +
                     "INNER JOIN address ON supplier.address_id = address.id " +
                     "INNER JOIN state ON address.state = state.id";
        List<Supplier> suppliers = jdbcTemplate.query(sql, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("state_name"));
            Address address = new Address(res.getInt("address_id"),
                                        state,
                                        res.getString("street_address"),
                                        res.getString("city"),
                                        res.getString("postal_code"));

            Supplier supplier = new Supplier(res.getInt("supplier_id"),
                                            address,
                                            res.getString("supplier_name"),
                                            res.getString("email"),
                                            res.getString("manager"),
                                            res.getInt("contact_number"));
            return supplier;
        });

        return suppliers;
    }

    @Override
    public Optional<Supplier> getById(String id) {
      String sql = "SELECT *, supplier.id AS supplier_id, state.id AS state_id, supplier.name AS supplier_name, state.name AS state_name " +
                "FROM supplier " +
                "INNER JOIN address ON supplier.address_id = address.id " +
                "INNER JOIN state ON address.state = state.id " +
                "WHERE supplier.id = ?";
        Supplier suppliers = jdbcTemplate.queryForObject(sql, new Object[]{Integer.valueOf(id)}, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("state_name"));
            Address address = new Address(res.getInt("address_id"),
                    state,
                    res.getString("street_address"),
                    res.getString("city"),
                    res.getString("postal_code"));

            Supplier supplier = new Supplier(res.getInt("supplier_id"),
                    address,
                    res.getString("supplier_name"),
                    res.getString("email"),
                    res.getString("manager"),
                    res.getInt("contact_number"));
            return supplier;
        });

        return Optional.ofNullable(suppliers);
    }

    @Override
    public Optional<Supplier> getByName(String name) {
        String sql = "SELECT * , supplier.id AS supplier_id, state.id AS state_id, supplier.name AS supplier_name, state.name AS state_name " +
                "FROM supplier " +
                "INNER JOIN address ON supplier.address_id = address.id " +
                "INNER JOIN state ON address.state = state.id " +
                "WHERE supplier.name = ?";
        Supplier suppliers = jdbcTemplate.queryForObject(sql, new Object[]{name}, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("state_name"));
            Address address = new Address(res.getInt("address_id"),
                    state,
                    res.getString("street_address"),
                    res.getString("city"),
                    res.getString("postal_code"));

            Supplier supplier = new Supplier(res.getInt("supplier_id"),
                    address,
                    res.getString("supplier_name"),
                    res.getString("email"),
                    res.getString("manager"),
                    res.getInt("contact_number"));
            return supplier;
        });

        return Optional.ofNullable(suppliers);
    }

    public Optional<Supplier> getByManager(String name) {
        String sql = "SELECT *, supplier.id AS supplier_id, state.id AS state_id, supplier.name AS supplier_name, state.name AS state_name  " +
                "FROM supplier " +
                "INNER JOIN address ON supplier.address_id = address.id " +
                "INNER JOIN state ON address.state = state.id " +
                "WHERE manager = ?";
        Supplier suppliers = jdbcTemplate.queryForObject(sql, new Object[]{name}, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("state_name"));
            Address address = new Address(res.getInt("address_id"),
                    state,
                    res.getString("street_address"),
                    res.getString("city"),
                    res.getString("postal_code"));

            Supplier supplier = new Supplier(res.getInt("supplier_id"),
                    address,
                    res.getString("supplier_name"),
                    res.getString("email"),
                    res.getString("manager"),
                    res.getInt("contact_number"));
            return supplier;
        });

        return Optional.ofNullable(suppliers);
    }

    @Override
    // No-op implementation of getBySpecialCriteria()
    public Optional<List<Supplier>> getBySpecialCriteria(String category) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    public void add(Supplier product) {

    }

    @Override
    public void update(Supplier product, String id) {

    }

    public void add(String address_id, String name, String email, String manager, String contact_number) {
        String sql = "INSERT INTO supplier (address_id, name, email, manager, contact_number) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                            Integer.valueOf(address_id),
                            name,
                            email,
                            manager,
                            Integer.valueOf(contact_number));
    }

    public void update(String id,String address_id, String name, String email, String manager, String contact_number) {
        String sql = "UPDATE supplier SET address_id = ?, name = ?, email = ?, manager = ?, contact_number = ? WHERE id = ?" ;
        jdbcTemplate.update(sql,
                            Integer.valueOf(address_id),
                            name,
                            email,
                            manager,
                            Integer.valueOf(contact_number),
                            Integer.valueOf(id));
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM supplier WHERE id = ?";
        jdbcTemplate.update(sql, Integer.valueOf(id));
    }
}
