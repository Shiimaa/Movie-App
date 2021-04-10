package apps.moviesapp.interfaces;

public interface LocalMoviesUpdated {
    void onMovieSaved();

    void onMovieDeleted();

    void isMovieSaved(boolean isSaved);
}
