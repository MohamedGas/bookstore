package com.dreamers.gsim.DAO;

import model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryDAO{

    public List<Inventory> getInventory();
    public Optional<Inventory> getInventoryBySku(String sku);
    public void reUpTheStock();
    public void reUpTheStockIndvividualy(String id);

    public List<Inventory> runnigOutInventory();

}
