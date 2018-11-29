package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeInterfaceController extends SceneController {

    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
    }

    @FXML
    public void handleBookMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("BookSearchMenu.fxml");
    }

    @FXML
    public void handleDVDMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("DVDSearchMenu.fxml");
    }

    @FXML
    public void handleLaptopMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("LaptopSearchMenu.fxml");
    }
}
