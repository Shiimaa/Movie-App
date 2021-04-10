package apps.moviesapp.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import apps.moviesapp.R;
import apps.moviesapp.databinding.ActivityMainBinding;
import apps.moviesapp.ui.favourite.FavouriteFragment;
import apps.moviesapp.ui.popular.PopularFragment;
import apps.moviesapp.ui.top.TopMoviesFragment;
import apps.moviesapp.ui.watching.CurrentWatchingFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.home_container_layout);
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (currentFragment instanceof PopularFragment)
                            return true;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_container_layout, new PopularFragment())
                                .commit();
                        return true;

                    case R.id.navigation_top_movies:
                        if (currentFragment instanceof TopMoviesFragment)
                            return true;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_container_layout, new TopMoviesFragment())
                                .commit();
                        return true;

                    case R.id.navigation_watching_movies:
                        if (currentFragment instanceof CurrentWatchingFragment)
                            return true;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_container_layout, new CurrentWatchingFragment())
                                .commit();

                        return true;

                    case R.id.navigation_favourite_movies:
                        if (currentFragment instanceof FavouriteFragment)
                            return true;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_container_layout, new FavouriteFragment())
                                .commit();
                        return true;

                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHomeActivity(this);

        binding.homeBottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container_layout, new PopularFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}