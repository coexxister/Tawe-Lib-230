package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserDashboardInterfaceController extends SceneController {

    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws Exception {
        handleButtonAction(event, SceneController.HOME_INTERFACE);
    }
}
