package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;

public abstract class ChangePage extends ChangePageAction {
    /**
     * method called by main which takes care of all the ChangePage type actions
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output) {
        String page = action.getPage();
        switch (page) {
            case "login" -> login(currentPage, currentUser, output);
            case "register" -> register(currentPage, currentUser, output);
            case "logout" -> logout(currentPage, currentUser, output);
            case "movies" -> movies(currentPage, currentUser, output);
            case "see details" -> seeDetails(action, currentPage, currentUser, output);
            case "upgrades" -> upgrades(currentPage, currentUser, output);
            default -> throw new IllegalStateException("Unexpected value: " + page);
        }
    }
}
