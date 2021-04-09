package apps.moviesapp.ui.moviedetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.ui.popular.PopularMoviesRepo;

public class MovieDetailsViewModel extends ViewModel {
    LiveData<MovieDetailsResponse> getMovieDetails(int movieId) {
        return MovieDetailsRepo.getInstance().getMovieDetails(movieId);
    }

    LiveData<String> handelError() {
        return PopularMoviesRepo.getInstance().handelError;
    }

}
