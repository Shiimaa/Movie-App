package apps.moviesapp.domin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailsResponse {

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genres")
    private List<GenresItem> genres;

    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setGenres(List<GenresItem> genres) {
        this.genres = genres;
    }

    public List<GenresItem> getGenres() {
        return genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return
                "MovieDetailsResponse{" +
                        ",title = '" + title + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",genres = '" + genres + '\'' +
                        ",id = '" + id + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        "}";
    }
}