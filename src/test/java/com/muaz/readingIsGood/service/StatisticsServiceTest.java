package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.Statistics;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.entity.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private OrderService orderService;

    @Test
    public void shouldGetStatisticsByCustomerId() {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(3);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(2);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(2);

       List<OrderItem> orderItemList = new ArrayList<>(
               Arrays.asList(orderItem, orderItem1));
        List<OrderItem> orderItemList1 = new ArrayList<>(
                Collections.singletonList(orderItem2));
        Orders orders = new Orders();
        orders.setPurchasedAmount(BigDecimal.valueOf(33));
        orders.setOrderItemList(orderItemList);
        orders.setCreatedOn(LocalDateTime.of(2021,
                Month.JULY, 29, 19, 30, 40));
        Orders orders1 = new Orders();
        orders1.setPurchasedAmount(BigDecimal.valueOf(111));
        orders1.setOrderItemList(orderItemList1);
        orders1.setCreatedOn(LocalDateTime.of(2021,
                Month.DECEMBER, 29, 19, 30, 40));
        List<Orders> ordersList = new ArrayList<>(
                Arrays.asList(orders, orders1));

        when(orderService.getAllOrdersByCustomerId(1l)).thenReturn(ordersList);
        List<Statistics> statisticsList = statisticsService.getStatisticsByCustomerId(1l);
        assertEquals(statisticsList.size(), 2);
        assertEquals(statisticsList.get(0).getTotalPurchasedAmount(), BigDecimal.valueOf(33));
        assertEquals(statisticsList.get(1).getTotalPurchasedAmount(), BigDecimal.valueOf(111));
    }
}
