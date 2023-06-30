package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemEntityControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetItems() {
        List<ItemEntity> listItemEntity = new ArrayList<>();
        ItemEntity itemEntity = createItem();
        listItemEntity.add(itemEntity);

        Mockito.when(itemRepository.findAll()).thenReturn(listItemEntity);
        ResponseEntity<List<ItemEntity>> response3 = itemController.getItems();
        Assert.assertEquals(HttpStatus.OK, response3.getStatusCode());
        Assert.assertNotNull(response3);
        List<ItemEntity> listEntity = response3.getBody();
        Assert.assertNotNull(listEntity);
        Assert.assertEquals(1, listEntity.size());
    }

    @Test
    public void testGetItemById() {
        ItemEntity itemEntity = createItem();

        ResponseEntity<ItemEntity> response = itemController.getItemById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));
        ResponseEntity<ItemEntity> response2 = itemController.getItemById(1L);
        Assert.assertNotNull(response2);
        Assert.assertNotNull(response2.getBody());
        Assert.assertEquals(itemEntity.getId(), response2.getBody().getId());
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    @Test
    public void testGetItemsByName() {
        ItemEntity itemEntity = createItem();
        List<ItemEntity> listItemEntity = new ArrayList<>();
        listItemEntity.add(itemEntity);

        ResponseEntity<List<ItemEntity>> response = itemController.getItemsByName("New Item");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.when(itemRepository.findByName("New Item")).thenReturn(listItemEntity);
        ResponseEntity<List<ItemEntity>> response2 = itemController.getItemsByName("New Item");
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        List<ItemEntity> listEntity = response2.getBody();
        Assert.assertNotNull(listEntity);
        Assert.assertEquals(1, listEntity.size());
    }

    public static UserEntity createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("UserName");
        userEntity.setPassword("Password");
        userEntity.setCartEntity(new CartEntity());
        return userEntity;
    }

    public static CartEntity createCart() {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(1L);
        cartEntity.setTotal(null);
        cartEntity.setItemEntities(new ArrayList<>());
        cartEntity.setTotal(BigDecimal.valueOf(13.0));
        return cartEntity;
    }

    public static ItemEntity createItem() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Item");
        itemEntity.setDescription("New item");
        itemEntity.setPrice(BigDecimal.valueOf(111.0));
        return itemEntity;
    }
}