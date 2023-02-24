package model;

import java.math.BigDecimal;

public record ProductCatalog (String sku, Category category, String productName, String productDescription, BigDecimal productPrice, byte[] productImage) {

}
