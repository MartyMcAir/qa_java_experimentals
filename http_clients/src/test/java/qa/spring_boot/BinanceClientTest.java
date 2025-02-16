package qa.spring_boot;

//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import qa.binance_client.BinanceClient;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import qa.binance_client.BinanceApplication;
import qa.binance_client.BinanceClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BinanceApplication.class)
public class BinanceClientTest {

    @Autowired
    private BinanceClient binanceClient;

    @Test
    @DisplayName("Получение данных по свечам для пары BTCUSDT за определенную дату")
    void getHistoricalKlineDataForDateTest() {
        long startTime = Instant.parse("2023-01-01T00:00:00Z").toEpochMilli();
        long endTime = startTime + ChronoUnit.DAYS.getDuration().toMillis() - 1;

        Mono<String> response = binanceClient.getKlineData("BTCUSDT", "1d", startTime, endTime);

        StepVerifier.create(response)
                .expectNextMatches(json -> json.contains("1672531200000")) // Проверка, что в ответе есть нужное время
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение текущей цены пары BTCUSDT")
    void getCurrentPriceTest() {
        Mono<String> response = binanceClient.getCurrentPrice("BTCUSDT");

        StepVerifier.create(response)
                .expectNextMatches(json -> json.contains("\"symbol\":\"BTCUSDT\"")) // Проверка, что в ответе есть символ
                .verifyComplete();
    }

}

