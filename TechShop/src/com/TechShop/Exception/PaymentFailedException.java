package com.TechShop.Exception;

@SuppressWarnings("serial")
public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}