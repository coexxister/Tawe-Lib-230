package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AccountsSearchController extends SceneController {

    @FXML
    private TextField userIDSearch;

    @FXML
    private void handleSearchIdButtonAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.STAFF_INTERFACE);
    }
}
