package apps.moviesapp.ui.top;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import apps.moviesapp.domin.models.MoviesResponse;
import apps.moviesapp.ui.popular.PopularMoviesRepo;

public class TopRatedMoviesViewModel extends ViewModel {
    LiveData<MoviesResponse> getTopRatedMovies(int pageNumber) {
        return TopRatedMoviesRepo.getInstance().getTopRatedMovies(pageNumber);
    }

    LiveData<String> handelError() {
        return PopularMoviesRepo.getInstance().handelError;
    }

}
