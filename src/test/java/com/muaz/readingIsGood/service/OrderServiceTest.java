package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.OrderItemDto;
import com.muaz.readingIsGood.dto.OrderRequest;
import com.muaz.readingIsGood.entity.Book;
import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.repository.BookRepository;
import com.muaz.readingIsGood.repository.CustomerRepository;
import com.muaz.readingIsGood.repository.OrderItemRepository;
import com.muaz.readingIsGood.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    public void shouldDoOrder() {
        Book book = new Book();
        book.setId(1l);
        book.setTotalQuantity(10);
        book.setPrice(BigDecimal.TEN);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setQuantity(3);
        orderItemDto.setBookId(111l);

        Customer customer = new Customer();
        customer.setId(123l);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>(
                Collections.singletonList(orderItemDto));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(123l);
        orderRequest.setOrderItemDtoList(orderItemDtoList);

        when(bookRepository.findById(111l)).thenReturn(java.util.Optional.of(book));
        when(customerRepository.findById(123l)).thenReturn(java.util.Optional.of(customer));

        orderService.doOrder(orderRequest);
    }

}
