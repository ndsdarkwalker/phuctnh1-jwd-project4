package com.example.demo.repositories;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CartEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUser(UserEntity userEntity);
}
