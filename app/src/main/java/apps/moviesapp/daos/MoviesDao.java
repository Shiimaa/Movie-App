package apps.moviesapp.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import apps.moviesapp.domin.models.MovieDetailsResponse;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM MOVIEDETAILSRESPONSE")
    List<MovieDetailsResponse> getAllSavedMovies();

    @Insert
    void AddMovieToFavourite(MovieDetailsResponse movie);

    @Query("SELECT * FROM MOVIEDETAILSRESPONSE WHERE id = :id")
    MovieDetailsResponse isMovieSaved(int id);

    @Query("DELETE FROM MOVIEDETAILSRESPONSE WHERE id = :id")
    void deleteSavedMovie(int id);

}
