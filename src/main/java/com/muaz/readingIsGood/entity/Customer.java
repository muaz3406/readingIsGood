package com.muaz.readingIsGood.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    private String userName;
    private String password;
}
