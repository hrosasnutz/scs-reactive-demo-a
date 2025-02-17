package io.codegitters.scs_reactive_demo_a;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.codegitters.scs_reactive_demo_a.account.Account;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.context.config.JsonMessageConverter;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.MappingJacksonParameterizedConverter;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.support.MessageBuilder;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SpringBootTest
class ScsReactiveDemoAApplicationTests {

	@Test
	void contextLoads() {
		var json = """
				{
				  "number": "1111-2222-3333-4444"
				}
				""";
		var record = new ConsumerRecord<byte[], byte[]>(
				"",
				0,
				0,
				UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8),
				json.getBytes(StandardCharsets.UTF_8)
		);
		
		var message = MessageBuilder.withPayload(json.getBytes(StandardCharsets.UTF_8))
				.build();
		var type = new ParameterizedTypeReference<Account> () {};
		var mapper = JsonMapper.builder()
				.findAndAddModules()
				.build();
		var acknowledgement = new NoOpAcknowledgment();
		var consumer = new MockConsumer<byte[], byte[]>(OffsetResetStrategy.EARLIEST);
		
		var mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
		var mappingJacksonParameterizedConverter = new MappingJacksonParameterizedConverter();
		var jsonMessageConverter = new JsonMessageConverter(new JacksonMapper(mapper));
		System.out.println(json);
		
		System.out.println(mappingJackson2MessageConverter.fromMessage(message, Account.class));
		System.out.println(mappingJacksonParameterizedConverter.fromMessage(message, Account.class));
		System.out.println(jsonMessageConverter.fromMessage(message, Account.class));
		
		var jsonMessagingMessageConverter = new org.springframework.kafka.support.converter.JsonMessageConverter(mapper);
		System.out.println(jsonMessagingMessageConverter.toMessage(record, acknowledgement, consumer, type.getType()));
	}

	public static class NoOpAcknowledgment implements Acknowledgment {
		@Override
		public void acknowledge() {
			
		}
	} 
}
