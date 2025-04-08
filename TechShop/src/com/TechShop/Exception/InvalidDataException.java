package com.TechShop.Exception;

@SuppressWarnings("serial")
public class InvalidDataException extends Exception{
	public InvalidDataException(String message) {
        super(message);
    }
}