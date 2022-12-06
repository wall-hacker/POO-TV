import com.fasterxml.jackson.databind.node.ArrayNode;

abstract class ChangePage extends ChangePageAction {
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
