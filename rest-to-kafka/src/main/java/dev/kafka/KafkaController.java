package dev.kafka;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Controller("/api/kafka")
public class KafkaController {
    @Inject
    private final DynamicKafkaProducer kafkaProducer;

    public KafkaController(DynamicKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Post
    public HttpResponse<String> sendMessage(
            @QueryValue String topicName,
            @Body String message
    ) {
        String messageKey = UUID.randomUUID().toString();
        kafkaProducer.sendMessage(topicName, messageKey, message);
        return HttpResponse.ok("✅ Message sent to Kafka topic: " + topicName);
    }

    @Get("/{topicName}/messages")
    public HttpResponse<List<String>> getMessages(@PathVariable String topicName) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "rest-to-kafka-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        List<String> messages = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topicName));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                messages.add(record.value());
            }
        }

        return HttpResponse.ok(messages);
    }
}
