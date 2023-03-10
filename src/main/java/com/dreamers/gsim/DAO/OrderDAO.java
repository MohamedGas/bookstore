package com.dreamers.gsim.DAO;

import model.Order;

public interface OrderDAO {

    void addOrder(Order order, int supplierId, String SKU, int quantity);

    void cancelOrder(int order);
}
