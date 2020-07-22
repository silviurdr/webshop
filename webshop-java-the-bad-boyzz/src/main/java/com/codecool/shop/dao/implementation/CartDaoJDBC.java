package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.*;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CartDaoJDBC implements CartDao {

	SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
	ProductCategoryDao productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();

    private static CartDaoJDBC instance = null;


    dbConnection myConn = dbConnection.getInstance();

    public CartDaoJDBC() throws IOException {
	}


	public static CartDaoJDBC getInstance() throws IOException {
		if (instance == null) {
			instance = new CartDaoJDBC();
		}
		return instance;
	}

    @Override
	public void add(Product product,Cart cart, int quantity) throws SQLException {
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("INSERT INTO orders_products (order_id , product_id , product_quantity) " +
						"values (?, ?, ?)");
		stmt.setInt(1, cart.getId());
		stmt.setInt(2, product.getId());
		stmt.setInt(3, quantity);
		stmt.executeUpdate();
	}

	@Override
	public void add(Product product, Cart cart) throws SQLException {
		add(product, cart,1);
	}

	@Override
	public Cart createCart(User user) throws SQLException {
		Cart cart = new Cart();
		Connection conn = myConn.getConnection();
		assert conn != null;
		try (PreparedStatement stmt = conn.prepareStatement
				("INSERT INTO orders (user_id) " +
						"values (?)")) {
			stmt.setInt(1, user.getId());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				cart.setId(id);
				cart.setCustomerId(user.getId());
			}
			return cart;
		}
	}


    @Override
    public Cart findById(int id) throws SQLException {
		Cart cart = new Cart();
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("SELECT * FROM orders WHERE " + " id = ?;");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String customer_name = rs.getString("customer_name");
			String customer_email = rs.getString("customer_email");
			String customer_phone = rs.getString("customer_phone");
			String customer_country = rs.getString("customer_country");
			String customer_zip = rs.getString("customer_zip");
			String customer_city = rs.getString("customer_city");
			String customer_address = rs.getString("customer_address");
			int customer_id = rs.getInt("user_id");
			cart.updateCustomerData(customer_name, customer_email, customer_phone, customer_country, customer_zip, customer_city, customer_address);
			cart.setCustomerId(customer_id);
		}
		return cart;
    }

	@Override
	public Cart findByUserID(int user_id) throws SQLException {
		Cart cart = new Cart();
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("SELECT * FROM orders WHERE user_id = ?;");
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String customer_name = rs.getString("customer_name");
			String customer_email = rs.getString("customer_email");
			String customer_phone = rs.getString("customer_phone");
			String customer_country = rs.getString("customer_country");
			String customer_zip = rs.getString("customer_zip");
			String customer_city = rs.getString("customer_city");
			String customer_address = rs.getString("customer_address");
			int id = rs.getInt("user_id");
			cart.updateCustomerData(customer_name, customer_email, customer_phone, customer_country, customer_zip, customer_city, customer_address);
			cart.setId(id);
			return cart;
		}else {
			return null;
		}

	}

    @Override
    public void remove(int product_id, int user_id) throws SQLException {
    	Connection conn = myConn.getConnection() ;
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders_products WHERE order_id = ? AND user_id= ? ");
		stmt.setInt(1, product_id);
		stmt.setInt(2, user_id);
		stmt.executeUpdate();

    }

    @Override
    public ConcurrentHashMap<Product, Integer> getCartProducts(Cart cart) throws SQLException {
    	ConcurrentHashMap<Product, Integer> products = new ConcurrentHashMap<>();
    	Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products JOIN orders_products ON id=product_id WHERE order_id=?");
		stmt.setInt(1,cart.getId());
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			int supplier_id = rs.getInt("supplier_id");
			int category_id = rs.getInt("category_id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			String image = rs.getString("image");
			float price = rs.getFloat("price");
			String currency = rs.getString("currency");
			products.merge( new Product(id, name, price, currency, description, productCategoryDaoJDBC.find(category_id) , supplierDao.find(supplier_id), image), 1, Integer::sum);
		}
		return products;
	}

    @Override
    public void clearCart(Cart cart) throws SQLException {
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders_products WHERE order_id = ?");
		stmt.setInt(1, cart.getId());
		stmt.executeUpdate();
    }

    @Override
	public void Jsonify(Cart cart) {
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Name", cart.getCustomerName());
        jsonObject.put("Phone", cart.getCustomerPhone());
        jsonObject.put("Adress", cart.getCustomerAddress());
        jsonObject.put("City", cart.getCustomerCity());
        jsonObject.put("Country", cart.getCustomerCountry());
        jsonObject.put("Email", cart.getCustomerEmail());
        jsonObject.put("Zip", cart.getCustomerZip());

        try {
            FileWriter file = new FileWriter("src/main/webapp/static/json/paymentDetails.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}