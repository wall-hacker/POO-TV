import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CurrentPage {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Input input;
    @Getter @Setter
    private ArrayList<Movie> moviesList;
    @Getter @Setter
    private Movie currentMovie;

    public CurrentPage(String name, Input input) {
        this.input = input;
        this.name = name;
    }

    public Input getInput() {
        return this.input;
    }

    public void setMoviesList(List<Movie> moviesList) {
        if (moviesList != null) {
            this.moviesList = new ArrayList(moviesList);
        } else {
            this.moviesList = new ArrayList();
        }
    }

    public void setCurrentPage(String name, Movie currentMovie, ArrayList<Movie> moviesList) {
        this.setName(name);
        this.setCurrentMovie(currentMovie);
        this.setMoviesList(moviesList);
    }
}
