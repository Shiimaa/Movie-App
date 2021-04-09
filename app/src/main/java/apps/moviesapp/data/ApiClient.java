package apps.moviesapp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apps.moviesapp.utils.Constant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;
    private static final String AUTHORIZATION_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhOTlhYWNkMjk4ZGZjNTllZGFiZDI5ODI0MmJjZThmMiIsInN1YiI6IjYwNzAyYTUxOTI0Y2U1MDAyOWIzNjI0OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cMi-RBvqoL7k7Ll9BgS5ahh60_yJ3RDw2hL8q_9-Vro";

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        final String newToken = "Bearer " + AUTHORIZATION_TOKEN;
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Authorization", newToken)
                    .method(original.method(), original.body());
            return chain.proceed(builder.build());
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(httpClient.addInterceptor(interceptor).build())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}
