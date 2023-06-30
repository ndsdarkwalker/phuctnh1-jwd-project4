package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserOrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<UserOrderEntity, Long> {
	List<UserOrderEntity> findByUser(UserEntity user);
}
