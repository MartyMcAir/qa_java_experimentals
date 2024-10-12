package qa.endpoints;

public enum CoinGeckoEndpoints {
    BITCOIN_MARKET("/coins/markets"),
    SUPPORTED_CURRENCIES("/simple/supported_vs_currencies"),
    COIN_DATA("/coins/{id}"),
    COIN_HISTORY("/coins/{id}/history"),
    ALL_COINS("/coins/list");

    private final String path;

    CoinGeckoEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
