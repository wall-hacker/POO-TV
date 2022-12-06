import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class User {

    @Getter @Setter
    private Credentials credentials;
    @Getter @Setter
    private int tokensCount;
    @Getter @Setter
    private int numFreePremiumMovies = 15;
    @Getter @Setter
    private ArrayList<Movie> purchasedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> watchedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> likedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> ratedMovies = new ArrayList();

    public User() {
    }
    public User(User currentUser) {
        this.credentials = new Credentials();
        this.credentials.setName(currentUser.getCredentials().getName());
        this.credentials.setAccountType(currentUser.getCredentials().getAccountType());
        this.credentials.setBalance(currentUser.getCredentials().getBalance());
        this.credentials.setCountry(currentUser.getCredentials().getCountry());
        this.credentials.setPassword(currentUser.getCredentials().getPassword());
        this.tokensCount = currentUser.tokensCount;
        this.numFreePremiumMovies = currentUser.numFreePremiumMovies;
        this.purchasedMovies = new ArrayList();
        this.purchasedMovies.addAll(currentUser.purchasedMovies);
        this.watchedMovies = new ArrayList();
        this.watchedMovies.addAll(currentUser.watchedMovies);
        this.likedMovies = new ArrayList();
        this.likedMovies.addAll(currentUser.likedMovies);
        this.ratedMovies = new ArrayList();
        this.ratedMovies.addAll(currentUser.ratedMovies);
    }
    public User(Credentials credentials) {
        this.credentials = new Credentials();
        this.credentials.setName(credentials.getName());
        this.credentials.setAccountType(credentials.getAccountType());
        this.credentials.setBalance(credentials.getBalance());
        this.credentials.setCountry(credentials.getCountry());
        this.credentials.setPassword(credentials.getPassword());
        this.purchasedMovies = new ArrayList();
        this.watchedMovies = new ArrayList();
        this.likedMovies = new ArrayList();
        this.ratedMovies = new ArrayList();
        this.tokensCount = 0;
        this.numFreePremiumMovies = 15;
    }

    public void decrementNumFreePremiumMovies() {
        --this.numFreePremiumMovies;
    }

    public void decrementTokensCount(final int numTokens) {
        this.tokensCount -= numTokens;
    }
}
