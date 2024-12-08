package com.enigma.carrent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.carrent.dto.request.CustomerRequest;
import com.enigma.carrent.dto.request.CustomerRequestRegister;
import com.enigma.carrent.dto.response.CustomerResponse;
import com.enigma.carrent.entity.Customer;
import com.enigma.carrent.repository.CustomerRepository;
import com.enigma.carrent.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getNik()).orElse(null);
        if (customer != null) {
            throw new IllegalArgumentException("Customer with NIK " + request.getNik() + " already exists.");
        }
        Customer newCustomer = Customer.builder()
                .nik(request.getNik())
                .name(request.getName())
                .driving_license(request.getDriving_license())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        customerRepository.saveAndFlush(newCustomer);
        return toResponse(newCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(String id) {
        Customer customer = customerRepository.findByNik(id).orElse(null);
        return customer;
    }

    @Override
    public CustomerResponse findCustomerById(String id) {
        System.out.println("x".repeat(50));
        Customer customer = customerRepository.findByNik(id).orElse(null);
        return toResponse(customer);
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerResponse updateCustomer(String id, CustomerRequest request) {
        System.out.println("@".repeat(50));
        Customer customer = customerRepository.findByNik(id).orElse(null);
        if (!customer.getNik().equals(request.getNik())) {
            throw new IllegalArgumentException("Cannot Change NIK");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer with NIK " + id + " not found.");
        }
        if (customer != null) {
            customer.setNik(request.getNik());
            customer.setName(request.getName());
            customer.setDriving_license(request.getDriving_license());
            customer.setPhone(request.getPhone());
            customer.setAddress(request.getAddress());
            customerRepository.saveAndFlush(customer);
            return toResponse(customer);
        }
        throw new IllegalArgumentException("Customer with NIK " + id + " not found.");
    }

    public void createCustomer(CustomerRequestRegister customerRequestRegister) {
        Customer customer = customerRepository.findById(customerRequestRegister.getNik()).orElse(null);
        if (customer != null) {
            throw new IllegalArgumentException("Customer with NIK " + customerRequestRegister.getNik() + " already exists.");
        }
        Customer newCustomer = Customer.builder()
                .nik(customerRequestRegister.getNik())
                .name(customerRequestRegister.getName())
                .driving_license(customerRequestRegister.getDriving_license())
                .phone(customerRequestRegister.getPhone())
                .address(customerRequestRegister.getAddress())
                .userAccount(customerRequestRegister.getUserAccount())
                .build();
        customerRepository.saveAndFlush(newCustomer);
    }

    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .nik(customer.getNik())
                .name(customer.getName())
                .driving_license(customer.getDriving_license())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }
}
