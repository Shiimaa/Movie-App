package apps.moviesapp.ui.moviedetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import apps.moviesapp.R;
import apps.moviesapp.databinding.ActivityMovieDetailsBinding;
import apps.moviesapp.dispatchqueues.AppQueues;
import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.interfaces.LocalMoviesUpdated;
import apps.moviesapp.operations.db.MoviesOperations;
import apps.moviesapp.utils.Constant;
import apps.moviesapp.utils.ProgressDialog;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;
    private MovieGenresRecyclerAdapter movieGenresAdapter;
    private boolean isMovieSaved = false;
    private MovieDetailsResponse movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        binding.setMovieDetailsScreen(this);
        setupMovieGenresRecycler();
        getMovieDetails();
        checkIfMovieSaved();
    }

    private void checkIfMovieSaved() {
        AppQueues.postToDbQueue(() -> MoviesOperations.isMovieSaved(getIntent().getIntExtra(Constant.MOVIE_ID_KEY, 0),
                new LocalMoviesUpdated() {
                    @Override
                    public void onMovieSaved() {

                    }

                    @Override
                    public void onMovieDeleted() {

                    }

                    @Override
                    public void isMovieSaved(boolean isSaved) {
                        isMovieSaved = isSaved;
                        if (isSaved)
                            binding.movieDetailsSaveIcon.setImageResource(R.drawable.ic_saved);
                        else
                            binding.movieDetailsSaveIcon.setImageResource(R.drawable.ic_unsaved);
                    }
                }));
    }

    private void setupMovieGenresRecycler() {
        movieGenresAdapter = new MovieGenresRecyclerAdapter();
        binding.movieDetailsMovieGenreRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.movieDetailsMovieGenreRv.setAdapter(movieGenresAdapter);
    }

    private void getMovieDetails() {
        ProgressDialog dialog = new ProgressDialog();
        dialog.showDialog(this);
        viewModel.getMovieDetails(getIntent().getIntExtra(Constant.MOVIE_ID_KEY, 0))
                .observe(this, movieDetailsResponse -> {
                    dialog.dismissDialog();
                    movieDetails = movieDetailsResponse;
                    Glide.with(MovieDetailsActivity.this)
                            .load(Constant.IMAGE_BASE_URL + movieDetailsResponse.getBackdropPath())
                            .into(binding.movieDetailsImage);

                    binding.setMovieDetailsData(movieDetailsResponse);

                    movieGenresAdapter.setMovieGenres(movieDetailsResponse.getGenres());
                    binding.movieDetailsMovieAverageVote.setText(String.valueOf(movieDetailsResponse.getVoteAverage()));
                    binding.movieDetailsMovieVoteCount.setText(String.valueOf(movieDetailsResponse.getVoteCount()));

                });

        viewModel.handelError().observe(this, s -> {
            dialog.dismissDialog();
            Toast.makeText(MovieDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
        });
    }

    public void back(View view) {
        onBackPressed();
    }

    public void checkIsMovieSaved(View view) {
        AppQueues.postToDbQueue(() -> {
            if (isMovieSaved) {
                isMovieSaved = false;
                MoviesOperations.removeMovieFromFavourite(getIntent().getIntExtra(Constant.MOVIE_ID_KEY, 0),
                        new LocalMoviesUpdated() {
                            @Override
                            public void onMovieSaved() {

                            }

                            @Override
                            public void onMovieDeleted() {
                                Toast.makeText(MovieDetailsActivity.this, R.string.movie_deleted_from_favourite, Toast.LENGTH_SHORT).show();
                                binding.movieDetailsSaveIcon.setImageResource(R.drawable.ic_unsaved);
                            }

                            @Override
                            public void isMovieSaved(boolean isSaved) {

                            }
                        });
            } else {
                isMovieSaved = true;
                MoviesOperations.AddMovieToFavourite(movieDetails,
                        new LocalMoviesUpdated() {
                            @Override
                            public void onMovieSaved() {
                                Toast.makeText(MovieDetailsActivity.this, R.string.movie_added_to_favourite, Toast.LENGTH_SHORT).show();
                                binding.movieDetailsSaveIcon.setImageResource(R.drawable.ic_saved);
                            }

                            @Override
                            public void onMovieDeleted() {

                            }

                            @Override
                            public void isMovieSaved(boolean isSaved) {

                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideLeft(this);
    }
}