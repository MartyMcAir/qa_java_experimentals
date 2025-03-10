package dev.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaConsumerService {
    private final Map<String, List<java.lang.Object>> messages = new HashMap<>();

    @KafkaListener(
            topicPattern = ".*",
            groupId = "rest-to-kafka-group",
            properties = "auto.offset.reset=earliest"
    )
    public void listen(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, String message) {
        messages.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(message);
    }

    public List<Object> getMessages(String topic) {
        return new ArrayList<>(messages.getOrDefault(topic, Collections.emptyList()));
    }

}