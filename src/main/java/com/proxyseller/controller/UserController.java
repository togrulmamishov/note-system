package com.proxyseller.controller;

import com.proxyseller.dto.UserRequest;
import com.proxyseller.dto.UserResponse;
import com.proxyseller.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
public record UserController(UserService userService) {

    @GetMapping
    public List<UserResponse> getAllPosts(@RequestParam(defaultValue = "0") int page) {
        return userService.getAllUsers(page);
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.registerUser(request), CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse> editUser(@PathVariable String username,
                                                 @Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.editUser(username, request), OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(username + " was deleted successfully", OK);
    }
}
