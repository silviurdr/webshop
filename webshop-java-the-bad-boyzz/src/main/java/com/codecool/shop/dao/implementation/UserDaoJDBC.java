package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {


    private DataSource dataSource;

    @Override
    public void add(User user) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;


        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO user (full_name, email, mobile_number, password) VALUES(?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getMobileNumber());
            preparedStatement.setString(4, user.getPassword());

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
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE user_id=?";
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
    public void remove(int id) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = dataSource.getConnection();
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
            conn = dataSource.getConnection();
            String sql = "SELECT * from user WHERE email=?";
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
            conn = dataSource.getConnection();
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
