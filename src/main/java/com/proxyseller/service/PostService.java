package com.proxyseller.service;

import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;
import com.proxyseller.model.Post;

import java.util.List;

public interface PostService {

    List<PostResponse> getAllPosts(int pageNumber);

    Post getPost(String id);

    PostResponse createPost(PostRequest request);

    void manageLikes(String id, String action);
}
