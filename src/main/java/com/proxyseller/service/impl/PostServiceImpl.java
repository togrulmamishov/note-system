package com.proxyseller.service.impl;

import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;
import com.proxyseller.mapper.PostMapper;
import com.proxyseller.repository.PostRepository;
import com.proxyseller.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public record PostServiceImpl(PostRepository postRepository,
                              PostMapper postMapper) implements PostService {

    @Override
    public List<PostResponse> getAllPosts(int pageNumber) {
        var pageable = PageRequest.of(pageNumber, 50);
        var posts = postRepository.findAllByOrderByDateAddedDesc(pageable);
        return postMapper.mapModelListToResponseList(posts);
    }

    @Override
    public PostResponse createPost(PostRequest request) {
        var post = postMapper.mapRequestToModel(request);
        post.setDateAdded(LocalDateTime.now());
        post.setNumberOfLikes(0);
        return postMapper.mapModelToResponse(postRepository.save(post));
    }
}
