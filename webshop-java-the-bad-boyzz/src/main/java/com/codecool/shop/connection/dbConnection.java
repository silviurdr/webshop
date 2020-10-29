package com.codecool.shop.connection;




import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class dbConnection {
	private final Properties dbProperties = new Properties();
	private static dbConnection instance;

	private dbConnection() throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("connection.properties");
		dbProperties.load(inputStream);
	}

	public static dbConnection getInstance() throws IOException {
		if (instance == null) instance = new dbConnection();
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties.getProperty("user"),
				dbProperties.getProperty("pass"));
	}


}
