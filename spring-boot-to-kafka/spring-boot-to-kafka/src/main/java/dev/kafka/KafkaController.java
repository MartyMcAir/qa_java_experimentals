package dev.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;
    private final KafkaAdminService kafkaAdminService;

    public KafkaController(KafkaProducerService producerService, KafkaConsumerService consumerService, KafkaAdminService kafkaAdminService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
        this.kafkaAdminService = kafkaAdminService;
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> getTopics() {
        return ResponseEntity.ok(kafkaAdminService.getAllTopics());
    }

//    @PostMapping
//    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestBody String message) {
//        String key = "key-" + System.currentTimeMillis();
//        producerService.sendMessage(topic, key, message);
//        return ResponseEntity.ok("✅ Message sent to Kafka topic: " + topic);
//    }
//    @GetMapping("/{topic}/messages")
//    public ResponseEntity<List<String>> getMessages(@PathVariable String topic) {
//        List<String> messages = consumerService.getMessages();
//        return ResponseEntity.ok(messages);
//    }
//    @GetMapping("/{topic}/messages")
//    public ResponseEntity<List<String>> getMessages(@PathVariable("topic") String topic) {  // ✅ Исправленный параметр
//        List<String> messages = consumerService.getMessages();
//        return ResponseEntity.ok(messages);
//    }
    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam("topic") String topic, @RequestBody String message) {
        String key = "key-" + System.currentTimeMillis();
        producerService.sendMessage(topic, key, message);
        return ResponseEntity.ok("✅ Message sent to Kafka topic: " + topic);
    }

    @GetMapping("/{topic}/messages")
    public ResponseEntity<List<Object>> getMessages(@PathVariable("topic") String topic) {
        return ResponseEntity.ok(consumerService.getMessages(topic));
    }

}