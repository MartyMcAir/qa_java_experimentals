package qa.http_clients.rest_assured;

import qa.http_clients.ok_http.endpoints.ApiEndpoint;

public enum SpaceXEndpoints implements ApiEndpoint {
    LAUNCHES("/launches"),
    LAUNCHES_UPCOMING("/launches/upcoming"),
    LAUNCHES_LATEST("/launches/latest"),
    ROCKETS_ID("/rockets/{id}");

    private final String path;

    SpaceXEndpoints(String path) {
        this.path = path;
    }

    @Override
    public String getBaseUrl() {
        return "https://api.spacexdata.com/v4";
    }

    @Override
    public String getPath() {
        return path;
    }
}
