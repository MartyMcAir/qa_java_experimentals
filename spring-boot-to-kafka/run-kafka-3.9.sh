#!/bin/bash
echo "делаем скрипт исполняемым: chmod +x run-kafka-3.9.sh"
echo "запускаем скрипт: ./run-kafka-3.9.sh"
echo "📌 Проверь, что Kafka запущена: kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list"
echo "📌 Как остановить Kafka: kafka/bin/kafka-server-stop.sh"
sleep 2

KAFKA_DIR="kafka"
KAFKA_CONFIG="config/kraft/server.properties"
KAFKA_CLUSTER_ID=$(bin/kafka-storage.sh random-uuid)
TOPIC_NAME="test-topic"
USER_TOPIC="user-topic"

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🚀 Инициализируем KRaft Kafka 3.9.0..."
cd "$KAFKA_DIR" || { echo "❌ Ошибка: папка Kafka не найдена!"; exit 1; }

# Форматируем хранилище Kafka для KRaft
# Убери эту строку, если ты не хочешь терять Kafka данные при каждом запуске
bin/kafka-storage.sh format -t "$KAFKA_CLUSTER_ID" -c "$KAFKA_CONFIG"

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🚀 Запускаем Kafka без Zookeeper (KRaft mode)..."
bin/kafka-server-start.sh "$KAFKA_CONFIG" &

# Ждём запуск Kafka
sleep 10

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "📡 Проверяем доступные топики..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🎯 Создаём топик '$TOPIC_NAME'..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic "$TOPIC_NAME" --partitions 1 --replication-factor 1

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🎯 Создаём топик '$USER_TOPIC' для работы с User..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic "$USER_TOPIC" --partitions 3 --replication-factor 1

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "📡 Проверяем созданные топики..."
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🎤 Отправляем тестовое сообщение в '$TOPIC_NAME'..."
echo "Hello, Kafka 3.9!" | bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic "$TOPIC_NAME"

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "🎤 Отправляем тестовое сообщение в '$USER_TOPIC'..."
echo '{"id":1,"name":"John Doe","email":"john.doe@example.com"}' | bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic "$USER_TOPIC"

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "📩 Читаем сообщения из '$TOPIC_NAME'..."
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic "$TOPIC_NAME" --from-beginning --max-messages 1

echo "&----------------------------------&----------------------------------&----------------------------------&"
echo "✅ Готово! Kafka работает."
