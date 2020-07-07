package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

	private static ProductDaoJDBC instance = null;
	private SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
	private ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();

	dbConnection myConn = dbConnection.getInstance();

	public ProductDaoJDBC() throws IOException {
	}


	public static ProductDaoJDBC getInstance() throws IOException {
		if (instance == null) {
			instance = new ProductDaoJDBC();
		}
		return instance;
	}

	@Override
	public void add(Product product) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("INSERT INTO products (id, supplier_id, category_id, name, description, image, price, currency) " +
							"values (?, ?, ?, ?,?, ?, ?, ?)")) {
				stmt.setInt(1, product.getId());
				stmt.setInt(2, product.getSupplier().getId());
				stmt.setInt(3, product.getProductCategory().getId());
				stmt.setString(4, product.getName());
				stmt.setString(5, product.getDescription());
				stmt.setString(6, product.getImage());
				stmt.setFloat(7, product.getDefaultPrice());
				stmt.setString(8, product.getDefaultCurrency().toString());
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}


	@Override
	public Product find(int id) {

		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products WHERE " + "id = ?;")) {
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					String name = rs.getString("name");
					int supplierId = rs.getInt("supplier_id");
					int categoryId = rs.getInt("category_id");
					String description = rs.getString("description");
					String image = rs.getString("image");
					float price = rs.getFloat("price");
					String currency = rs.getString("currency");
					return new Product(name, price, currency, description, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image);
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
			try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE id = ?");) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}


	@Override
	public List<Product> getAll()  {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products")) {
				ResultSet rs = stmt.executeQuery();
				List<Product> products = new ArrayList<>();
				while (rs.next()) {
					String name = rs.getString("name");
					int supplierId = rs.getInt("supplier_id");
					int categoryId = rs.getInt("category_id");
					String description = rs.getString("description");
					String image = rs.getString("image");
					float price = rs.getFloat("price");
					String currency = rs.getString("currency");
					products.add(new Product(name, price, currency, description, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image));
				}
				return products;
			}
		} catch (
				SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}




	@Override
	public List<Product> getBy(Supplier supplier) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products WHERE supplier_id = ?")) {
				stmt.setInt(1, supplier.getId());
				ResultSet rs = stmt.executeQuery();
				List<Product> products = new ArrayList<>();
				if (rs.next()) {
					String name = rs.getString("name");
					int supplierId = rs.getInt("supplier_id");
					int categoryId = rs.getInt("category_id");
					String description = rs.getString("description");
					String image = rs.getString("image");
					float price = rs.getFloat("price");
					String currency = rs.getString("currency");
					products.add(new Product(name, price, currency, description, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image));
				}
				return products;
			}
		} catch (
				SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getBy(ProductCategory productCategory) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products WHERE category_id = ?")) {
				stmt.setInt(1, productCategory.getId());
				ResultSet rs = stmt.executeQuery();
				List<Product> products = new ArrayList<>();
				if (rs.next()) {
					String name = rs.getString("name");
					int supplierId = rs.getInt("supplier_id");
					int categoryId = rs.getInt("category_id");
					String description = rs.getString("description");
					String image = rs.getString("image");
					float price = rs.getFloat("price");
					String currency = rs.getString("currency");
					products.add(new Product(name, price, currency, description, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image));
				}
				return products;
			}
		} catch (
				SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
		try (Connection conn = myConn.getConnection()) {
			assert conn != null;
			try (PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products WHERE supplier_id = ? AND category_id = ?")) {
				stmt.setInt(1, supplier.getId());
				stmt.setInt(1, productCategory.getId());
				ResultSet rs = stmt.executeQuery();
				List<Product> products = new ArrayList<>();
				if (rs.next()) {
					String name = rs.getString("name");
					int supplierId = rs.getInt("supplier_id");
					int categoryId = rs.getInt("category_id");
					String description = rs.getString("description");
					String image = rs.getString("image");
					float price = rs.getFloat("price");
					String currency = rs.getString("currency");
					products.add(new Product(name, price, currency, description, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image));
				}
				return products;
			}
		} catch (
				SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
}
