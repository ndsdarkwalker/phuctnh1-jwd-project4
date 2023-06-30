package com.example.demo.controllers;

import com.example.demo.entity.ItemEntity;
import com.example.demo.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<ItemEntity>> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
        return ResponseEntity.of(itemRepository.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ItemEntity>> getItemsByName(@PathVariable String name) {
        List<ItemEntity> itemEntities = itemRepository.findByName(name);
        if (itemEntities == null || itemEntities.isEmpty()) {
            log.error("item not found!");
            log.debug(HttpStatus.NOT_FOUND + "Item - NOTFOUND");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemEntities);

    }

}
