package qa.ok_http.endpoints;

public enum BinanceEndpoints implements ApiEndpoint {
    TICKER_PRICE("/api/v3/ticker/price"),
    EXCHANGE_INFO("/api/v3/exchangeInfo"),
    KLINE_DATA("/api/v3/klines"),
    ACCOUNT("/api/v3/account"),
    ORDER("/api/v3/order");

    private final String path;

    BinanceEndpoints(String path) {
        this.path = path;
    }

    @Override
    public String getBaseUrl() {
        return "https://api.binance.com";
    }

    @Override
    public String getPath() {
        return path;
    }
}
