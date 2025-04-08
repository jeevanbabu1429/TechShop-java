package com.TechShop.Dao;

import com.TechShop.Entity.Customers;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.CustomerNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomersDaoImpl implements CustomersDao {

    private final Connection connection;

    public CustomersDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(Customers customer) throws DataAccessException {
        String query = "INSERT INTO customer(firstName, lastName, email, phone, address) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
     
            stmt.setString(1, customer.getFirstname());
            stmt.setString(2, customer.getLastname());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());

            stmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            throw new DataAccessException("Failed to add customer", e);
        }
    }

    @Override
    public Customers getUserById(int userId) throws CustomerNotFoundException, DataAccessException {
        String query = "SELECT * FROM customer WHERE customerID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            } else {
                throw new CustomerNotFoundException("Customer not found with ID: " + userId);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve customer by ID", e);
        }
    }

    @Override
    public Customers getUserByUsername(String username) throws CustomerNotFoundException, DataAccessException {
        String query = "SELECT * FROM customer WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            } else {
                throw new CustomerNotFoundException("Customer not found with email: " + username);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve customer by email", e);
        }
    }

    // Reusable method to extract Customer
    private Customers extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        return new Customers(
                rs.getInt("customerID"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address")
        );
    }

	@Override
	public List<Customers> getAllUsers() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(Customers user) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(int userId) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}
}



/* package com.TechShop.Dao;

import com.TechShop.Entity.Customers;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.CustomerNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDaoImpl implements CustomersDao {
    private Connection connection;

    public CustomersDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(Customers customer) throws DataAccessException {
        String query = "INSERT INTO customer(customerId, firstName, lastName, email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getFirstname());
            stmt.setString(3, customer.getLastname());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getAddress());
            stmt.executeUpdate();
            System.out.println("Record added");
        } catch (SQLException e) {
            throw new DataAccessException("Failed to add customer", e);
        }
    }

    @Override
    public Customers getUserById(int userId) throws CustomerNotFoundException, DataAccessException {
        String query = "SELECT * FROM customer WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customers(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve customer by ID", e);
        }
        throw new CustomerNotFoundException("Customer not found with ID: " + userId);
    }

    @Override
    public Customers getUserByUsername(String username) throws CustomerNotFoundException, DataAccessException {
        String query = "SELECT * FROM customer WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customers(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve customer by email", e);
        }
        throw new CustomerNotFoundException("Customer not found with email: " + username);
    }

    @Override
    public List<Customers> getAllUsers() throws DataAccessException {
        List<Customers> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customers(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all customers", e);
        }
        return customers;
    }

    @Override
    public void updateUser(Customers user) throws DataAccessException {
        String query = "UPDATE customer SET firstName = ?, lastName = ?, email = ?, phone = ?, address = ? WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setInt(6, user.getCustomerID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update customer", e);
        }
    }

    @Override
    public void deleteUser(int userId) throws DataAccessException {
        String query = "DELETE FROM customer WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete customer", e);
        }
    }
} */