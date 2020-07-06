package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductCategoryDao;

import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

	private static ProductCategoryDao instance = null;

	public static ProductCategoryDao getInstance() throws IOException {
		if (instance == null) instance = new ProductCategoryDaoJDBC();
		return instance;
	}

	@Override
	public void add(ProductCategory category) {
		try (Connection conn = dbConnection.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("INSERT INTO products (id, name, department, desciption) " +
							"values (?, ? ,? ,?)")) {
				stmt.setInt(1, category.getId());
				stmt.setString(2, category.getName());
				stmt.setString(3, category.getDepartment());
				stmt.setString(4, category.getDescription());
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public ProductCategory find(int id) {
		try (Connection conn = dbConnection.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM categories WHERE id = ?;")) {
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					String name = rs.getString("name");
					String department = rs.getString("department");
					String description = rs.getString("description");

					return new ProductCategory(name, department, description);
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
		try (Connection conn = dbConnection.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categories WHERE id = ?");) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public List<ProductCategory> getAll() throws SQLException {
		try (Connection conn = dbConnection.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM categories")) {
				ResultSet rs = stmt.executeQuery();
				List<ProductCategory> categories = new ArrayList<>();

				if (rs.next()) {

					String name = rs.getString("name");
					String department = rs.getString("department");
					String description = rs.getString("description");

					categories.add(new ProductCategory(name, department, description));
				}
				return categories;
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
			return null;
		}
	}
}
