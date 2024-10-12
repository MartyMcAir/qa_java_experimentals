package qa.http_clients.retrofit.services;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CryptoCompareService {

    @SneakyThrows
    @GET("/data/price")
    Call<JsonObject> getCurrentPrice(@Query("fsym") String fromSymbol, @Query("tsyms") String toSymbols);

    @SneakyThrows
    @GET("/data/v2/histoday")
    Call<JsonObject> getHistoricalData(@Query("fsym") String fromSymbol, @Query("tsym") String toSymbol,
                                       @Query("limit") int limit, @Query("toTs") long toTimestamp);

    @Headers("Authorization: Bearer YOUR_API_KEY")
    @POST("/data/create")
    @FormUrlEncoded
    Call<JsonObject> createData(@Field("symbol") String symbol, @Field("price") double price);

    @POST("/data/create")
    Call<JsonObject> createData(@Body JsonObject data);

    @POST("/data/update/{id}")
    Call<JsonObject> updateData(@Header("Authorization") String apiKey, @Path("id") String id, @Body JsonObject data);

    @PUT("/data/update")
    Call<JsonObject> updateData(@Body JsonObject data);

    @DELETE("/data/delete/{id}")
    Call<JsonObject> deleteData(@Path("id") String id);

    @DELETE("/data/delete/{id}")
    Call<JsonObject> deleteData(@Header("Authorization") String apiKey, @Path("id") String id
    );
}
