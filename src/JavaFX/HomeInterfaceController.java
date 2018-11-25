package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeInterfaceController extends SceneController {
    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        handleButtonAction(event, SceneController.DASHBOARD_INTERFACE);
    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        handleButtonAction(event, SceneController.MAIN_INTERFACE);
    }
}
