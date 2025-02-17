package io.codegitters.scs_reactive_demo_a.util;

import io.codegitters.scs_reactive_demo_a.customer.Customer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomerDeserializer extends JsonDeserializer<Customer> {
}
