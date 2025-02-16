package dev.kafka;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.UUID;

@Controller("/api/kafka")
public class KafkaController {
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
}
