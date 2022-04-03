package com.example.domain_user_service.service;

import com.example.domain_user_service.dto.UserDto;
import com.example.domain_user_service.entity.User;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(UserDto userDto);

    void update(Long id, UserDto userDto);

    void deleteById(Long id);

}
