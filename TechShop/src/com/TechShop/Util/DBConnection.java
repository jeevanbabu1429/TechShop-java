package com.TechShop.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3307/techshop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static Connection connection;
	
	private DBConnection() {}
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Connection Established");
			}
			catch(SQLException e){
				System.err.println("Database Connection failed..."+e.getMessage());		
			}
		}
		return connection;	
	}
	
}