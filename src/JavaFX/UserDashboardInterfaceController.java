package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserDashboardInterfaceController extends SceneController {

    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
    }

    @FXML
    private void handleAvatarChangeButtonAction(ActionEvent event) throws Exception {
        loadSubscene(SceneController.AVATAR_CHANGE_INTERFACE);
    }
}