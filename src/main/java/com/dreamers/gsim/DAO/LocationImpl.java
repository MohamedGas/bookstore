package com.dreamers.gsim.DAO;

import model.Location;
import model.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LocationImpl implements  DAO<Location>{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LocationImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Location> getAll() {
        String sql = "SELECT * FROM location";
        List<Location> query = jdbcTemplate.query(sql, (res, rowNum) -> {
            Location location = new Location(res.getInt("id"), res.getString("name"));
            return location;
        });
        return  query;

    }

    @Override
    public Optional<Location> getById(String id) {
        String sql = "SELECT * FROM location WHERE id = ?";
        Location location = jdbcTemplate.queryForObject(sql, new Object[]{Integer.valueOf(id)}, (res, rowNum) -> {
            Location location1 = new Location(res.getInt("id"), res.getString("name"));
            return location1;
        });
        return Optional.ofNullable(location);
    }

    @Override
    public Optional<Location> getByName(String name) {
        String sql = "SELECT * FROM location WHERE name = ?";
        Location query = jdbcTemplate.queryForObject(sql, new Object[]{name}, (res, rowNum) -> {
            Location location1 = new Location(res.getInt("id"), res.getString("name"));
            return location1;
        });
        return Optional.ofNullable(query);
    }

    @Override
    public Optional<List<Location>> getBySpecialCriteria(String category) {
        return Optional.empty();
    }

    @Override
    public void add(Location location) {
     String sql = "INSERT INTO location (name) VALUES (?)";
        jdbcTemplate.update(sql, location.name());
    }

    @Override
    public void update(Location location, String id) {
        String sql = "UPDATE location SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, location.name(), id);

    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM location WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}