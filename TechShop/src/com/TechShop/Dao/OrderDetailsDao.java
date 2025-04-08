package com.TechShop.Dao;

import com.TechShop.Entity.OrderDetails;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.OrderDetailNotFoundException;

import java.util.List;

public interface OrderDetailsDao {
    void addOrderDetail(OrderDetails orderDetail) throws DataAccessException;
    OrderDetails getOrderDetailById(int orderDetailId) throws DataAccessException, OrderDetailNotFoundException;
    List<OrderDetails> getAllOrderDetails() throws DataAccessException;
    void updateOrderDetail(OrderDetails orderDetail) throws DataAccessException;
    void deleteOrderDetail(int orderDetailId) throws DataAccessException;
}