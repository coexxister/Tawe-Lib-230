package JavaFX;

import Core.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AccountSearchController extends SceneController {

    @FXML
    private TextField accountName;
    public static int userID;

    @FXML
    private void handleAccountSearchButtonAction(ActionEvent event) throws IOException {
        userID = Integer.parseInt(accountName.getText());
        loadSubscene(SceneController.STAFF_INTERFACE);
    }
}
