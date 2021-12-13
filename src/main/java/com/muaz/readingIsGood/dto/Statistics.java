package com.muaz.readingIsGood.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Statistics {

    private Long customerId;
    private java.time.Month Month;
    private int orderCount;
    private int bookCount;
    private BigDecimal totalPurchasedAmount;
}
