package com.dreamers.gsim.DAO;

import model.ProductCatalog;
import model.SalesBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
public class SalesImpl implements SalesDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductCatalogImpl productCatalogimpl;

    public SalesImpl(JdbcTemplate jdbcTemplate, ProductCatalogImpl productCatalogimpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.productCatalogimpl = productCatalogimpl;
    }

    /**
     * This method returns the best selling products between a given date range
     * @param startDate
     * @param endDate
     * @return
     */

    @Override
    public List<SalesBook> getBestSellers(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SalesBook.id AS SID,salesbook.sku AS product_sku, productcatalog.product_name, productcatalog.product_price " +
                "AS product_price, SUM(salesbook.quantity) AS sales_quantity, salesbook.date_of_sale  " +
                "FROM salesbook "+
                "INNER JOIN productcatalog ON salesbook.sku = productcatalog.sku " +
                "WHERE salesbook.date_of_sale  BETWEEN ? AND ? " +
                "GROUP BY salesbook.id , productCatalog.product_name , productCatalog.product_price " +
                "ORDER BY salesbook.quantity DESC LIMIT 10";

        List<SalesBook> salesBooks = jdbcTemplate.query(sql, new Object[]{startDate, endDate}, (res, rowNum) -> {
            ProductCatalog productCatalog = productCatalogimpl.getById(res.getString("product_sku")).orElseThrow(() -> new RuntimeException("No product found"));
            SalesBook salesBook = new SalesBook(res.getInt("SID"), productCatalog, res.getInt("sales_quantity"), startDate);
            return salesBook;
        });
        return salesBooks;
        }
    }

