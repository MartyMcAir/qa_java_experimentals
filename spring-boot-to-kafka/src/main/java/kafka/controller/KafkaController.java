package kafka.controller;

import kafka.service.KafkaAdminService;
import kafka.service.KafkaConsumerService;
import kafka.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private final KafkaProducerService producerService;
    private final KafkaConsumerService consumerService;
    private final KafkaAdminService kafkaAdminService;

    // TODO если моя апишка будет все сообщения забирать всегда.
    //  .тогда нужные сообщения для gas-station-e100 пропадут и все дальше заказы не дойдут до COMPLETED.
    //  .вариант что бы endpoint выдал сообщение из указанного кафка топика, и потом его же назад вернул (c другим id)
    public KafkaController(KafkaProducerService producerService, KafkaConsumerService consumerService, KafkaAdminService kafkaAdminService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
        this.kafkaAdminService = kafkaAdminService;
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> getTopics() {
        return ResponseEntity.ok(kafkaAdminService.getAllTopics());
    }

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