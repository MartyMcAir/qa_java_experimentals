package qa.binance_client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BinanceClient {
    private final WebClient webClient;

    public BinanceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.binance.com").build();
    }

    public Mono<String> getKlineData(String symbol, String interval, Long startTime, Long endTime) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("startTime", startTime)
                        .queryParam("endTime", endTime)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getCurrentPrice(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/ticker/price")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getExchangeInfo() {
        return webClient.get()
                .uri("/api/v3/exchangeInfo")
                .retrieve()
                .bodyToMono(String.class);
    }
}

