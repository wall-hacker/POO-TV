import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Input {
    private static Input input = null;
    @Getter @Setter
    private ArrayList<User> users;
    @Getter @Setter
    private ArrayList<Movie> movies;
    @Getter @Setter
    private ArrayList<Action> actions;

    public static Input getInput() {
        if (input == null) {
            input = new Input();
        }
        return input;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
