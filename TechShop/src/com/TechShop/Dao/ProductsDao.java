package com.TechShop.Dao;

import java.util.List;

import com.TechShop.Entity.*;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.ProductNotFoundException;

public interface ProductsDao {
	void addProduct(Products product) throws DataAccessException;
    Products getProductById(int productId) throws ProductNotFoundException, DataAccessException;
    List<Products> getAllProducts() throws DataAccessException;
    void updateProduct(Products product) throws DataAccessException;
    void deleteProduct(int productId) throws DataAccessException;

}