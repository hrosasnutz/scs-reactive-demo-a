package io.codegitters.scs_reactive_demo_a.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.reactorkafka.ReceiverOptionsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.util.Objects;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@Configuration
@EnableR2dbcAuditing
public class ScsReactiveDemoAConfig { 
  
    @Bean
    @Order(HIGHEST_PRECEDENCE)
    public ReceiverOptionsCustomizer<byte[], byte[]> globalReceiverOptionsCustomizer() {
        return (binding, receiver) -> {
            log.debug("Customizing binding: {}", binding);
            log.debug("Customizing receiver: {}", receiver);
            return receiver;
        };
    }
    
    @Bean
    @Order(1)
    public ReceiverOptionsCustomizer<byte[], byte[]> customerReceiverOptionsCustomizer() {
        return (binding, receiver) ->  {
            if (Objects.equals("onCustomerConsumed-in-0", binding)) {
                log.debug("Customizing customer binding module.");
            }
            return receiver;
        };
    }
}
