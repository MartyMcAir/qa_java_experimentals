#### запустить Spring Boot с подробными логами
mvn spring-boot:run -X

#### Очистить локальный кэш Maven
mvn clean
mvn clean package
mvn dependency:purge-local-repository
mvn install
mvn spring-boot:run

##### Получить список топиков
curl --location 'http://localhost:8080/api/kafka/topics'

##### Получить сообщения из топика "test-topic"
curl --location 'http://localhost:8080/api/kafka/test-topic/messages'

##### Отправить сообщение в топик "test-topic"
curl --location 'http://localhost:8080/api/kafka?topic=test-topic' \
--header 'Content-Type: application/json' \
--data 'Hello Kafka Spring 88'

