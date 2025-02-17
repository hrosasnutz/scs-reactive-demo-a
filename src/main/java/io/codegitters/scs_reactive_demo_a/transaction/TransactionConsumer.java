package io.codegitters.scs_reactive_demo_a.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.springframework.kafka.support.KafkaHeaders.ACKNOWLEDGMENT;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TransactionConsumer {
    
    private final TransactionService transactionService;
    
    @Bean
    public Function<Flux<Message<Transaction>>, Mono<Void>> onTransactionConsumed() {
        return transaction -> transaction
                .doOnNext(msg -> log.debug("process message {}", msg))
                .flatMap(msg -> Mono.just(msg)
                        .map(Message::getPayload)
                        .flatMap(transactionService::saveTransaction)
                        .doOnNext(trx -> log.debug("Saved transaction: {}", trx))
                        .thenReturn(msg))
                .flatMap(msg -> Mono.justOrEmpty(msg.getHeaders()
                        .get(ACKNOWLEDGMENT, ReceiverOffset.class))
                        .flatMap(ReceiverOffset::commit)
                        .thenReturn(msg))
                .doOnError(e -> log.error("Error saving transaction", e))
//                .retryWhen(Retry.backoff(3L, Duration.ofSeconds(10L)))
                .then();
    }
    
    @Bean
    public Function<Flux<Message<?>>, Mono<Void>> errorOnTransactionConsumed() {
        return errors -> errors
                .doOnNext(error -> log.debug("On Error Transaction Consumed: {}", error))
                .flatMap(error -> Mono.justOrEmpty(error.getHeaders().get(ACKNOWLEDGMENT, ReceiverOffset.class))
                        .flatMap(ReceiverOffset::commit)
                        .thenReturn(error))
                .then();
    };
    
    @Bean
    public Consumer<ErrorMessage> onErrorTransactionConsumed() {
        return error -> {
          log.error("On Error Transaction Consumed: {}", error);  
        };
    }
}
