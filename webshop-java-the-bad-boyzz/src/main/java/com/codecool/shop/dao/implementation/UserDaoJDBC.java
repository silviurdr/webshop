package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {


    dbConnection myConn = dbConnection.getInstance();

    private static UserDaoJDBC instance;

    public static UserDaoJDBC getInstance() throws IOException {
        if (instance == null) {
            instance = new UserDaoJDBC();
        }
        return instance;
    }

    public UserDaoJDBC() throws IOException {
    }

    @Override
    public void add(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;


        try {
            conn = myConn.getConnection();
            String sql = "INSERT INTO users (full_name, email, mobile_number, password) VALUES(?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getMobileNumber());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
            conn.close();
        }

    }

    @Override
    public User find(int id) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            conn = myConn.getConnection();
            String sql = "SELECT * FROM users WHERE user_id=?";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("full_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("mobile_number");


                User foundUser = new User(userId, userName, email, phoneNumber);
                return foundUser;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            preparedStatement.close();
            conn.close();
        }

        return null;
    }

    @Override
    public User find(String email) throws SQLException {

        Connection conn = myConn.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE email=?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, email);

        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int existingUserID = rs.getInt("user_id");
            String existingUserName = rs.getString("full_name");
            String existingUserEmail = rs.getString("email");
            String existingMobileNumber = rs.getString("mobile_number");
            User existingUser = new User(existingUserID, existingUserName, existingUserEmail, existingMobileNumber);
            return existingUser;
        }
        return null;

    }

    @Override
    public void remove(int id) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = myConn.getConnection();
            String sql = "DELETE * FROM user WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public String getUserPasswordByEmail(String email) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            conn = myConn.getConnection();
            String sql = "SELECT * from users WHERE email=?";
            preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1, email);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");
                return password;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            preparedStatement.close();
            conn.close();
        }

        return null;
    }


    @Override
    public List<User> getAll() throws SQLException {

        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = myConn.getConnection();
            String sql = "SELECT * from user";
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("full_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("mobile_number");
                User user = new User(userId, userName, email, phoneNumber);
                users.add(user);
            }
            return users;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }

        return null;
    }
}
