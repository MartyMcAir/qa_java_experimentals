#!/bin/bash
echo "скачать кафку с https://downloads.apache.org/kafka/3.9.0/kafka-3.9.0-src.tgz"
echo "делаем скрипт исполняемым: chmod +x run-kafka-for-test.sh"
echo "запускаем скрипт: ./run-kafka-for-test.sh"
echo "📌 Как остановить Kafka: bin/kafka-server-stop.sh"
sleep 2

KAFKA_DIR="kafka"
KAFKA_CONFIG="config/kraft/server.properties"
KAFKA_CLUSTER_ID=$(bin/kafka-storage.sh random-uuid)
USER_TOPIC="user-topic"

echo "🚀 Инициализируем KRaft Kafka 3.9.0..."
cd "$KAFKA_DIR" || { echo "❌ Ошибка: папка Kafka не найдена!"; exit 1; }

# Форматируем хранилище Kafka для KRaft (ОСТОРОЖНО! Удаляет все данные)
bin/kafka-storage.sh format -t "$KAFKA_CLUSTER_ID" -c "$KAFKA_CONFIG"

echo "🚀 Запускаем Kafka без Zookeeper (KRaft mode)..."
bin/kafka-server-start.sh "$KAFKA_CONFIG" &

# Ждём запуск Kafka
sleep 10

echo "📡 Проверяем доступные топики..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

echo "🎯 Создаём топик '$USER_TOPIC' для работы с User..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic "$USER_TOPIC" --partitions 3 --replication-factor 1

echo "📡 Проверяем созданные топики..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

echo "🎤 Отправляем тестовое сообщение в '$USER_TOPIC'..."
echo '{"id":1,"name":"John Doe","email":"john.doe@example.com"}' | bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic "$USER_TOPIC"

echo "📩 Читаем сообщения из '$USER_TOPIC'..."
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic "$USER_TOPIC" --from-beginning --max-messages 1

echo "✅ Готово! Kafka и топик '$USER_TOPIC' готовы к работе."
