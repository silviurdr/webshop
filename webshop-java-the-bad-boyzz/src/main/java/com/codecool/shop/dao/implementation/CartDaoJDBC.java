package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.*;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public HashMap getCartProducts(int order_id) throws SQLException {
		HashMap<Integer, Integer> cartProducts = new HashMap<>();
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("SELECT * FROM orders_products WHERE order_id = ?;");
		stmt.setInt(1, order_id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int product_id = rs.getInt("product_id");
			int product_quantity = rs.getInt("product_quantity");
			cartProducts.put(product_id, product_quantity);
		}
		return cartProducts;
	}

	@Override
	public void updateProductQuantity(Product product, Cart cart, int quantity) throws SQLException {
		Connection conn = myConn.getConnection();
		assert conn != null;
		if (quantity==0){
			remove(product,cart);
		}else {
			PreparedStatement stmt = conn.prepareStatement
					("UPDATE orders_products SET product_quantity=? " +
							"WHERE order_id=? AND product_id=?;");
			stmt.setInt(1, quantity);
			stmt.setInt(2, cart.getId());
			stmt.setInt(3, product.getId());
			stmt.executeUpdate();
		}
	}

	@Override
	public void add(Product product,int id, int quantity, HashMap<Integer, Integer> cartProducts) throws SQLException {
    	Connection conn = myConn.getConnection();
		assert conn != null;
		int product_id = product.getId();
		if(cartProducts.containsKey(product_id)){
			PreparedStatement stmt = conn.prepareStatement
					("UPDATE orders_products SET product_quantity=product_quantity+? " +
							"WHERE order_id=? AND product_id=?;");
			stmt.setInt(1, quantity);
			stmt.setInt(2, id);
			stmt.setInt(3, product.getId());
			stmt.executeUpdate();
		}else {
			PreparedStatement stmt = conn.prepareStatement
					("INSERT INTO orders_products (order_id , product_id , product_quantity) " +
							"values (?, ?, ?);");
			stmt.setInt(1, id);
			stmt.setInt(2, product.getId());
			stmt.setInt(3, quantity);
			stmt.executeUpdate();
		}
	}

	@Override
	public void add(Product product,int id, HashMap<Integer, Integer> cartProducts) throws SQLException {
		add(product, id,1, cartProducts);
	}

	@Override
	public void updateCustomerInfo(Cart cart) throws SQLException {
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("UPDATE orders " +
						" SET customer_name=?, customer_email=?, customer_phone=?, customer_country=?, customer_zip=?, customer_city=?, customer_address=? " +
						"WHERE id=? AND user_id=?;" );
		stmt.setString(1, cart.getCustomerName());
		stmt.setString(2, cart.getCustomerEmail());
		stmt.setString(3, cart.getCustomerPhone());
		stmt.setString(4, cart.getCustomerCountry());
		stmt.setString(5, cart.getCustomerZip());
		stmt.setString(6, cart.getCustomerCity());
		stmt.setString(7, cart.getCustomerAddress());
		stmt.setInt(8, cart.getId());
		stmt.setInt(9, cart.getCustomerId());

		stmt.executeUpdate();
	}

	@Override
	public void connectUserToCart(int order_id, int user_id) throws SQLException {
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("UPDATE orders SET user_id=? " +
						"WHERE id=?;");
		stmt.setInt(1, user_id);
		stmt.setInt(2, order_id);
		stmt.executeUpdate();


	}

	@Override
	public Cart createCart(User user) throws SQLException {
		Cart cart = new Cart();
		Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
				("INSERT INTO orders (user_id) values (?);", Statement.RETURN_GENERATED_KEYS);
		if(user==null){
			stmt.setNull(1, Types.INTEGER);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				cart.setId(id);
			}
		}else{
			stmt.setInt (1, user.getId());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				cart.setId(id);
				cart.setCustomerId(user.getId());
			}
		}
		return cart;
		}



    @Override
    public Cart findById(int id) throws SQLException {
    	if (id==0){
    		return null;
		}
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
			int id = rs.getInt("id");
			cart.updateCustomerData(customer_name, customer_email, customer_phone, customer_country, customer_zip, customer_city, customer_address);
			cart.setId(id);
			cart.setCustomerId(user_id);
			return cart;
		}else {
			return null;
		}

	}

    @Override
    public void remove(Product product, Cart cart) throws SQLException {
    	Connection conn = myConn.getConnection() ;
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders_products WHERE order_id = ? AND product_id= ?;");
		stmt.setInt(1, cart.getId());
		stmt.setInt(2, product.getId());
		stmt.executeUpdate();

    }

    @Override
    public ConcurrentHashMap<Product, Integer> getCartProducts(Cart cart) throws SQLException {
    	ConcurrentHashMap<Product, Integer> products = new ConcurrentHashMap<>();
    	Connection conn = myConn.getConnection();
		assert conn != null;
		PreparedStatement stmt = conn.prepareStatement
					("SELECT * FROM products JOIN orders_products ON id=product_id WHERE order_id=?;");
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
			int quantity = rs.getInt("product_quantity");
			products.put( new Product(id, name, price, currency, description, productCategoryDaoJDBC.find(category_id) , supplierDao.find(supplier_id), image), quantity);
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