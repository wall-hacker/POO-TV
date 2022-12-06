import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class ChangePage {
    public static void run(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) {
        String page = action.getPage();
        switch (page) {
            case "login":
                if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
                    currentPage.setCurrentPage("login", null, null);
                } else {
                    output.addPOJO(new FormatedOutput());
                }
                break;
            case "register":
                if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
                    currentPage.setCurrentPage("register", null, null);
                } else {
                    output.addPOJO(new FormatedOutput());
                }
                break;
            case "logout":
                if (currentUser.getUser() != null) {
                    currentUser.setUser(null);
                    currentPage.setCurrentPage("homepage", null, null);
                } else {
                    output.addPOJO(new FormatedOutput());
                }
                break;
            case "movies":
                if (currentUser.getUser() != null) {
                    currentPage.setMoviesList(currentPage.getInput().getMovies());

                    ArrayList<Movie> movies = currentPage.getMoviesList();
                    ArrayList<Movie> unbannedMovies = new ArrayList<Movie>();
                    for (Movie movie : movies) {
                        if (!movie.getCountriesBanned().contains(currentUser.getUser().getCredentials().getCountry())) {
                            unbannedMovies.add(movie);
                        }
                    }
                    currentPage.setCurrentPage("movies", null, unbannedMovies);

                    output.addPOJO(new FormatedOutput(currentPage.getMoviesList(), currentUser.getUser()));
                } else {
                    output.addPOJO(new FormatedOutput());
                }
                break;
            case "see details":
                if (currentPage.getName().equals("movies")) {
                    Movie currentMovie = null;
                    ArrayList<Movie> movies = currentPage.getMoviesList();
                    for(Movie movie : movies) {
                        if(movie.getName().equals(action.getMovie())) {
                            currentMovie = movie;
                        }
                    }
                    if (currentMovie != null) {
                        currentPage.setName("see details");
                        currentPage.setCurrentMovie(currentMovie);
                        currentPage.setCurrentPage("see details", currentMovie, movies);
                        output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));
                    } else {
                        output.addPOJO(new FormatedOutput());
                    }
                }
                break;
            case "upgrades":
                if (currentUser.getUser() != null) {
                    currentPage.setCurrentPage("upgrades", null, null);
                } else {
                    output.addPOJO(new FormatedOutput());
                }
                break;
            default:
                return;
        }
    }
}
