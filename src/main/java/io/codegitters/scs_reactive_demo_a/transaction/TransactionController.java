package io.codegitters.scs_reactive_demo_a.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionRepository transactionRepository;
    
    @GetMapping
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
