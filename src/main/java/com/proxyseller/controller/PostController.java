package com.proxyseller.controller;

import com.proxyseller.dto.LikeRequest;
import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;
import com.proxyseller.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/posts")
public record PostController(PostService postService) {

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam(defaultValue = "0") int page) {
        return postService.getAllPosts(page);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        return new ResponseEntity<>(postService.createPost(request), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> editPost(@PathVariable String id,
                                                 @Valid @RequestBody PostRequest request) {
        return new ResponseEntity<>(postService.editPost(id, request), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> reactToPost(@PathVariable String id,
                                              @Valid @RequestBody LikeRequest request) {
        postService.manageLikes(id, request.action());
        return new ResponseEntity<>("Success", OK);
    }
}
