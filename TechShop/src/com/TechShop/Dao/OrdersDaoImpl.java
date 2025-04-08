package com.TechShop.Dao;

import com.TechShop.Entity.Customers;
import com.TechShop.Entity.Orders;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.OrderNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDaoImpl implements OrdersDao {
	private Connection connection;

	public OrdersDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void addOrder(Orders order) throws DataAccessException {
		String query = "INSERT INTO orders (orderID, customerID, orderDate, totalAmount) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, order.getOrderID());
			stmt.setInt(2, order.getCustomer().getCustomerID());
			stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
			stmt.setDouble(4, order.getTotalAmount());
			stmt.executeUpdate();
			System.out.println("Order record added");
		} catch (SQLException e) {
			throw new DataAccessException("Failed to add order", e);
		}
	}

	@Override
	public Orders getOrderById(int orderId) throws OrderNotFoundException, DataAccessException {
		String query = "SELECT * FROM orders WHERE orderID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Customers customer = new Customers(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("email"), rs.getString("phone"),
						rs.getString("address"));
				return new Orders(rs.getInt("orderID"), customer, rs.getDate("orderDate"), rs.getDouble("totalAmount"));
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to retrieve order by ID", e);
		}
		throw new OrderNotFoundException("Order not found with ID: " + orderId);
	}

	@Override
	public List<Orders> getAllOrders() throws DataAccessException {
		List<Orders> orders = new ArrayList<>();
		String query = "SELECT * FROM orders";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Customers customer = new Customers(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("email"), rs.getString("phone"),
						rs.getString("address"));
				orders.add(new Orders(rs.getInt("orderID"), customer, rs.getDate("orderDate"),
						rs.getDouble("totalAmount")));
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to retrieve all orders", e);
		}
		return orders;
	}

	@Override
	public void updateOrder(Orders order) throws DataAccessException {
		if (order.getCustomer() == null) {
			throw new IllegalArgumentException("Customer is not initialized for the order");
		}

		String query = "UPDATE orders SET customerID = ?, orderDate = ?, totalAmount = ? WHERE orderID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, order.getCustomer().getCustomerID());
			stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
			stmt.setDouble(3, order.getTotalAmount());
			stmt.setInt(4, order.getOrderID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Failed to update order", e);
		}
	}

	@Override
	public void deleteOrder(int orderId) throws DataAccessException {
		String query = "DELETE FROM orders WHERE orderID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, orderId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete order", e);
		}
	}
}