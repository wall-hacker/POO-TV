import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class OnPage {

    public static void run(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        String feature = action.getFeature();
        switch (feature) {
            case "login" -> login(action, currentPage, currentUser, output);
            case "register" -> register(action, currentPage, currentUser, output);
            case "search" -> search(action, currentPage, currentUser, output);
            case "filter" -> filter(action, currentPage, currentUser, output);
            case "buy tokens" -> buyTokens(action, currentUser, output);
            case "buy premium account" -> buyPremiumAccount(currentPage, currentUser, output);
            case "purchase" -> purchase(currentPage, currentUser, output);
            case "watch" -> watch(currentPage, currentUser, output);
            case "like" -> like(currentPage, currentUser, output);
            case "rate" -> rate(action, currentPage, currentUser, output);
        }
    }

    private static void login(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("login") && currentUser.getUser() == null) {
            User validUser = null;
            Credentials loginCredentials = action.getCredentials();
            ArrayList<User> users = currentPage.getInput().getUsers();
            for(User user : users) {
                if (user.getCredentials().getName().equals(loginCredentials.getName()) &&
                        user.getCredentials().getPassword().equals(loginCredentials.getPassword())) {
                    validUser = user;
                }
            }
            if (validUser != null) {
                currentUser.setUser(validUser);
                output.addPOJO(new FormatedOutput(currentPage.getMoviesList(), currentUser.getUser()));
            } else {
                output.addPOJO(new FormatedOutput());
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
        currentPage.setName("homepage");
    }

    private static void register(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("register") && currentUser.getUser() == null) {
            User validUser = null;
            Credentials registerCredentials = action.getCredentials();
            ArrayList<User> users = currentPage.getInput().getUsers();
            for(User user : users) {
                if(user.getCredentials().getName().equals(registerCredentials.getName()) &&
                        user.getCredentials().getPassword().equals(registerCredentials.getPassword())) {
                    validUser = user;
                }
            }
            if(validUser == null) {
                validUser = new User(registerCredentials);
                currentPage.getInput().addUser(validUser);
                currentUser.setUser(validUser);
                output.addPOJO(new FormatedOutput(currentPage.getMoviesList(), validUser));
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
        currentPage.setName("homepage");
    }

    private static void search(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("movies")) {
            ArrayList<Movie> movies = currentPage.getMoviesList();
            ArrayList<Movie> searchResult = new ArrayList<Movie>();
            for(Movie movie : movies) {
                if(movie.getName().startsWith(action.getStartsWith())) {
                    searchResult.add(movie);
                }
            }
            currentPage.setMoviesList(searchResult);
            output.addPOJO(new FormatedOutput(currentPage.getMoviesList(), currentUser.getUser()));
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    private static void filter(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("movies")) {
            ArrayList<Movie> movies = currentPage.getInput().getMovies();

            Contains contains = action.getFilters().getContains();
            if (contains != null) {
                if (contains.getGenre() != null) {
                    ArrayList<Movie> genreMovies = new ArrayList<Movie>();
                    for (Movie movie : movies) {
                        for (String genre : contains.getGenre()) {
                            if (movie.getGenres().contains(genre)) {
                                genreMovies.add(movie);
                            }
                        }
                    }
                    currentPage.setMoviesList(genreMovies);
                    movies = genreMovies;
                }

                if (contains.getActors() != null) {
                    ArrayList<Movie> actorMovies = new ArrayList<Movie>();
                    for (Movie movie : movies) {
                        for (String actor : contains.getActors()) {
                            if (movie.getActors().contains(actor)) {
                                actorMovies.add(movie);
                            }
                        }
                    }
                    currentPage.setMoviesList(actorMovies);
                }
            }

            Sort sort = action.getFilters().getSort();
            movies = currentPage.getMoviesList();
            if (sort != null) {
                if (sort.getDuration() != null) {
                    if (sort.getDuration().equals("increasing")) {
                        movies.sort(Comparator.comparingInt(Movie::getDuration).thenComparing(Movie::getRating));
                    } else {
                        movies.sort(Comparator.comparingInt(Movie::getDuration).reversed().thenComparing(Movie::getRating));
                    }
                }
                if (sort.getRating() != null && sort.getDuration() == null) {
                    if (sort.getRating().equals("increasing")) {
                        movies.sort(Comparator.comparingInt(Movie::getRating));
                    } else {
                        movies.sort(Comparator.comparingInt(Movie::getRating).reversed());
                    }
                }
            }

            ArrayList<Movie> unbannedMovies = new ArrayList<Movie>();
            for (Movie movie : movies) {
                if (!movie.getCountriesBanned().contains(currentUser.getUser().getCredentials().getCountry())) {
                    unbannedMovies.add(movie);
                }
            }
            currentPage.setMoviesList(unbannedMovies);

            output.addPOJO(new FormatedOutput(currentPage.getMoviesList(), currentUser.getUser()));
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    public static void buyTokens (Action action, CurrentUser currentUser, ArrayNode output) throws IOException {
        int totalTokens = Integer.parseInt(currentUser.getUser().getCredentials().getBalance());
        int wantedTokens = Integer.parseInt(action.getCount());
        if (wantedTokens <= totalTokens) {
            currentUser.getUser().setTokensCount(wantedTokens);
            currentUser.getUser().getCredentials().setBalance(Integer.toString(totalTokens - wantedTokens));
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    private static void buyPremiumAccount(CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("upgrades")) {
            if (currentUser.getUser().getTokensCount() > 10) {
                currentUser.getUser().getCredentials().setAccountType("premium");
                currentUser.getUser().setTokensCount(currentUser.getUser().getTokensCount() - 10);
            } else {
                output.addPOJO(new FormatedOutput());
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    private static void purchase(CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();
            int found = 0;
            ArrayList<Movie> movies = currentUser.getUser().getPurchasedMovies();
            for (Movie movie : movies) {
                if(movie.getName().equals(currentMovie.getName())) {
                    found = 1;
                    break;
                }
            }

            if (found == 0) {
                if (currentUser.getUser().getCredentials().getAccountType().equals("premium") && currentUser.getUser().getNumFreePremiumMovies() > 0) {
                    currentUser.getUser().decrementNumFreePremiumMovies();
                    currentUser.getUser().getPurchasedMovies().add(new Movie(currentMovie));
                    output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));
                } else if (currentUser.getUser().getTokensCount() > 2) {
                    currentUser.getUser().decrementTokensCount(2);
                    currentUser.getUser().getPurchasedMovies().add(new Movie(currentMovie));
                    output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));
                } else {
                    output.addPOJO(new FormatedOutput());
                }
            } else {
                output.addPOJO(new FormatedOutput());
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    public static void watch(CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();

            int purchased = 0;
            ArrayList<Movie> movies1 = currentUser.getUser().getPurchasedMovies();
            for(Movie movie : movies1) {
                if (movie.getName().equals(currentMovie.getName())) {
                    purchased = 1;
                    break;
                }
            }

            int watched = 0;
            ArrayList<Movie> movies2 = currentUser.getUser().getWatchedMovies();
            for(Movie movie : movies2) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            if (purchased == 1 && watched == 0) {
                currentUser.getUser().getWatchedMovies().add(new Movie(currentMovie));
                output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));
            } else {
                output.addPOJO(new FormatedOutput());
            }

        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    public static void like(CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();

            int watched = 0;
            ArrayList<Movie> watchedMovies = currentUser.getUser().getWatchedMovies();
            for (Movie movie : watchedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            int liked = 0;
            ArrayList<Movie> likedMovies = currentUser.getUser().getLikedMovies();
            for (Movie movie : likedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    liked = 1;
                    break;
                }
            }

            if (watched == 1 && liked == 0) {
                currentMovie.incrementNumLikes();
                currentUser.getUser().getLikedMovies().add(new Movie(currentMovie));
                updateUserMovieLists(currentPage, currentMovie);
                output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));

            } else {
                output.addPOJO(new FormatedOutput());
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    public static void rate(Action action, CurrentPage currentPage, CurrentUser currentUser, ArrayNode output) throws IOException {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();
            int rating = action.getRate();

            int watched = 0;
            ArrayList<Movie> watchedMovies = currentUser.getUser().getWatchedMovies();
            for(Movie movie : watchedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            int rated = 0;
            ArrayList<Movie> ratedMovies = currentUser.getUser().getRatedMovies();
            for (Movie movie : ratedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    rated = 1;
                    break;
                }
            }

            if (watched == 1 && rated == 0 && (rating <= 5 && rating >= 1)) {
                int movieRating = currentMovie.getRating();
                int movieNumRatings = currentMovie.getNumRatings();
                currentMovie.setRating((movieRating * movieNumRatings + rating) / (movieNumRatings + 1));
                currentMovie.incrementNumRatings();
                currentUser.getUser().getRatedMovies().add(new Movie(currentMovie));
                updateUserMovieLists(currentPage, currentMovie);
                output.addPOJO(new FormatedOutput(currentUser.getUser(), currentMovie));
            } else {
                output.addPOJO(new FormatedOutput());
            }
        } else {
            output.addPOJO(new FormatedOutput());
        }
    }

    public static void updateUserMovieLists (CurrentPage currentPage, Movie currentMovie) {
        ArrayList<User> users = currentPage.getInput().getUsers();
        for (User user : users) {
            ArrayList<Movie> purchasedMovies = user.getPurchasedMovies();
            ArrayList<Movie> watchedMovies = user.getWatchedMovies();
            ArrayList<Movie> likedMovies = user.getLikedMovies();
            ArrayList<Movie> ratedMovies = user.getRatedMovies();
            for (int i = 0; i < purchasedMovies.size(); i++) {
                if (purchasedMovies.get(i).getName().equals(currentMovie.getName())) {
                    purchasedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < watchedMovies.size(); i++) {
                if (watchedMovies.get(i).getName().equals(currentMovie.getName())) {
                    watchedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < likedMovies.size(); i++) {
                if (likedMovies.get(i).getName().equals(currentMovie.getName())) {
                    likedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < ratedMovies.size(); i++) {
                if (ratedMovies.get(i).getName().equals(currentMovie.getName())) {
                    ratedMovies.set(i, currentMovie);
                }
            }
        }
    }
}
