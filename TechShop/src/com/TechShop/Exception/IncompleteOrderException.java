package com.TechShop.Exception;

@SuppressWarnings("serial")
public class IncompleteOrderException extends Exception {
    public IncompleteOrderException(String message) {
        super(message);
    }
}