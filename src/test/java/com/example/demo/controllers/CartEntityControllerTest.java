package com.example.demo.controllers;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.ModifyCartRequest;
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

public class CartEntityControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTocart() {
        UserEntity userEntity = createUser();
        CartEntity cartEntity = userEntity.getCartEntity();
        ItemEntity itemEntity = createItem();
        cartEntity.setId(1L);
        cartEntity.addItem(itemEntity);
        cartEntity.setUser(userEntity);
        userEntity.setCartEntity(cartEntity);
        cartEntity.setTotal(BigDecimal.valueOf(001.0));

        Mockito.when(itemRepository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));
        Mockito.when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(userEntity);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("username");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<CartEntity> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        CartEntity cartEntity1 = response.getBody();
        Assert.assertNotNull(cartEntity1);
        Assert.assertNotNull(cartEntity1.getItemEntities());
        Assert.assertNotNull(cartEntity1.getUser());
        Assert.assertEquals(userEntity.getUsername(), cartEntity1.getUser().getUsername());
        Assert.assertEquals(userEntity.getPassword(), cartEntity1.getUser().getPassword());

        ModifyCartRequest request2 = new ModifyCartRequest();
        request2.setUsername("another2");
        request2.setItemId(2L);
        request2.setQuantity(2);
        ResponseEntity<CartEntity> response2 = cartController.addTocart(request2);
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void testRemoveFromcart() {
        UserEntity userEntity = createUser();
        CartEntity cartEntity = userEntity.getCartEntity();
        ItemEntity itemEntity = createItem();
        cartEntity.setUser(userEntity);
        cartEntity.addItem(itemEntity);
        cartEntity.setId(1L);
        cartEntity.setTotal(BigDecimal.valueOf(41.0));
        userEntity.setCartEntity(cartEntity);

        Mockito.when(itemRepository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));
        Mockito.when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(userEntity);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("username");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<CartEntity> response = cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        CartEntity entity = response.getBody();
        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.getItemEntities());
        Assert.assertNotNull(entity.getUser());
        Assert.assertEquals(userEntity.getUsername(), entity.getUser().getUsername());

        ModifyCartRequest request2 = new ModifyCartRequest();
        request2.setUsername("UserName");
        request2.setItemId(1L);
        request2.setQuantity(2);

        ResponseEntity<CartEntity> response2 = cartController.removeFromcart(request2);
        Assert.assertNotNull(response2);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    public static UserEntity createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("username");
        userEntity.setPassword("Password");
        userEntity.setCartEntity(new CartEntity());
        return userEntity;
    }

    public static ItemEntity createItem() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Name1");
        itemEntity.setDescription("description");
        itemEntity.setPrice(BigDecimal.valueOf(131.0));
        return itemEntity;
    }

    public static CartEntity createCart() {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(1L);
        cartEntity.setTotal(null);
        cartEntity.setItemEntities(new ArrayList<>());
        cartEntity.setTotal(BigDecimal.valueOf(001.0));
        return cartEntity;
    }
}