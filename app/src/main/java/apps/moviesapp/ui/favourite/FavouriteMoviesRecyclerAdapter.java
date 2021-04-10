package apps.moviesapp.ui.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import apps.moviesapp.R;
import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.utils.Constant;

public class FavouriteMoviesRecyclerAdapter extends RecyclerView.Adapter<FavouriteMoviesRecyclerAdapter.PopularMoviesHolder> {
    private List<MovieDetailsResponse> movieItemList;
    private ItemClickListener clickListener;

    public FavouriteMoviesRecyclerAdapter() {
        movieItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PopularMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularMoviesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Constant.IMAGE_BASE_URL + movieItemList.get(position).getBackdropPath())
                .into(holder.movieImage);

        holder.movieTitle.setText(movieItemList.get(position).getTitle());
        holder.movieRate.setText(String.valueOf(movieItemList.get(position).getVoteAverage()));

        holder.itemView.setOnClickListener(v -> clickListener.onClick(movieItemList.get(position).getId()));
    }

    @Override
    public void onViewRecycled(@NonNull PopularMoviesHolder holder) {
        Glide.with(holder.itemView.getContext())
                .clear(holder.movieImage);

        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return movieItemList.size();
    }

    public void setMovieItemList(List<MovieDetailsResponse> movieItemList) {
        this.movieItemList = movieItemList;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onClick(int movieId);
    }

    public static class PopularMoviesHolder extends RecyclerView.ViewHolder {
        private final ImageView movieImage;
        private final TextView movieTitle;
        private final TextView movieRate;

        public PopularMoviesHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_item_movie_image);
            movieTitle = itemView.findViewById(R.id.movie_item_movie_title);
            movieRate = itemView.findViewById(R.id.movie_item_movie_vote);
        }
    }
}
