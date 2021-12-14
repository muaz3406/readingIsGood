package com.muaz.readingIsGood.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemDto> orderItemDtoList;
    private Long customerId;
}
