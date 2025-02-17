package io.codegitters.scs_reactive_demo_a.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }
}
