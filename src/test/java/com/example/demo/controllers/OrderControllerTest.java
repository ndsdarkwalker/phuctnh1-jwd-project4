package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserOrderEntity;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
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

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmit() {
        UserEntity userEntity = createUser();
        CartEntity cartEntity = userEntity.getCartEntity();
        ItemEntity itemEntity = createItem();
        cartEntity.setId(1L);
        cartEntity.setTotal(BigDecimal.valueOf(11.0));
        cartEntity.setUser(userEntity);
        cartEntity.addItem(itemEntity);
        userEntity.setCartEntity(cartEntity);

        ResponseEntity<UserOrderEntity> response = orderController.submit("username");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.when(userRepository.findByUsername("username")).thenReturn(userEntity);
        ResponseEntity<UserOrderEntity> response2 = orderController.submit("username");
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        UserOrderEntity userOrderEntity = response2.getBody();
        Assert.assertNotNull(userOrderEntity);
        Assert.assertNotNull(userOrderEntity.getUser());
        Assert.assertNotNull(userOrderEntity.getItemEntities());
        Assert.assertNotNull(userOrderEntity.getTotal());
    }

    @Test
    public void testGetOrdersForUser() {
        UserEntity userEntity = createUser();
        ItemEntity itemEntity = createItem();
        CartEntity cartEntity = userEntity.getCartEntity();
        cartEntity.setId(1L);
        cartEntity.setTotal(BigDecimal.valueOf(13.0));
        cartEntity.addItem(itemEntity);
        cartEntity.setUser(userEntity);
        userEntity.setCartEntity(cartEntity);

        ResponseEntity<List<UserOrderEntity>> response = orderController.getOrdersForUser("username");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.when(userRepository.findByUsername("username")).thenReturn(userEntity);
        ResponseEntity<List<UserOrderEntity>> response2 = orderController.getOrdersForUser("username");
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        List<UserOrderEntity> list = response2.getBody();
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
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