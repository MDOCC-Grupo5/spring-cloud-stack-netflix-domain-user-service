package com.example.domain_user_service.service.impl;

import com.example.domain_user_service.dto.UserDto;
import com.example.domain_user_service.entity.User;
import com.example.domain_user_service.exception.ObjectNotFoundException;
import com.example.domain_user_service.repository.UserRepository;
import com.example.domain_user_service.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("User with id '" + id + "', not found"));
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        var user = User.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getEmail())
            .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, UserDto userDto) {
        var userDb = findById(id).toBuilder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getEmail())
            .build();
        userRepository.save(userDb);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var userDb = findById(id);
        userRepository.delete(userDb);
    }

}
