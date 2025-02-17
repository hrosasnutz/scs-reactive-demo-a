package io.codegitters.scs_reactive_demo_a.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

import java.lang.reflect.Type;
import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AccountConsumer {
    
    private final AccountService accountService;
    
    @Bean
    public Function<Flux<ReceiverRecord<byte[], Account>>, Mono<Void>> onAccountConsumed() {
        return records -> records
                .doOnNext(record -> log.debug("Consumed new account message: {}", record))
                .flatMap(record -> accountService.save(record.value())
                        .doOnNext(a -> log.debug("Saved new account: {}", a))
                        .then(record.receiverOffset().commit())
                        .thenReturn(record))
                .then();        
    }
    
    @Bean
    public RecordMessageConverter onAccountConsumedRecordMessageConverter() {
        var converter = new JsonMessageConverter();
        return new RecordMessageConverter() {
            
            @Override
            public Message<?> toMessage(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer, Type payloadType) {
                var accountType = new ParameterizedTypeReference<Account>(){}.getType();
                var message = converter.toMessage(record, acknowledgment, consumer, accountType);
                var record1000 = new ConsumerRecord<>(
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.timestamp(),
                        record.timestampType(),
                        record.serializedKeySize(),
                        record.serializedValueSize(),
                        record.key(),
                        message.getPayload(),
                        record.headers(),
                        record.leaderEpoch()
                );
                if (record instanceof ReceiverRecord receiver) {
                    var receiver1000 = new ReceiverRecord<>(record1000, receiver.receiverOffset());
                    return MessageBuilder.createMessage(
                            receiver1000, 
                            message.getHeaders());
                } else {
                    return MessageBuilder.createMessage(
                            record1000,
                            message.getHeaders());
                }
            }

            @Override
            public ProducerRecord<?, ?> fromMessage(Message<?> message, String defaultTopic) {
                return converter.fromMessage(message, defaultTopic);
            }
        };
    }
}
