package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.OrderRequest;
import com.muaz.readingIsGood.entity.Book;
import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.entity.OrderItem;
import com.muaz.readingIsGood.repository.BookRepository;
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
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    public void shouldDoOrder() {
        Book book = new Book();
        book.setId(1l);
        book.setTotalQuantity(10);
        book.setPrice(BigDecimal.TEN);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(3);
        orderItem.setBook(book);
        orderItem.setPurchasedAmount(BigDecimal.valueOf(20));

        List<OrderItem> orderItemList = new ArrayList<>(
                Collections.singletonList(orderItem));

        Customer customer = new Customer();
        customer.setUserName("muaz");
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomer(customer);
        orderRequest.setOrderItemList(orderItemList);

        when(bookRepository.findById(1l)).thenReturn(java.util.Optional.of(book));

        orderService.doOrder(orderRequest);

        verify(orderItemRepository).save(orderItem);
        verify(bookRepository).save(book);
    }

}
