package com.muaz.readingIsGood.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {

    @OneToOne
    private Customer customer;

    @OneToMany
    private List<OrderItem> orderItemList;

    private BigDecimal purchasedAmount;
}
