package com.muaz.readingIsGood.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "book")
public class Book extends BaseEntity {

    private String name;
    private BigDecimal price;
    private int totalQuantity;
}
