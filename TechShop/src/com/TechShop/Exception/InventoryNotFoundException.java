package com.TechShop.Exception;

@SuppressWarnings("serial")
public class InventoryNotFoundException extends Exception {
    public InventoryNotFoundException(String message) {
        super(message);
    }
}