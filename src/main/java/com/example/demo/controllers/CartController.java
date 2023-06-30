package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.ModifyCartRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<CartEntity> addTocart(@RequestBody ModifyCartRequest request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            log.error("user not found!");
            log.debug(HttpStatus.NOT_FOUND + "User - NOTFOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<ItemEntity> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            log.error("item not found!");
            log.debug(HttpStatus.NOT_FOUND + "Item - NOTFOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CartEntity cartEntity = userEntity.getCartEntity();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cartEntity.addItem(item.get()));
        cartRepository.save(cartEntity);
        log.debug(HttpStatus.OK + "Add to cart");
        return ResponseEntity.ok(cartEntity);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<CartEntity> removeFromcart(@RequestBody ModifyCartRequest request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            log.error("user not found!");
            log.debug(HttpStatus.NOT_FOUND + "User - NOTFOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<ItemEntity> item = itemRepository.findById(request.getItemId());
        if (!item.isPresent()) {
            log.error("item not found!");
            log.debug(HttpStatus.NOT_FOUND + "Item - NOTFOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CartEntity cartEntity = userEntity.getCartEntity();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cartEntity.removeItem(item.get()));
        cartRepository.save(cartEntity);
        log.debug(HttpStatus.OK + "Removed from cart");
        return ResponseEntity.ok(cartEntity);
    }

}
