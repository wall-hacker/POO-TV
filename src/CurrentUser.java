import lombok.Getter;
import lombok.Setter;

public class CurrentUser {
    private static CurrentUser currentUser = null;
    @Getter @Setter
    private User user;

    private CurrentUser() {
    }

    public static CurrentUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        return currentUser;
    }
}