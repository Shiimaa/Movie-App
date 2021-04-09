package apps.moviesapp.ui.popular;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import apps.moviesapp.domin.models.MoviesResponse;

public class PopularMoviesViewModel extends ViewModel {
    LiveData<MoviesResponse> getPopularMovies(int pageNumber) {
        return PopularMoviesRepo.getInstance().getPopularMovies(pageNumber);
    }

    LiveData<MoviesResponse> getFilteredMovies(String searchText,int pageNumber) {
        return PopularMoviesRepo.getInstance().getFilteredMovies(searchText,pageNumber);
    }

    LiveData<String> handelError() {
        return PopularMoviesRepo.getInstance().handelError;
    }

}
