package com.enigma.carrent.controller;
import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.CustomerRequest;
import com.enigma.carrent.dto.response.CustomerResponse;
import com.enigma.carrent.entity.Customer;
import com.enigma.carrent.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(Constant.API_CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping
    public CustomerResponse addCustomer(@RequestBody CustomerRequest request) {
        return customerService.addCustomer(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public CustomerResponse findCustomerById(@PathVariable String id) {
        System.out.println(" ==== findCustomerById ====");
        return customerService.findCustomerById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request) {
        System.out.println(" ==== updateCustomer ====");
        return customerService.updateCustomer(id, request);
    }

    // @DeleteMapping("/{id}")
    // public void deleteById(@PathVariable String id) {
    //     customerService.deleteById(id);
    // }
}
