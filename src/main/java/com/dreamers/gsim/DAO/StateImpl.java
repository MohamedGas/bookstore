package com.dreamers.gsim.DAO;

import model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class StateImpl implements DAO<State>{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<State> getAll() {
        String sql = "SELECT * FROM state";
        List<State> states = jdbcTemplate.query(sql, (res, rowNum) -> {
            State state = new State(res.getString("id"), res.getString("name"));
            return state;
        });

        return states;
    }

    @Override
    public Optional<State> getById(String id) {
        String sql = "SELECT * FROM state WHERE id = ?";
        State state = jdbcTemplate.queryForObject(sql, new Object[]{id}, (res, rowNum) -> {
            State state1 = new State(res.getString("id"), res.getString("name"));
            return state1;
        });

        return Optional.ofNullable(state);
    }

    @Override
    public Optional<State> getByName(String name) {
        String sql = "SELECT * FROM state WHERE name = ?";
        State state = jdbcTemplate.queryForObject(sql, new Object[]{name}, (res, rowNum) -> {
            State state1 = new State(res.getString("id"), res.getString("name"));
            return state1;
        });

        return Optional.ofNullable(state);
    }

    @Override
    // No-op implementation of getBySpecialCriteria()
    public Optional<List<State>> getBySpecialCriteria(String category) {
        // Do nothing
        return Optional.empty();
    }

    @Override
    public void add(State state) {
        String sql = "INSERT INTO state (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, state.id(), state.name());
    }

    @Override
    public void update(State state, String id) {
        String sql = "UPDATE state SET id = ?, name = ? WHERE id = ?";
        jdbcTemplate.update(sql, state.id(),state.name(), id);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM state WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
