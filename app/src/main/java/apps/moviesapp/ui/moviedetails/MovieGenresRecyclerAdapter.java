package apps.moviesapp.ui.moviedetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import apps.moviesapp.R;
import apps.moviesapp.domin.models.GenresItem;

public class MovieGenresRecyclerAdapter extends RecyclerView.Adapter<MovieGenresRecyclerAdapter.MovieGenresHolder> {
    private List<GenresItem> movieGenres;

    public MovieGenresRecyclerAdapter() {
        movieGenres = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieGenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieGenresHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_genres_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGenresHolder holder, int position) {
        holder.movieGenresText.setText(movieGenres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return movieGenres.size();
    }

    public void setMovieGenres(List<GenresItem> movieGenres) {
        this.movieGenres = movieGenres;
        notifyDataSetChanged();
    }

    public static class MovieGenresHolder extends RecyclerView.ViewHolder {
        private final TextView movieGenresText;

        public MovieGenresHolder(@NonNull View itemView) {
            super(itemView);
            movieGenresText = itemView.findViewById(R.id.movie_generns_text);
        }
    }
}
