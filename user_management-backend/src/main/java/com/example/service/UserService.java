package com.example.service;

import java.util.List;

import com.example.entity.User;


public interface UserService {
    void fetchAndStoreExternalUsers();
    List<User> getAllUsers();
    List<User> searchUsers(String query);
    User getUserById(Long id);
}
