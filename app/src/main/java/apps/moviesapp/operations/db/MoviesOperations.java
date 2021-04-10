package apps.moviesapp.operations.db;

import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.interfaces.LocalMoviesLoaded;
import apps.moviesapp.interfaces.LocalMoviesUpdated;

public class MoviesOperations {
    public static void getAllSavedMovies(LocalMoviesLoaded callback) {
        if (callback == null) throw new RuntimeException("Callback is NULL!");

        callback.onMoviesLoaded(AppDatabase.getInstance().moviesDao().getAllSavedMovies());
    }

    public static void isMovieSaved(int id, LocalMoviesUpdated callback) {
        if (callback == null) throw new RuntimeException("Callback is NULL!");

        MovieDetailsResponse savedMovie = AppDatabase.getInstance().moviesDao().isMovieSaved(id);
        callback.isMovieSaved(savedMovie != null);
    }

    public static void AddMovieToFavourite(MovieDetailsResponse movie, LocalMoviesUpdated callback) {
        AppDatabase.getInstance().moviesDao().AddMovieToFavourite(movie);
        callback.onMovieSaved();
    }

    public static void removeMovieFromFavourite(int movieId, LocalMoviesUpdated callback) {
        AppDatabase.getInstance().moviesDao().deleteSavedMovie(movieId);
        callback.onMovieDeleted();

    }
}
