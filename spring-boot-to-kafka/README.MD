#### запустить Spring Boot с подробными логами
mvn spring-boot:run -X

#### Очистить локальный кэш Maven
mvn clean
mvn clean package
mvn dependency:purge-local-repository
mvn install
mvn spring-boot:run

### Для тестирования запросов
1. скачать kafka_2.12-3.9.0.tgz (https://downloads.apache.org/kafka/ ..)
2. распаковать
3. делаем скрипт исполняемым: chmod +x run-kafka-3.9.sh
4. запускаем скрипт: ./run-kafka-3.9.sh

##### Получить список топиков
curl --location 'http://localhost:8080/api/kafka/topics'

#### Получить сообщения из топика "test-topic"
curl --location 'http://localhost:8080/api/kafka/test-topic/messages'

#### Отправить сообщение в топик "test-topic"
curl --location 'http://localhost:8080/api/kafka?topic=test-topic' \
--header 'Content-Type: application/json' \
--data 'Hello Kafka Spring 88'

#### Offset Explorer настройки
- bootstrap server localhost:9092
- kafka klaster v: 2.5
- security: Plaintext
- SASL Mechanism: NONE
- JAAS Config: empty