package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainInterfaceController extends SceneController {

    @FXML protected void handleLoginButtonAction(ActionEvent event) throws IOException {
        handleButtonAction(event, SceneController.HOME_INTERFACE);
    }
}