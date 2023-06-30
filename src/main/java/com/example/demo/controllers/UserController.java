package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> findByUserName(@PathVariable String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            log.error("user not found!");
            return ResponseEntity.notFound().build();
        }
        log.debug(HttpStatus.FOUND + "Found by username");
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserRequest.getUsername());
        userEntity.setPassword(createUserRequest.getPassword());
        CartEntity cartEntity = new CartEntity();
        cartRepository.save(cartEntity);
        userEntity.setCartEntity(cartEntity);
        userRepository.save(userEntity);
        log.debug(HttpStatus.CREATED + "User - Success");
        return ResponseEntity.ok(userEntity);
    }

}
