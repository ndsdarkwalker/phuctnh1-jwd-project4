package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.CreateUserRequest;
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
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class UserEntityControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepo = mock(UserRepository.class);

    @Mock
    private CartRepository cartRepo = mock(CartRepository.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = createUser();
        ResponseEntity<UserEntity> response = userController.findById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
        ResponseEntity<UserEntity> response2 = userController.findById(1L);
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        Assert.assertEquals(userEntity, response2.getBody());
    }

    @Test
    public void testFindByUserName() {
        UserEntity userEntity = createUser();
        ResponseEntity<UserEntity> response = userController.findByUserName("UserName");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Mockito.when(userRepo.findByUsername(userEntity.getUsername())).thenReturn(userEntity);
        ResponseEntity<UserEntity> response2 = userController.findByUserName("UserName");
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        UserEntity entity = response2.getBody();
        Assert.assertEquals(userEntity.getId(), entity.getId());
        Assert.assertEquals(userEntity.getUsername(), entity.getUsername());
        Assert.assertEquals(userEntity.getCartEntity(), entity.getCartEntity());
    }

    @Test
    public void testCreateUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("UserName");
        createUserRequest.setPassword("Password");
        createUserRequest.setConfirmPassword("Password");

        ResponseEntity<UserEntity> responseEntity = userController.createUser(createUserRequest);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserEntity userEntity = responseEntity.getBody();
        Assert.assertNotNull(userEntity);
        Assert.assertEquals(createUserRequest.getUsername(), userEntity.getUsername());
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