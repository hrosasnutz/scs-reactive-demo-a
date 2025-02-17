package io.codegitters.scs_reactive_demo_a.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountRepository accountRepository;
    
    @GetMapping
    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
