package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.Statistics;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private OrderService orderService;

    public List<Statistics> getStatisticsByCustomerId(Long customerId) {
        List<Statistics> statisticsList = new ArrayList<>();
        List<Orders> ordersList = orderService.getAllOrdersByCustomerId(customerId);
        Map<Integer, List<Orders>> map = ordersList.stream().collect(Collectors.groupingBy(element
                -> element.getCreatedOn().getMonthValue()));

        for (Map.Entry<Integer, List<Orders>> entry : map.entrySet()) {
            Statistics statistics = new Statistics();
            Integer key = entry.getKey();
            List<Orders> orders = entry.getValue();

            statistics.setCustomerId(customerId);
            statistics.setOrderCount(orders.size());
            statistics.setMonth(Month.of(key));
            statistics.setTotalPurchasedAmount(
                    orders.stream()
                            .map(Orders::getPurchasedAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
            statistics.setBookCount(
                    orders.stream().flatMap(order -> order.getOrderItemList().stream()).map(OrderItem::getQuantity).reduce(0, Integer::sum));

            statisticsList.add(statistics);
        }
        return statisticsList;
    }
}
