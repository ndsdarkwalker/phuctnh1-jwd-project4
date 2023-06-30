package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Entity
@Table(name = "cart")
@Data
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany
    @JsonProperty
    @Column
    private List<ItemEntity> itemEntities;

    @OneToOne(mappedBy = "cartEntity")
    @JsonProperty
    private UserEntity user;

    @Column
    @JsonProperty
    private BigDecimal total;

    public void addItem(ItemEntity itemEntity) {
        if (itemEntities == null) {
            itemEntities = new ArrayList<>();
        }
        itemEntities.add(itemEntity);
        if (total == null) {
            total = new BigDecimal(0);
        }
        total = total.add(itemEntity.getPrice());
    }

    public void removeItem(ItemEntity itemEntity) {
        if (itemEntities == null) {
            itemEntities = new ArrayList<>();
        }
        itemEntities.remove(itemEntity);
        if (total == null) {
            total = new BigDecimal(0);
        }
        total = total.subtract(itemEntity.getPrice());
    }
}
