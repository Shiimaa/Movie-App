package apps.moviesapp.data;

import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.domin.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("3/movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("language") String lang, @Query("page") int page);

    @GET("3/search/movie")
    Call<MoviesResponse> getFilteredMovies(@Query("language") String lang, @Query("page") int page, @Query("query") String searchText);

    @GET("3/movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("language") String lang, @Query("page") int page);

    @GET("3/movie/now_playing")
    Call<MoviesResponse> getCurrentPlayingMovies(@Query("language") String lang, @Query("page") int page);

    @GET("3/movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("language") String lang);
}
