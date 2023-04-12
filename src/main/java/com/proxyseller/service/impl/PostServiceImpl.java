package com.proxyseller.service.impl;

import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;
import com.proxyseller.enums.Action;
import com.proxyseller.exception.PostNotFoundException;
import com.proxyseller.mapper.PostMapper;
import com.proxyseller.model.Post;
import com.proxyseller.repository.PostRepository;
import com.proxyseller.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static com.proxyseller.enums.Action.ADD;

@Slf4j
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
    public Post getPost(String id) {
        return postRepository.findById(id).orElseThrow(() -> {
            log.error("No post found. [id: {}]", id);
            throw new PostNotFoundException("Post not found");
        });
    }

    @Override
    public PostResponse createPost(PostRequest request) {
        var post = postMapper.mapRequestToModel(request);
        post.setDateAdded(LocalDateTime.now());
        post.setNumberOfLikes(0);
        log.info("Post created successfully");
        return postMapper.mapModelToResponse(postRepository.save(post));
    }

    @Override
    public PostResponse editPost(String id, PostRequest request) {
        var post = getPost(id);
        post.setText(request.text());
        return postMapper.mapModelToResponse(postRepository.save(post));
    }

    @Override
    public void manageLikes(String id, String action) {
        var post = getPost(id);
        var actionEnum = Action.valueOf(action.toUpperCase());
        if (actionEnum.equals(ADD)) {
            manageLikes(post, p -> p.getNumberOfLikes() + 1);
            return;
        }
        manageLikes(post, p -> p.getNumberOfLikes() - 1);
    }

    private void manageLikes(Post post, Function<Post, Integer> operation) {
        post.setNumberOfLikes(operation.apply(post));
        postRepository.save(post);
    }
}
