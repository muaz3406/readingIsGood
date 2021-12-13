package com.muaz.readingIsGood.repository;

import com.muaz.readingIsGood.entity.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {
    List<Orders> findByCustomerId(Long customerId);
}
