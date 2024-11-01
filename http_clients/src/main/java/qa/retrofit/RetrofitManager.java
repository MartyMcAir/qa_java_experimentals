package qa.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private final Retrofit retrofit;

    public RetrofitManager(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addConverterFactory(JacksonConverterFactory.create()) // с jackson не работает
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
