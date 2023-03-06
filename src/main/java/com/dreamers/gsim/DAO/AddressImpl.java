package com.dreamers.gsim.DAO;

import model.Address;
import model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class AddressImpl implements DAO<Address>{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AddressImpl(JdbcTemplate jdbcTemplate, StateImpl stateImpl) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Address> getAll() {
        String sql = "SELECT *, address.id AS address_id, state.id AS state_id " +
                "FROM address " +
                "INNER JOIN state ON state.id = address.state";
        List<Address> address = jdbcTemplate.query(sql, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("name"));
            Address address1 = new Address(res.getInt("address_id"),
                                            state,
                                            res.getString("street_address"),
                                            res.getString("city"),
                                            res.getString("postal_code"));
            return address1;
        });

        return address;
    }

    @Override
    public Optional<Address> getById(String id) {
        String sql = "SELECT *, address.id AS address_id, state.id AS state_id " +
                     "FROM address " +
                     "INNER JOIN state ON state.id = address.state " +
                     "WHERE address.id = ?";
        Address address = jdbcTemplate.queryForObject(sql, new Object[]{Integer.valueOf(id)}, (res, rowNum) -> {
            State state = new State(res.getString("state_id"), res.getString("name"));
            Address address1 = new Address(res.getInt("address_id"),
                                            state,
                                            res.getString("street_address"),
                                            res.getString("city"),
                                            res.getString("postal_code"));
            return address1;
        });

        return Optional.ofNullable(address);
    }

    @Override
    // No-op implementation of getByName()
    public Optional<Address> getByName(String name) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    // No-op implementation of getBySpecialCriteria()
    public Optional<List<Address>> getBySpecialCriteria(String category) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    public void add(Address address) {
        String sql = "INSERT INTO address (state, street_address, city, postal_code) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                            address.state().id(),
                            address.streetAddress(),
                            address.city(),
                            address.postalCode());
    }

    @Override
    public void update(Address address, String id) {
        String sql = "UPDATE address SET state = ?, street_address = ?, city = ?, postal_code = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                            address.state().id(),
                            address.streetAddress(),
                            address.city(),
                            address.postalCode(),
                            Integer.valueOf(id));
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM address WHERE id = ?";
        jdbcTemplate.update(sql, Integer.valueOf(id));
    }
}
