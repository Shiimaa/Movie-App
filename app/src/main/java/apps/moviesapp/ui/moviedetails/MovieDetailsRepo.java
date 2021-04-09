package apps.moviesapp.ui.moviedetails;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import apps.moviesapp.data.Api;
import apps.moviesapp.data.ApiClient;
import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepo {
    private static MovieDetailsRepo instance;
    private static final Object mutex = new Object();

    private final Api apiService = ApiClient.getClient().create(Api.class);

    public MutableLiveData<String> handelError = new MutableLiveData<>();

    private MovieDetailsRepo() {
    }

    public static MovieDetailsRepo getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new MovieDetailsRepo();
                }
            }
        }

        return instance;
    }

    public MutableLiveData<MovieDetailsResponse> getMovieDetails(int movieId) {
        handelError = new MutableLiveData<>();
        MutableLiveData<MovieDetailsResponse> movieDetailsMutableLiveData = new MutableLiveData<>();

        Call<MovieDetailsResponse> call = apiService.getMovieDetails(movieId, Constant.REQUESTS_LANGUAGE);

        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieDetailsResponse> call, @NotNull Response<MovieDetailsResponse> response) {
                if (response.isSuccessful()) {
                    movieDetailsMutableLiveData.setValue(response.body());
                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        handelError.setValue(errorObject.getString("status_message"));
                    } catch (JSONException | IOException ignored) {
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                handelError.setValue(t.getMessage());
            }
        });

        return movieDetailsMutableLiveData;
    }

}
