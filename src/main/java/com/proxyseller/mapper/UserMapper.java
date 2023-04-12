package com.proxyseller.mapper;

import com.proxyseller.dto.UserRequest;
import com.proxyseller.dto.UserResponse;
import com.proxyseller.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserResponse> mapModelListToResponseList(List<User> users);

    UserResponse mapModelToResponse(User user);

    @Mapping(target = "password", ignore = true)
    User mapRequestToModel(UserRequest request);

    @Mapping(target = "password", ignore = true)
    void updateUserWithDto(User user, @MappingTarget UserRequest userRequest);
}
