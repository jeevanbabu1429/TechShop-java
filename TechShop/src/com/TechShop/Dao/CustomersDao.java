package com.TechShop.Dao;

import java.util.List;

import com.TechShop.Entity.*;
import com.TechShop.Exception.CustomerNotFoundException;
import com.TechShop.Exception.DataAccessException;

public interface CustomersDao {
	void addUser(Customers customer) throws DataAccessException;
    Customers getUserById(int userId) throws CustomerNotFoundException, DataAccessException;
    Customers getUserByUsername(String username) throws CustomerNotFoundException, DataAccessException;
    List<Customers> getAllUsers() throws DataAccessException;
    void updateUser(Customers user) throws DataAccessException;
    void deleteUser(int userId) throws DataAccessException;
}