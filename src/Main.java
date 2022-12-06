import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();
        CurrentPage currentPage = new CurrentPage("homepage", input);
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        currentUser.setUser((User)null);

        ArrayList<Action> actions = input.getActions();
        for(Action action : actions) {
            switch(action.getType()) {
                case "change page" -> ChangePage.run(action, currentPage, currentUser, output);
                case "on page" -> OnPage.run(action, currentPage, currentUser, output);
                default -> System.out.println("easter egg");
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
