package com.TechShop.Exception;

@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception {
  public DatabaseConnectionException(String message) {
      super(message);
  }
}