package com.enigma.carrent.service;

import java.util.List;

import com.enigma.carrent.dto.request.CustomerRequest;
import com.enigma.carrent.dto.request.CustomerRequestRegister;
import com.enigma.carrent.dto.response.CustomerResponse;
import com.enigma.carrent.entity.Customer;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest request);
    List<Customer> findAll();
    Customer findById(String id);
    CustomerResponse findCustomerById(String id);
    void deleteById(String id);
    CustomerResponse updateCustomer(String id, CustomerRequest request);
    void createCustomer(CustomerRequestRegister customerRequestRegister);
}
