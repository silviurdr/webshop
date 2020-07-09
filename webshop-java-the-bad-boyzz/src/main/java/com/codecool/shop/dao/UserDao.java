package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void add(User user) throws SQLException;
    User find(int id) throws SQLException;
    User find(String email);
    void remove(int id);
    String getUserPasswordByEmail(String string) throws SQLException;

    List<User> getAll() throws SQLException;


}