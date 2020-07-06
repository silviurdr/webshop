package com.codecool.shop.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.LogManager;

public class dbConnection {



	private static LogManager dbProperties;
	private static final String DATABASE;
	private static final String DB_USER;
	private static final String DB_PASS;

	static {
		assert false;
		DATABASE = dbProperties.getProperty("url");
		DB_USER = dbProperties.getProperty("user");
		DB_PASS = dbProperties.getProperty("pass");
	}




	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(DATABASE, DB_USER, DB_PASS);
			return DriverManager.getConnection(
					DATABASE,
					DB_USER,
					DB_PASS);
		} catch (SQLException e) {
			System.err.println("ERROR: Connection error.");
			e.printStackTrace();
		}
		return null;
	}
}
