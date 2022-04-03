package com.example.domain_user_service.controller;

import com.example.domain_user_service.dto.UserDto;
import com.example.domain_user_service.dto.validations.MandatoryFieldsValidation;
import com.example.domain_user_service.mapper.UserMapper;
import com.example.domain_user_service.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        var userList = userService.findAll().stream()
            .map(UserMapper.INSTANCE::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(
        @PathVariable @Min(value = 1, message = "invalid Id") Long id) {
        var user = UserMapper.INSTANCE.toDto(userService.findById(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> save(
        @RequestBody @Validated(MandatoryFieldsValidation.class) UserDto userDto) {
        var user = userService.save(userDto);
        var location = UriComponentsBuilder
            .fromPath("http://localhost:9090/api/users")
            .path("/v1/users/{id}")
            .buildAndExpand(user.getId())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable @Min(value = 1, message = "invalid Id") Long id,
        @RequestBody @Validated(MandatoryFieldsValidation.class) UserDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Min(value = 1, message = "invalid Id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
