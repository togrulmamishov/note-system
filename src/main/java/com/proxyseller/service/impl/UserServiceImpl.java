package com.proxyseller.service.impl;

import com.proxyseller.dto.UserRequest;
import com.proxyseller.dto.UserResponse;
import com.proxyseller.exception.UserAlreadyExistsException;
import com.proxyseller.exception.UserNotFoundException;
import com.proxyseller.mapper.UserMapper;
import com.proxyseller.repository.UserRepository;
import com.proxyseller.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public record UserServiceImpl(UserRepository repository,
                              UserMapper userMapper,
                              PasswordEncoder passwordEncoder) implements UserService {

    @Override
    public List<UserResponse> getAllUsers(int page) {
        var pageRequest = PageRequest.of(page, 50);
        var users = repository
                .findAll(pageRequest)
                .stream()
                .toList();
        return userMapper.mapModelListToResponseList(users);
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        var username = request.username();
        if (repository.existsByUsername(username)) {
            log.error("{} already exists", username);
            throw new UserAlreadyExistsException(username + " already exists");
        }
        var user = userMapper.mapRequestToModel(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        return userMapper.mapModelToResponse(repository.save(user));
    }

    @Override
    public UserResponse editUser(String username, UserRequest request) {
        var newUsername = request.username();
        if (repository.existsByUsername(newUsername)) {
            log.error("{} already exists", newUsername);
            throw new UserAlreadyExistsException(newUsername + " already exists");
        }
        var user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        userMapper.updateUserWithDto(user, request);
        user.setPassword(passwordEncoder.encode(request.password()));
        return userMapper.mapModelToResponse(user);
    }

    @Override
    public void deleteUser(String username) {
        var user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        repository.delete(user);
        log.info("User deleted successfully. [username: {}]", username);
    }
}
