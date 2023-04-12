package com.proxyseller.mapper;

import com.proxyseller.dto.PostRequest;
import com.proxyseller.dto.PostResponse;
import com.proxyseller.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    List<PostResponse> mapModelListToResponseList(List<Post> posts);

    PostResponse mapModelToResponse(Post post);

    Post mapRequestToModel(PostRequest request);
}
