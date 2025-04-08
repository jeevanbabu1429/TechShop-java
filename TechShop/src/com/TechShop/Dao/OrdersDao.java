package com.TechShop.Dao;

import com.TechShop.Entity.Orders;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.OrderNotFoundException;

import java.util.List;

public interface OrdersDao {
    void addOrder(Orders order) throws DataAccessException;
    Orders getOrderById(int orderId) throws DataAccessException, OrderNotFoundException;
    List<Orders> getAllOrders() throws DataAccessException;
    void updateOrder(Orders order) throws DataAccessException;
    void deleteOrder(int orderId) throws DataAccessException;
}