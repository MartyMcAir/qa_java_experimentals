package kafka.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaAdminService {

    private final AdminClient adminClient;

    public KafkaAdminService() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        this.adminClient = AdminClient.create(config);
    }

    public List<String> getAllTopics() {
        try {
            ListTopicsResult topicsResult = adminClient.listTopics();
            return new ArrayList<>(topicsResult.names().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("⚠ Ошибка при получении списка топиков", e);
        }
    }

}