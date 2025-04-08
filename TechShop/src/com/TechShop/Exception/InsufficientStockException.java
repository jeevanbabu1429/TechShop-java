package com.TechShop.Exception;

@SuppressWarnings("serial")
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}