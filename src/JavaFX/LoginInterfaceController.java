package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginInterfaceController extends SceneController {

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
    }
}