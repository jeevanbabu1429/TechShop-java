package com.TechShop.Exception;

@SuppressWarnings("serial")
public class OrderDetailNotFoundException extends Exception {
    public OrderDetailNotFoundException(String message) {
        super(message);
    }
}