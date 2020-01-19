package ir.akhbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsListServer {

    @GET("v2/everything")
    public Call<ServerResponse> getNewsList(@Query("q") String q, @Query("apiKey") String apiKey);

}
