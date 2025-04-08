package com.TechShop.Exception;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}