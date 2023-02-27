package com.dreamers.gsim.DAO;

import model.ProductCatalog;

import java.util.List;
import java.util.Optional;

public interface DAO <T> {

    public List<T> getAll();
    public Optional<T> getById(String id);
    public Optional<T> getByName(String name);
    public Optional<List<T>> getBySpecialCriteria(String category);
    public void add(T product);
    public void update(T product, String id);
    public void delete(String id);


}
