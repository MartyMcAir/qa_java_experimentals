package qa.retrofit;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class RetrofitCallExecutor {

    public static <T> T executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                throw new RuntimeException("Unexpected response: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute Retrofit call: " + e.getMessage(), e);
        }
    }
}