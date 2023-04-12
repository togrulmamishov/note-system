package com.proxyseller.service;

import com.proxyseller.dto.UserRequest;
import com.proxyseller.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers(int page);
    UserResponse registerUser(UserRequest request);
    UserResponse editUser(String username, UserRequest request);
    void deleteUser(String username);
}
