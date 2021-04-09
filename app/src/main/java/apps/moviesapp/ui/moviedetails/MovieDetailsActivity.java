package apps.moviesapp.ui.moviedetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import apps.moviesapp.R;
import apps.moviesapp.databinding.ActivityMovieDetailsBinding;
import apps.moviesapp.utils.Constant;
import apps.moviesapp.utils.ProgressDialog;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;
    private MovieGenresRecyclerAdapter movieGenresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        binding.setMovieDetailsScreen(this);
        setupMovieGenresRecycler();
        getMovieDetails();
    }

    private void setupMovieGenresRecycler() {
        movieGenresAdapter = new MovieGenresRecyclerAdapter();
        binding.movieDetailsMovieGenreRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.movieDetailsMovieGenreRv.setAdapter(movieGenresAdapter);
    }

    private void getMovieDetails() {
        ProgressDialog dialog = new ProgressDialog();
        dialog.showDialog(this);
        viewModel.getMovieDetails(getIntent().getIntExtra(Constant.MOVIE_ID_KEY, 0))
                .observe(this, movieDetailsResponse -> {
                    dialog.dismissDialog();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideLeft(this);
    }
}