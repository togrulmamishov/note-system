package com.proxyseller.service;

import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getAllPosts(int pageNumber);

    PostResponse createPost(PostRequest request);
}
