package io.codegitters.scs_reactive_demo_a.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomerConsumer { 
    
    private final CustomerService customerService;
    
    @Bean
    public Function<Flux<Message<Flux<ConsumerRecord<?, Customer>>>>, Mono<Void>> onCustomerConsumed() {
        return messages -> messages
                .doOnNext(message -> log.debug("onCustomerConsumed: {}", message))
                .flatMap(message -> message.getPayload()
                        .doOnNext(record -> log.debug("Consumed new customer message: {}", record))
                        .flatMap(record -> customerService.save(record.value()))
                        .doOnNext(c -> log.debug("Saved new customer: {}", c))
                        .thenMany(Flux.just(message)))
                .then();
    }
}
