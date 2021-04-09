package apps.moviesapp.ui.popular;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import apps.moviesapp.R;
import apps.moviesapp.databinding.FragmentPopularBinding;
import apps.moviesapp.ui.moviedetails.MovieDetailsActivity;
import apps.moviesapp.utils.CommonMethods;
import apps.moviesapp.utils.Constant;

public class PopularFragment extends Fragment implements Paginate.Callbacks {
    private FragmentPopularBinding binding;
    private PopularMoviesViewModel viewModel;
    private PopularMoviesRecyclerAdapter popularMoviesAdapter;

    private Paginate paginate;
    private boolean isLoadingData = false;
    private boolean isAllDataLoaded = false;
    private final AtomicInteger pageNumber = new AtomicInteger(1);
    private int currentPage = -1;
    private static final int itemsNumber = 5;
    private boolean isSearchTextClicked = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular, container, false);
        viewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);
        binding.setPopularFragment(this);

        setupRecyclerView();
        setListenerOnSearchText();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        popularMoviesAdapter = new PopularMoviesRecyclerAdapter();
        binding.popularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.popularItemsRecyclerView.setAdapter(popularMoviesAdapter);
        binding.popularItemsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 1) {
                    outRect.top = CommonMethods.dp2pixels(10);
                }
            }
        });

        popularMoviesAdapter.setClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra(Constant.MOVIE_ID_KEY, movieId);
            startActivity(intent);
            Animatoo.animateSlideRight(requireContext());
        });

        paginate = Paginate.with(binding.popularItemsRecyclerView, this)
                .setLoadingTriggerThreshold(itemsNumber)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(null)
                .build();

        Log.d("TAG","teiuffhe,called 2");
        getPopularMovies();

    }

    private void getPopularMovies() {
        isLoadingData = true;
        isAllDataLoaded = false;

        viewModel.getPopularMovies(pageNumber.get()).observe(getViewLifecycleOwner(), moviesResponse -> {
            if (moviesResponse.getResults().size() > 0) {
                if (currentPage == -1 || moviesResponse.getPage() > currentPage) {
                    currentPage = moviesResponse.getPage();
                    pageNumber.incrementAndGet();
                    isLoadingData = false;
                    popularMoviesAdapter.setMovieItemList(moviesResponse.getResults());
                }
            } else {
                isLoadingData = false;
                isAllDataLoaded = true;
                paginate.setHasMoreDataToLoad(false);

            }
        });

        viewModel.handelError().observe(getViewLifecycleOwner(), s ->
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show());
    }

    private void setListenerOnSearchText() {
        binding.popularSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    binding.popularCloseSearchIcon.setVisibility(View.VISIBLE);
                else
                    binding.popularCloseSearchIcon.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void clearSearchText(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.popularSearchText.getWindowToken(), 0);
        binding.popularScreenTitle.setVisibility(View.VISIBLE);

        binding.popularSearchText.setText("");
        binding.popularCloseSearchIcon.setVisibility(View.GONE);
        if (isSearchTextClicked) {
            currentPage = -1;
            pageNumber.set(1);
            isSearchTextClicked = false;
            popularMoviesAdapter.clearList();
            isLoadingData = false;
            isAllDataLoaded = false;
            paginate.setHasMoreDataToLoad(true);
            Log.d("TAG","teiuffhe,called 1");
            getPopularMovies();
        }
    }

    public void getSearchData(View view) {
        if (binding.popularSearchText.getText().toString().isEmpty())
            return;

        binding.popularScreenTitle.setVisibility(View.INVISIBLE);
        pageNumber.set(1);
        isSearchTextClicked = true;
        popularMoviesAdapter.clearList();
        currentPage = -1;
        isLoadingData = false;
        isAllDataLoaded = false;
        paginate.setHasMoreDataToLoad(true);
        Log.d("TAG","teiuhe,called 1");
        getFilteredMovies();

    }

    private void getFilteredMovies() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.popularSearchText.getWindowToken(), 0);

        isLoadingData = true;
        isAllDataLoaded = false;

        viewModel.getFilteredMovies(binding.popularSearchText.getText().toString(), pageNumber.get()).observe(getViewLifecycleOwner(), moviesResponse -> {
            if (moviesResponse.getResults().size() > 0) {
                if (currentPage == -1 || moviesResponse.getPage() > currentPage) {
                    currentPage = moviesResponse.getPage();
                    pageNumber.incrementAndGet();
                    isLoadingData = false;
                    popularMoviesAdapter.setMovieItemList(moviesResponse.getResults());
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
        if (pageNumber.get() != 1) {
            if (isSearchTextClicked) {
                Log.d("TAG","teiuhe,called 2");
                getFilteredMovies();
            }
            else {
                Log.d("TAG","teiuffhe,called 3");
                getPopularMovies();
            }
        }
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