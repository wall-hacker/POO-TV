import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Movie {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int year;
    @Getter @Setter
    private Integer duration;
    @Getter @Setter
    private ArrayList<String> genres;
    @Getter @Setter
    private ArrayList<String> actors;
    @Getter @Setter
    private ArrayList<String> countriesBanned;
    @Getter @Setter
    private int numLikes;
    @Getter @Setter
    private Integer rating;
    @Getter @Setter
    private int numRatings;

    public Movie() {
        this.rating = 0;
    }

    public Movie(Movie movie) {
        this.name = movie.name;
        this.year = movie.year;
        this.duration = movie.duration;
        this.genres = new ArrayList();
        this.genres.addAll(movie.genres);
        this.actors = new ArrayList();
        this.actors.addAll(movie.actors);
        this.countriesBanned = new ArrayList();
        this.countriesBanned.addAll(movie.countriesBanned);
        this.numLikes = movie.numLikes;
        this.rating = movie.rating;
        this.numRatings = movie.numRatings;
    }

    public void incrementNumLikes() {
        this.numLikes++;
    }
    public void incrementNumRatings() {
        this.numRatings++;
    }

}
