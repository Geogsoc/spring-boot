package com.elisporter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.elisporter.RegistrationOrigin.*;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

@GetMapping
public List<Customer> getCustomers(){
    return List.of(new Customer(1,"Jeff", CASINO),new Customer(2,"Fred", BET));
}

}
