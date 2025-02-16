package dev.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface DynamicKafkaProducer {

    /**
     * Отправка сообщения в динамический Kafka-топик
     * @param topic - Название топика
     * @param key - Уникальный ключ сообщения
     * @param message - JSON-строка сообщения
     */
    void sendMessage(@Topic String topic, String key, String message);
}
