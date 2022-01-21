package com.Swaroop.shopping.orderservice.datab;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Swaroop.shopping.orderservice.entity.Address;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByCustomerId(int customerId);
}
