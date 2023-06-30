package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Entity
@Table(name = "user_order")
@Data
public class UserOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonProperty
    @Column
    private List<ItemEntity> itemEntities;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonProperty
    private UserEntity user;

    @JsonProperty
    @Column
    private BigDecimal total;

    public static UserOrderEntity createFromCart(CartEntity cartEntity) {
        UserOrderEntity order = new UserOrderEntity();
        order.setItemEntities(new ArrayList<>(cartEntity.getItemEntities()));
        order.setTotal(cartEntity.getTotal());
        order.setUser(cartEntity.getUser());
        return order;
    }

}
