package com.TechShop.Exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DataAccessException extends Exception {
    public DataAccessException(String message, SQLException sqlException) {
        super(message);
    }
}