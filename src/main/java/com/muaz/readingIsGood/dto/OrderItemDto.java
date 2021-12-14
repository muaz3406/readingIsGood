package com.muaz.readingIsGood.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long bookId;
    private int quantity;
}
