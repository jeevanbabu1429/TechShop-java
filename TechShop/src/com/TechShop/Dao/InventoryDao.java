package com.TechShop.Dao;


import com.TechShop.Entity.Inventory;
import com.TechShop.Exception.DataAccessException;
import com.TechShop.Exception.InventoryNotFoundException;

import java.util.List;

public interface InventoryDao {
    void addInventory(Inventory inventory) throws DataAccessException;
    Inventory getInventoryById(int inventoryId) throws DataAccessException, InventoryNotFoundException;
    List<Inventory> getAllInventories() throws DataAccessException;
    void updateInventory(Inventory inventory) throws DataAccessException;
    void deleteInventory(int inventoryId) throws DataAccessException;
}