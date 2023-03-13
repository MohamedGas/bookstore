package com.dreamers.gsim.DAO;

import model.Inventory;
import model.Location;
import model.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InventoryImpl  implements  InventoryDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductCatalogImpl productCatalogImpl;
    private LocationImpl locationImpl;

    public InventoryImpl(JdbcTemplate jdbcTemplate, ProductCatalogImpl productCatalogImpl, LocationImpl locationImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.productCatalogImpl = productCatalogImpl;
        this.locationImpl = locationImpl;
    }

    @Override
    public List<Inventory> getInventory() {
        String sql =    "SELECT Inventory.id, Inventory.sku,location_id,quantity,expiration_date " +
                "FROM inventory " +
                "INNER JOIN productcatalog ON inventory.sku = productcatalog.sku " +
                "INNER JOIN location ON inventory.location_id = location.id ";

        RowMapper<Inventory> inventoryRowMapper = (res, rowNum) -> {
            ProductCatalog productCatalog = productCatalogImpl.getById(res.getString("sku")).orElseThrow(()-> new RuntimeException("No product found"));
            Location location = locationImpl.getById(res.getString("location_id")).orElseThrow(()-> new RuntimeException("No location found"));

            Inventory inventory = new Inventory(res.getInt("id"), productCatalog, location, res.getInt("quantity"), res.getTimestamp("expiration_date"));
            return inventory;
        };
        return jdbcTemplate.query(sql, inventoryRowMapper);

    }

    @Override
    public Optional<Inventory> getInventoryBySku(String sku) {
        String sql =    "SELECT Inventory.id, Inventory.sku,location_id,quantity,expiration_date " +
                "FROM inventory " +
                "INNER JOIN productcatalog ON inventory.sku = productcatalog.sku " +
                "INNER JOIN location ON inventory.location_id = location.id "
                + "WHERE inventory.sku = ?";

        RowMapper<Inventory> inventoryRowMapper = (res, rowNum) -> {
            ProductCatalog productCatalog = productCatalogImpl.getById(res.getString("sku")).orElseThrow(()-> new RuntimeException("No product found"));
            Location location = locationImpl.getById(res.getString("location_id")).orElseThrow(()-> new RuntimeException("No location found"));

            Inventory inventory = new Inventory(res.getInt("id"), productCatalog, location, res.getInt("quantity"), res.getTimestamp("expiration_date"));
            return inventory;
        };
        Inventory inventory = jdbcTemplate.queryForObject(sql, inventoryRowMapper, sku);
        return Optional.ofNullable(inventory);

    }

    /*
    * This method will update the inventory table with the new quantity
    * from the inventory audit table based on the adjustment type and quantity adjusted

     */
    @Override
    public void reUpTheStock() {

        String sql = " UPDATE inventory " +
                "SET quantity = CASE " +
                "WHEN adjustment_type = (SELECT id FROM adjustmenttype WHERE name = 'Restocked') " +
                "THEN quantity + quantity_adjusted " +
                "WHEN adjustment_type IN (SELECT id FROM adjustmenttype WHERE name IN ('Damaged', 'Sold')) " +
                "THEN quantity - quantity_adjusted " +
                "END " +
                "FROM inventoryaudit " +
                "WHERE inventory.id = inventoryaudit.inventory_id";

        jdbcTemplate.update(sql);

    }
    /*
    * This method will update the inventory table with the new quantity
    * from the inventory audit table based on the adjustment type and quantity adjusted
    * and the sku of the product
    *
     */
    @Override
    public void reUpTheStockIndvividualy(String id) {

            String sql = " UPDATE inventory " +
                    "SET quantity = CASE " +
                    "WHEN adjustment_type IN (SELECT id FROM adjustmenttype WHERE name IN ('Restocked', 'Refund')) " +
                    "THEN quantity + quantity_adjusted " +
                    "WHEN adjustment_type IN (SELECT id FROM adjustmenttype WHERE name IN ('Damaged', 'Sold', 'Stale')) " +
                    "THEN quantity - quantity_adjusted " +
                    "END " +
                    "FROM inventoryaudit " +
                    "WHERE inventory.id = inventoryaudit.inventory_id " +
                    "AND inventory.id = (select id from inventory where sku = ?)";
        jdbcTemplate.update(sql, id);

    }


    /*
    * This method will return a list of inventory that is running out
    * based on the quantity in the inventory table
     */
    @Override
    public List<Inventory> runnigOutInventory() {

        String sql =    "SELECT Inventory.id, Inventory.sku,location_id,quantity,expiration_date " +
                        "FROM inventory " +
                        "INNER JOIN productcatalog ON inventory.sku = productcatalog.sku " +
                        "INNER JOIN location ON inventory.location_id = location.id " +
                        "WHERE quantity < 5 ";

            RowMapper<Inventory> inventoryRowMapper = (res, rowNum) -> {
            ProductCatalog productCatalog = productCatalogImpl.getById(res.getString("sku")).orElseThrow(()-> new RuntimeException("No product found"));
            Location location = locationImpl.getById(res.getString("location_id")).orElseThrow(()-> new RuntimeException("No location found"));

            Inventory inventory = new Inventory(res.getInt("id"), productCatalog, location, res.getInt("quantity"), res.getTimestamp("expiration_date"));
            return inventory;
        };
        return jdbcTemplate.query(sql, inventoryRowMapper);

    }
}
