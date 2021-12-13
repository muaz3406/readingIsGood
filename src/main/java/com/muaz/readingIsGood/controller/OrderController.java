package com.muaz.readingIsGood.controller;

import com.muaz.readingIsGood.dto.OrderRequest;
import com.muaz.readingIsGood.entity.Orders;
import com.muaz.readingIsGood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orderService")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/doOrder")
    public ResponseEntity<Orders> doOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.doOrder(orderRequest),HttpStatus.OK);
    }

    @GetMapping("/getAllOrdersByCustomerId/{customerId}")
    public ResponseEntity<List> getAllOrdersByCustomerId(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<List>(orderService.getAllOrdersByCustomerId(customerId),HttpStatus.OK);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("orderId") Long orderId){
        Optional<Orders> orders =orderService.getOrderById(orderId);
        return orders.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
