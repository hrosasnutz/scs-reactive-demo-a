package io.codegitters.scs_reactive_demo_a.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    
    private final CustomerRepository customerRepository;
    
    @GetMapping
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
