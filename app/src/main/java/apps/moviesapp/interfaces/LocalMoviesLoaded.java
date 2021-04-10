package apps.moviesapp.interfaces;

import java.util.List;

import apps.moviesapp.domin.models.MovieDetailsResponse;

public interface LocalMoviesLoaded {
    void onMoviesLoaded(List<MovieDetailsResponse> savedMovies);
}
