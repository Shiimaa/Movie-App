package apps.moviesapp.ui.watching;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import apps.moviesapp.domin.models.MoviesResponse;
import apps.moviesapp.ui.popular.PopularMoviesRepo;

public class CurrentPlayingMoviesViewModel extends ViewModel {
    LiveData<MoviesResponse> getCurrentPlayingMovies(int pageNumber) {
        return CurrentPlayingMoviesRepo.getInstance().getCurrentPlayingMovies(pageNumber);
    }

    LiveData<String> handelError() {
        return PopularMoviesRepo.getInstance().handelError;
    }

}
