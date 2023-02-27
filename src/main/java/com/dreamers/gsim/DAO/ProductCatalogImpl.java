package com.dreamers.gsim.DAO;

import model.Category;
import model.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductCatalogImpl implements DAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ProductCatalogImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductCatalog> getAll() {

     String sql = "SELECT * FROM productcatalog INNER JOIN category ON productcatalog.product_category = category.id";
        List<ProductCatalog> products = jdbcTemplate.query(sql, (res,rowNum) -> {
            Category category = new Category(res.getInt("id"), res.getString("name"));
            ProductCatalog product = new ProductCatalog(res.getString("sku"), category, res.getString("product_name"), res.getString("product_description"), res.getBigDecimal("product_price"), res.getBytes("productimage"));
            return product;
        });

        return products;
    }


    @Override
    public Optional<ProductCatalog> getById(String id) {
        String sql = "SELECT * FROM productcatalog INNER JOIN category ON productcatalog.product_category = category.id WHERE productcatalog.sku = ?";

        Optional<ProductCatalog> product = Optional.ofNullable(queryMapper(id, sql).orElseThrow(() -> new RuntimeException("No product found")));

        return product;
    }

    @Override
    public Optional<ProductCatalog> getByName(String name) {
        String sql = "SELECT * FROM productcatalog INNER JOIN category ON productcatalog.product_category = category.id WHERE productcatalog.product_name = ?";
        Optional<ProductCatalog> product = Optional.ofNullable(queryMapper(name, sql).orElseThrow(() -> new RuntimeException("No product found")));
        return product;
    }

    private Optional<ProductCatalog> queryMapper(String name, String sql) {
        ProductCatalog products = jdbcTemplate.queryForObject(sql, new Object[]{name}, (res, rowNum) -> {
            Category category = new Category(res.getInt("id"), res.getString("name"));
            ProductCatalog product = new ProductCatalog(res.getString("sku"), category, res.getString("product_name"), res.getString("product_description"), res.getBigDecimal("product_price"), res.getBytes("productimage"));
            return product;
        });
        return Optional.ofNullable(products);
    }

    @Override
    public Optional<List<ProductCatalog>> getBySpecialCriteria(String category) {
        String sql = "SELECT * FROM productcatalog INNER JOIN category ON productcatalog.product_category = category.id WHERE category.name = ?";
        List<ProductCatalog> product = jdbcTemplate.query(sql, new Object[]{category}, (res, rowNum) -> {
            Category category1 = new Category(res.getInt("id"), res.getString("name"));
            ProductCatalog product1 = new ProductCatalog(res.getString("sku"), category1, res.getString("product_name"), res.getString("product_description"), res.getBigDecimal("product_price"), res.getBytes("productimage"));
            return product1;
        });
        return Optional.ofNullable(product);

    }

    @Override
    public void add(ProductCatalog product) {

        ProductCatalog productCatalog = new ProductCatalog(product.sku(), product.category(), product.productName(), product.productDescription(), product.productPrice(), product.productImage());
        String sql = "INSERT INTO productcatalog (sku, product_category, product_name, product_description, product_price, productimage) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, productCatalog.sku(),
                productCatalog.category().id(),
                productCatalog.productName(),
                productCatalog.productDescription(),
                productCatalog.productPrice(),
                productCatalog.productImage());
    }


    @Override
    public void update(ProductCatalog product, String id) {


        String sql = "UPDATE productcatalog SET sku = ?, product_category = ?, product_name = ?, product_description = ?, product_price = ?, productimage = ? WHERE sku = ?";

        jdbcTemplate.update(sql,
                product.sku(),
                product.category().id(),
                product.productName(),
                product.productDescription(),
                product.productPrice(),
                product.productImage(),
                id);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM productcatalog WHERE sku = ?";
        jdbcTemplate.update(sql, id);
    }
}
