package com.spring.user.management.dao;

import com.spring.user.management.dto.UserResponseDto;
import com.spring.user.management.model.User;

import java.util.List;

public interface UserDAO {

    List<UserResponseDto> getAll();

    UserResponseDto getOne(int userId);

    UserResponseDto addUser(User user);

    UserResponseDto modifyUser(User user , int userId);

    UserResponseDto deleteUser(int userId);
}
