package io.codegitters.scs_reactive_demo_a.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    @Transactional
    public Mono<Transaction> saveTransaction(Transaction transaction) {
//        return transactionRepository.save(transaction);
        return Mono.error(new RuntimeException("Saving transaction failed"));
    }
}
