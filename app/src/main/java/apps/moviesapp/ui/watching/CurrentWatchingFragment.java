package apps.moviesapp.ui.watching;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import apps.moviesapp.R;
import apps.moviesapp.databinding.FragmentCurrentWatchingBinding;
import apps.moviesapp.ui.moviedetails.MovieDetailsActivity;
import apps.moviesapp.utils.CommonMethods;
import apps.moviesapp.utils.Constant;

public class CurrentWatchingFragment extends Fragment implements Paginate.Callbacks {
    private FragmentCurrentWatchingBinding binding;
    private CurrentPlayingMoviesViewModel viewModel;
    private CurrentWatchingMoviesRecyclerAdapter currentWatchingMoviesAdapter;

    private Paginate paginate;
    private boolean isLoadingData = false;
    private boolean isAllDataLoaded = false;
    private final AtomicInteger pageNumber = new AtomicInteger(1);
    private int currentPage = -1;
    private static final int itemsNumber = 5;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_watching, container, false);
        viewModel = new ViewModelProvider(this).get(CurrentPlayingMoviesViewModel.class);
        binding.setCurrentPlayingFragment(this);

        setupRecyclerView();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        currentWatchingMoviesAdapter = new CurrentWatchingMoviesRecyclerAdapter();
        binding.currentPlayingItemsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.currentPlayingItemsRecyclerView.setAdapter(currentWatchingMoviesAdapter);
        binding.currentPlayingItemsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 1) {
                    outRect.top = CommonMethods.dp2pixels(10);
                }
            }
        });

        currentWatchingMoviesAdapter.setClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra(Constant.MOVIE_ID_KEY,movieId);
            startActivity(intent);
            Animatoo.animateSlideRight(requireContext());
        });

        paginate = Paginate.with(binding.currentPlayingItemsRecyclerView, this)
                .setLoadingTriggerThreshold(itemsNumber)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(null)
                .build();

        getPopularMovies();

    }

    private void getPopularMovies() {
        isLoadingData = true;
        isAllDataLoaded = false;

        viewModel.getCurrentPlayingMovies(pageNumber.get()).observe(getViewLifecycleOwner(), moviesResponse -> {
            if (moviesResponse.getResults().size() > 0) {
                if (currentPage == -1 || moviesResponse.getPage() > currentPage) {
                    currentPage = moviesResponse.getPage();
                    pageNumber.incrementAndGet();
                    isLoadingData = false;
                    currentWatchingMoviesAdapter.setMovieItemList(moviesResponse.getResults());
                }
            } else {
                isLoadingData = false;
                isAllDataLoaded = true;
                paginate.setHasMoreDataToLoad(false);

            }
        });
    }

    @Override
    public synchronized void onLoadMore() {
        if (pageNumber.get() != 1)
            getPopularMovies();
    }

    @Override
    public synchronized boolean isLoading() {
        return isLoadingData;
    }

    @Override
    public synchronized boolean hasLoadedAllItems() {
        return isAllDataLoaded;
    }
}