package com.TechShop.Dao;

import com.TechShop.Entity.Customers;
import com.TechShop.Entity.Orders;
import com.TechShop.Entity.OrderDetails;
import com.TechShop.Entity.Products;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.OrderDetailNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private Connection connection;

    public OrderDetailsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void addOrderDetail(OrderDetails orderDetail) throws DataAccessException {
        String query = "INSERT INTO orderDetails (orderDetailID, orderID, productID, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetail.getOrderDetailID());
            stmt.setInt(2, orderDetail.getOrder().getOrderID());
            stmt.setInt(3, orderDetail.getProduct().getProductID());
            stmt.setInt(4, orderDetail.getQuantity());
            stmt.executeUpdate();
            System.out.println("OrderDetail record added");
        } catch (SQLException e) {
            throw new DataAccessException("Failed to add order detail", e);
        }
    }

    @Override
    public OrderDetails getOrderDetailById(int orderDetailId) throws OrderDetailNotFoundException, DataAccessException {
        String query = "SELECT * FROM orderDetails WHERE orderDetailID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetailId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Orders order = new Orders(
                    rs.getInt("orderID"),
                    new Customers(rs.getInt("customerID"), "", "", "", "", ""), // Placeholder for Customer object
                    rs.getDate("orderDate"),
                    rs.getDouble("totalAmount")
                );
                Products product = new Products(
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getBoolean("instock")
                );
                return new OrderDetails(
                    rs.getInt("orderDetailID"),
                    order,
                    product,
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve order detail by ID", e);
        }
        throw new OrderDetailNotFoundException("OrderDetail not found with ID: " + orderDetailId);
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() throws DataAccessException {
        List<OrderDetails> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM orderDetails";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Orders order = new Orders(
                    rs.getInt("orderID"),
                    new Customers(rs.getInt("customerID"), "", "", "", "", ""), // Placeholder for Customer object
                    rs.getDate("orderDate"),
                    rs.getDouble("totalAmount")
                );
                Products product = new Products(
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getBoolean("instock")
                );
                orderDetails.add(new OrderDetails(
                    rs.getInt("orderDetailID"),
                    order,
                    product,
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all order details", e);
        }
        return orderDetails;
    }

    public void updateOrderDetail(OrderDetails orderDetail) throws DataAccessException {
        String query = "UPDATE orderDetails SET orderID = ?, productID = ?, quantity = ? WHERE orderDetailID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetail.getOrder().getOrderID());
            stmt.setInt(2, orderDetail.getProduct().getProductID());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setInt(4, orderDetail.getOrderDetailID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update order detail", e);
        }
    }

    public void deleteOrderDetail(int orderDetailId) throws DataAccessException {
        String query = "DELETE FROM orderDetails WHERE orderDetailID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetailId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete order detail", e);
        }
    }
}