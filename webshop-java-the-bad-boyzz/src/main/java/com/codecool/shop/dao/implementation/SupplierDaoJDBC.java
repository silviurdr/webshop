package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;

import com.codecool.shop.dao.SupplierDao;

import com.codecool.shop.model.Supplier;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {
	
	private static SupplierDaoJDBC instance = null;
	dbConnection myConn = dbConnection.getInstance();

	public SupplierDaoJDBC() throws IOException {
	}

	public static SupplierDaoJDBC getInstance() throws IOException {
		if (instance == null) instance = new SupplierDaoJDBC();
		return instance;
	}
	
	
	@Override
	public void add(Supplier supplier) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("INSERT INTO products (name, description) values (?, ? ,?)")) {
				stmt.setString(1, supplier.getName());
				stmt.setString(2, supplier.getDescription());
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public Supplier find(int id) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM suppliers WHERE id = ?;")) {
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					String name = rs.getString("name");
					String description = rs.getString("description");

					return new Supplier(id,name, description);
				} else {
					return null;
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(int id) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM suppliers WHERE id = ?");) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public List<Supplier> getAll() {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM suppliers")) {
				ResultSet rs = stmt.executeQuery();
				List<Supplier> suppliers = new ArrayList<>();
				while (rs.next()) {
                    int id = rs.getInt("id");
					String name = rs.getString("name");
					String description = rs.getString("description");

					suppliers.add(new Supplier(id, name, description));
				}
				return suppliers;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
}
