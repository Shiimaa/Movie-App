package apps.moviesapp.ui.popular;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import apps.moviesapp.data.Api;
import apps.moviesapp.data.ApiClient;
import apps.moviesapp.domin.models.MoviesResponse;
import apps.moviesapp.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepo {
    private static PopularMoviesRepo instance;
    private static final Object mutex = new Object();

    private final Api apiService = ApiClient.getClient().create(Api.class);

    public MutableLiveData<String> handelError = new MutableLiveData<>();

    private PopularMoviesRepo() {
    }

    public static PopularMoviesRepo getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new PopularMoviesRepo();
                }
            }
        }

        return instance;
    }

    public MutableLiveData<MoviesResponse> getPopularMovies(int pageNumber) {
        handelError = new MutableLiveData<>();
        MutableLiveData<MoviesResponse> moviesMutableLiveData = new MutableLiveData<>();

        Call<MoviesResponse> call = apiService.getPopularMovies(Constant.REQUESTS_LANGUAGE, pageNumber);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NotNull Call<MoviesResponse> call, @NotNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesMutableLiveData.setValue(response.body());
                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        handelError.setValue(errorObject.getString("status_message"));
                    } catch (JSONException | IOException ignored) {
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                handelError.setValue(t.getMessage());
            }
        });

        return moviesMutableLiveData;
    }

    public MutableLiveData<MoviesResponse> getFilteredMovies(String searchText,int pageNumber) {
        handelError = new MutableLiveData<>();
        MutableLiveData<MoviesResponse> moviesMutableLiveData = new MutableLiveData<>();

        Call<MoviesResponse> call = apiService.getFilteredMovies(Constant.REQUESTS_LANGUAGE, pageNumber,searchText);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NotNull Call<MoviesResponse> call, @NotNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesMutableLiveData.setValue(response.body());
                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        handelError.setValue(errorObject.getString("status_message"));
                    } catch (JSONException | IOException ignored) {
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                handelError.setValue(t.getMessage());
            }
        });

        return moviesMutableLiveData;
    }

}
