package com.example.demo.controllers;

import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserOrderEntity;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrderEntity> submit(@PathVariable String username) {
        log.info("[submit] Executed");
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            log.error("user not found!");
            log.debug(HttpStatus.NOT_FOUND + "User - NOTFOUND");
            return ResponseEntity.notFound().build();
        }
        UserOrderEntity order = UserOrderEntity.createFromCart(userEntity.getCartEntity());
        orderRepository.save(order);
        log.debug(HttpStatus.OK + "Submitted");
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrderEntity>> getOrdersForUser(@PathVariable String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            log.error("user not found!");
            log.debug(HttpStatus.NOT_FOUND + "User - NOTFOUND");
            return ResponseEntity.notFound().build();
        }
        log.debug(HttpStatus.FOUND + "User found");
        return ResponseEntity.ok(orderRepository.findByUser(userEntity));
    }
}
