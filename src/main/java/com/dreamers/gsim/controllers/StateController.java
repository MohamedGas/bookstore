package com.dreamers.gsim.controllers;


import com.dreamers.gsim.DAO.StateImpl;
import model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StateController {
    @Autowired
    private StateImpl stateDAOimpl;

    /**
     * Constructor injection
     * @param stateDAOimpl
     */
    public StateController(StateImpl stateDAOimpl) {
        this.stateDAOimpl = stateDAOimpl;
    }

    /**
     * Get all states
     * @return
     */
    @GetMapping("/states")
    public ResponseEntity<?> getAllStates() {
        List<State> states = stateDAOimpl.getAll();
        return ResponseEntity.ok(states);
    }

    /**
     * Get state by id
     * @param id
     * @return
     */
    @GetMapping("/states/id/{id}")
    public ResponseEntity<?> getStateById(@PathVariable String id) {
        Optional<State> stateById = stateDAOimpl.getById(id);
        return ResponseEntity.ok(stateById);
    }

    /**
     * Get state by name
     * @param name
     * @return
     */
    @GetMapping("/states/name/{name}")
    public ResponseEntity<?> getStateByName(@PathVariable String name) {
        Optional<State> stateByName = stateDAOimpl.getByName(name);
        return ResponseEntity.ok(stateByName);
    }

    /**
     * Add state
     * @param
     * @return
     */
    @PostMapping(value = "/states/add")
    public ResponseEntity<?> addState(@RequestBody State state) {
        stateDAOimpl.add(state);
        return ResponseEntity.ok(state);
    }

    /**
     * Update state
     * @param id
     * @return
     */

    @PutMapping(value = "/states/update/{id}")
    public ResponseEntity<?> updateState(@RequestBody State state, @PathVariable String id) {
        stateDAOimpl.update(state, id);
        return ResponseEntity.ok(state);
    }

    /**
     * Delete state
     * @param id
     * @return
     */
    @DeleteMapping(value = "/states/delete/{id}")
    public ResponseEntity<?> deleteState(@PathVariable String id) {
        stateDAOimpl.delete(id);
        return ResponseEntity.ok("State deleted");
    }
}
