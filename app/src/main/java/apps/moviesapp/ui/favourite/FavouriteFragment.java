package apps.moviesapp.ui.favourite;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.jetbrains.annotations.NotNull;

import apps.moviesapp.R;
import apps.moviesapp.databinding.FragmentFavouriteBinding;
import apps.moviesapp.dispatchqueues.AppQueues;
import apps.moviesapp.operations.db.MoviesOperations;
import apps.moviesapp.ui.moviedetails.MovieDetailsActivity;
import apps.moviesapp.utils.CommonMethods;
import apps.moviesapp.utils.Constant;

public class FavouriteFragment extends Fragment {
    private FragmentFavouriteBinding binding;
    private FavouriteMoviesRecyclerAdapter favouriteMoviesAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);
        binding.setFavouriteFragment(this);

        setupRecyclerView();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        favouriteMoviesAdapter = new FavouriteMoviesRecyclerAdapter();
        binding.topItemsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.topItemsRecyclerView.setAdapter(favouriteMoviesAdapter);
        binding.topItemsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 1) {
                    outRect.top = CommonMethods.dp2pixels(10);
                }
            }
        });

        favouriteMoviesAdapter.setClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra(Constant.MOVIE_ID_KEY, movieId);
            startActivity(intent);
            Animatoo.animateSlideRight(requireContext());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavouriteMovies();
    }

    private void getFavouriteMovies() {
        AppQueues.postToDbQueue(() -> MoviesOperations.getAllSavedMovies(savedMovies -> {
            AppQueues.postToMainThreadQueue(() -> {
                if (savedMovies.isEmpty()) {
                    binding.topScreenNoSavedMovies.setVisibility(View.VISIBLE);
                    binding.topItemsRecyclerView.setVisibility(View.GONE);
                } else {
                    binding.topScreenNoSavedMovies.setVisibility(View.GONE);
                    binding.topItemsRecyclerView.setVisibility(View.VISIBLE);
                    favouriteMoviesAdapter.setMovieItemList(savedMovies);
                }
            });

        }));
    }

}