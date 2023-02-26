package com.dreamers.gsim.DAO;

import model.ProductCatalog;

import java.util.List;
import java.util.Optional;

public interface DAO {

    public List<ProductCatalog> getAll();
    public Optional<ProductCatalog> getById(String id);
    public Optional<ProductCatalog> getByName(String name);
    public Optional<List<ProductCatalog>> getBySpecialCriteria(String category);
    public void add(ProductCatalog product);
    public void update(ProductCatalog product, String id);
    public void delete(String id);

}
