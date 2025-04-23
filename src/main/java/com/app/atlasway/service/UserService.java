package com.app.atlasway.service;

import com.app.atlasway.dao.UserDao;
import com.app.atlasway.models.User;
import com.app.atlasway.util.PasswordHash;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDao userDAO;

    public UserService() {
        this.userDAO = new UserDao();
    }

    public User registerUser(User user) throws Exception {
        // Check if username exists
        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }

        // Check if email exists
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }

        // In a real application, hash the password before storing
        user.setPassword(PasswordHash.hashPassword(user.getPassword()));

        return userDAO.save(user);
    }

    public Optional<User> loginUser(String username, String password) throws SQLException {
        // In a real application, verify the password hash
        password = PasswordHash.hashPassword(password);
        if (userDAO.authenticate(username, password)) {
            return userDAO.findByUsername(username);
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(int userId) throws SQLException {
        return userDAO.findById(userId);
    }

    public User updateUser(User user) throws SQLException {
        return userDAO.save(user);
    }

    public boolean deleteUser(int userId) throws SQLException {
        return userDAO.delete(userId);
    }

    public List<User> getAllManagers() throws SQLException {
        return userDAO.findAllByUserType(User.UserType.MANAGER);
    }

    public List<User> getAllTourists() throws SQLException {
        return userDAO.findAllByUserType(User.UserType.TOURIST);
    }


}
