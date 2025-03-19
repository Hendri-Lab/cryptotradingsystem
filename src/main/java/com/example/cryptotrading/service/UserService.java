package com.example.cryptotrading.service;

import com.example.cryptotrading.models.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
}
