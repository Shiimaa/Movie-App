package apps.moviesapp.domin.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private List<MovieItem> movieItems;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setResults(List<MovieItem> movieItems){
		this.movieItems = movieItems;
	}

	public List<MovieItem> getResults(){
		return movieItems;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"page = '" + page + '\'' + 
			",movieItems = '" + movieItems + '\'' +
			"}";
		}
}