package com.TechShop.Dao;

import com.TechShop.Entity.Products;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.ProductNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDaoImpl implements ProductsDao {
    private Connection connection;

    public ProductsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addProduct(Products product) throws DataAccessException {
        String query = "INSERT INTO products (productID, productName, description, price, instock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, product.getProductID());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setBoolean(5, product.isInstock());
            stmt.executeUpdate();
            System.out.println("Product record added");
        } catch (SQLException e) {
            throw new DataAccessException("Failed to add product", e);
        }
    }

    @Override
    public Products getProductById(int productId) throws ProductNotFoundException, DataAccessException {
        String query = "SELECT * FROM products WHERE productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Products(
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getBoolean("instock")
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve product by ID", e);
        }
        throw new ProductNotFoundException("Product not found with ID: " + productId);
    }

    @Override
    public List<Products> getAllProducts() throws DataAccessException {
        List<Products> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                products.add(new Products(
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getBoolean("instock")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all products", e);
        }
        return products;
    }

    @Override
    public void updateProduct(Products product) throws DataAccessException {
        String query = "UPDATE products SET productName = ?, description = ?, price = ?, instock = ? WHERE productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setBoolean(4, product.isInstock());
            stmt.setInt(5, product.getProductID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update product", e);
        }
    }

    @Override
    public void deleteProduct(int productId) throws DataAccessException {
        String query = "DELETE FROM products WHERE productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete product", e);
        }
    }
}