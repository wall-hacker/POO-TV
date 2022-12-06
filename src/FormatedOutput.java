import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FormatedOutput {
    @Getter @Setter
    private String error;
    @Getter @Setter
    private ArrayList<Movie> currentMoviesList;
    @Getter @Setter
    private User currentUser;

    public FormatedOutput() {
        this.error = "Error";
        this.currentMoviesList = new ArrayList<Movie>();
        this.currentUser = null;
    }

    public FormatedOutput(List<Movie> currentMoviesList, User currentUser) {
        this.currentMoviesList = new ArrayList<Movie>();
        for(Movie movie : currentMoviesList)
            this.currentMoviesList.add(new Movie(movie));
        this.currentUser = new User(currentUser);
    }

    public FormatedOutput(User currentUser, Movie movie) {
        this.currentUser = new User(currentUser);
        this.currentMoviesList = new ArrayList<Movie>();
        this.currentMoviesList.add(new Movie(movie));
    }
}
