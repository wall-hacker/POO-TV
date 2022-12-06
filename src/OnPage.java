import com.fasterxml.jackson.databind.node.ArrayNode;

abstract class OnPage extends OnPageAction {
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output) {
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
            default -> throw new IllegalStateException("Unexpected value: " + feature);
        }
    }
}
