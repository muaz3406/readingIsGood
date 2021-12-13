package com.muaz.readingIsGood.dto;

import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItem> orderItemList;
    private Customer customer;
}
