package apps.moviesapp.ui.top;

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
import apps.moviesapp.databinding.FragmentTopMoviesBinding;
import apps.moviesapp.ui.moviedetails.MovieDetailsActivity;
import apps.moviesapp.ui.popular.PopularMoviesRecyclerAdapter;
import apps.moviesapp.utils.CommonMethods;
import apps.moviesapp.utils.Constant;

public class TopMoviesFragment extends Fragment implements Paginate.Callbacks {
    private FragmentTopMoviesBinding binding;
    private TopRatedMoviesViewModel viewModel;
    private TopRatedMoviesRecyclerAdapter topRatedMoviesAdapter;

    private Paginate paginate;
    private boolean isLoadingData = false;
    private boolean isAllDataLoaded = false;
    private final AtomicInteger pageNumber = new AtomicInteger(1);
    private int currentPage = -1;
    private static final int itemsNumber = 5;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_movies, container, false);
        viewModel = new ViewModelProvider(this).get(TopRatedMoviesViewModel.class);
        binding.setTopFragment(this);

        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        topRatedMoviesAdapter = new TopRatedMoviesRecyclerAdapter();
        binding.topItemsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.topItemsRecyclerView.setAdapter(topRatedMoviesAdapter);
        binding.topItemsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 1) {
                    outRect.top = CommonMethods.dp2pixels(10);
                }
            }
        });

        topRatedMoviesAdapter.setClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra(Constant.MOVIE_ID_KEY,movieId);
            startActivity(intent);
            Animatoo.animateSlideRight(requireContext());
        });

        paginate = Paginate.with(binding.topItemsRecyclerView, this)
                .setLoadingTriggerThreshold(itemsNumber)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(null)
                .build();

        getPopularMovies();

    }

    private void getPopularMovies() {
        isLoadingData = true;
        isAllDataLoaded = false;

        viewModel.getTopRatedMovies(pageNumber.get()).observe(getViewLifecycleOwner(), moviesResponse -> {
            if (moviesResponse.getResults().size() > 0) {
                if (currentPage == -1 || moviesResponse.getPage() > currentPage) {
                    currentPage = moviesResponse.getPage();
                    pageNumber.incrementAndGet();
                    isLoadingData = false;
                    topRatedMoviesAdapter.setMovieItemList(moviesResponse.getResults());
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