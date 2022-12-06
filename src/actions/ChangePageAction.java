package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import database.Movie;

import java.util.ArrayList;

abstract class ChangePageAction {
    static void login(final CurrentPage currentPage, final CurrentUser currentUser,
                      final ArrayNode output) {
        if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
            currentPage.setCurrentPage("login", null, null);
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }

    static void register(final CurrentPage currentPage, final CurrentUser currentUser,
                         final ArrayNode output) {
        if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
            currentPage.setCurrentPage("register", null, null);
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }

    static void logout(final CurrentPage currentPage, final CurrentUser currentUser,
                       final ArrayNode output) {
        if (currentUser.getUser() != null) {
            currentUser.setUser(null);
            currentPage.setCurrentPage("homepage", null, null);
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }

    static void movies(final CurrentPage currentPage, final CurrentUser currentUser,
                       final ArrayNode output) {
        if (currentUser.getUser() != null) {
            currentPage.setMoviesList(currentPage.getInput().getMovies());

            ArrayList<Movie> movies = currentPage.getMoviesList();
            ArrayList<Movie> unbannedMovies = new ArrayList<Movie>();
            for (Movie movie : movies) {
                String country = currentUser.getUser().getCredentials().getCountry();
                if (!movie.getCountriesBanned().contains(country)) {
                    unbannedMovies.add(movie);
                }
            }
            currentPage.setCurrentPage("movies", null, unbannedMovies);

            output.addPOJO(new FormattedOutput(currentPage.getMoviesList(),
                    currentUser.getUser()));
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }

    static void seeDetails(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output) {
        if (currentPage.getName().equals("movies")) {
            Movie currentMovie = null;
            ArrayList<Movie> movies = currentPage.getMoviesList();
            for (Movie movie : movies) {
                if (movie.getName().equals(action.getMovie())) {
                    currentMovie = movie;
                }
            }
            if (currentMovie != null) {
                currentPage.setName("see details");
                currentPage.setCurrentMovie(currentMovie);
                currentPage.setCurrentPage("see details", currentMovie, movies);
                output.addPOJO(new FormattedOutput(currentUser.getUser(), currentMovie));
            } else {
                output.addPOJO(new FormattedOutput());
            }
        }
    }

    static void upgrades(final CurrentPage currentPage, final CurrentUser currentUser,
                         final ArrayNode output) {
        if (currentUser.getUser() != null) {
            currentPage.setCurrentPage("upgrades", null, null);
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }
}
