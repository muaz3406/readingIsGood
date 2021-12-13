package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.OrderRequest;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.entity.Orders;
import com.muaz.readingIsGood.repository.BookRepository;
import com.muaz.readingIsGood.repository.OrderItemRepository;
import com.muaz.readingIsGood.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    static Logger log = Logger.getLogger(OrderService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Orders doOrder(OrderRequest orderRequest) {
        for (OrderItem orderItem:orderRequest.getOrderItemList()) {
            BigDecimal purchasedAmount = BigDecimal.ZERO;
            bookRepository.findById(orderItem.getBook().getId()).ifPresent(book -> {
                if (orderItem.getQuantity()<=book.getTotalQuantity()) {
                    orderItem.setPurchasedAmount(purchasedAmount.add(book.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))));
                    book.setTotalQuantity(book.getTotalQuantity()-orderItem.getQuantity());
                    orderItemRepository.save(orderItem);
                    bookRepository.save(book);
                }
            });
        }
        Orders orders = new Orders();
        orders.setCustomer(orderRequest.getCustomer());
        orders.setOrderItemList(orderRequest.getOrderItemList());
        orders.setPurchasedAmount(orderRequest.getOrderItemList().stream().map(OrderItem::getPurchasedAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        return orderRepository.save(orders);
    }

    public List<Orders> getAllOrdersByCustomerId(Long id) {
        return orderRepository.findByCustomerId(id);
    }

    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
