package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.OrderItemDto;
import com.muaz.readingIsGood.dto.OrderRequest;
import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.entity.Orders;
import com.muaz.readingIsGood.exception.BadResourceRequestException;
import com.muaz.readingIsGood.exception.NoSuchResourceFoundException;
import com.muaz.readingIsGood.repository.BookRepository;
import com.muaz.readingIsGood.repository.CustomerRepository;
import com.muaz.readingIsGood.repository.OrderItemRepository;
import com.muaz.readingIsGood.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Orders doOrder(OrderRequest orderRequest) {
        log.debug("doOrder orderRequest:{}", orderRequest);

        quantityCheck(orderRequest.getOrderItemDtoList());
        Customer customer = getAndCheckCustomer(orderRequest.getCustomerId());
        List<OrderItem> orderItemList = getAndCheckOrderItemList(orderRequest.getOrderItemDtoList());

        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setOrderItemList(orderItemList);
        orders.setPurchasedAmount(orderItemList.stream().map(OrderItem::getPurchasedAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        return orderRepository.save(orders);
    }

    private List<OrderItem> getAndCheckOrderItemList(List<OrderItemDto> orderItemDtoList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) {
            OrderItem orderItem = new OrderItem();
            bookRepository.findById(orderItemDto.getBookId()).ifPresentOrElse(book -> {
                orderItem.setQuantity(orderItemDto.getQuantity());
                orderItem.setBook(book);
                orderItem.setPurchasedAmount(book.getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity())));
                orderItemList.add(orderItem);
            }, () -> {
                log.error("Book is not found");
                throw new NoSuchResourceFoundException("Book is not found");
            });

        }
        return (List<OrderItem>) orderItemRepository.saveAll(orderItemList);
    }

    private Customer getAndCheckCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            log.error("User Not Found");
            throw new NoSuchResourceFoundException("User Not Found");
        }
    }

    private void quantityCheck(List<OrderItemDto> orderItemDtoList) {
        if (CollectionUtils.isEmpty(orderItemDtoList)) {
            log.error("Cart is empty");
            throw new BadResourceRequestException("Cart is empty");
        }
        boolean hasInvalidQuantity = orderItemDtoList.stream().anyMatch(orderItem -> orderItem.getQuantity() < 1);
        if (hasInvalidQuantity) {
            log.error("Cart has invalid quantity");
            throw new BadResourceRequestException("Cart has invalid quantity");
        }
    }

    public List<Orders> getAllOrdersByCustomerId(Long id) {
        return orderRepository.findByCustomerId(id);
    }

    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
