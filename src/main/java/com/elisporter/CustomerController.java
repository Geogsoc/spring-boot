package com.elisporter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

@GetMapping
public List<Customer> getCustomers(){
    return List.of(new Customer(1,"Jeff", "Casino"),new Customer(2,"Fred", "Betting"));
}

}
