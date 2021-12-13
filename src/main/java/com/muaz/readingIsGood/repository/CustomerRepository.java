package com.muaz.readingIsGood.repository;

import com.muaz.readingIsGood.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByUserName(String userName);
}
