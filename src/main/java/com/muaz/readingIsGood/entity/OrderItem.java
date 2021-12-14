package com.muaz.readingIsGood.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orderItem")
public class OrderItem extends BaseEntity {

    @OneToOne
    private Book book;
    private int quantity;
    private BigDecimal purchasedAmount;

}
