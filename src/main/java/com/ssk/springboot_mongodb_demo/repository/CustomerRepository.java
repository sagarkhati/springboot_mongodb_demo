package com.ssk.springboot_mongodb_demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssk.springboot_mongodb_demo.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public List<Customer> findByLastName(String lastName);

}