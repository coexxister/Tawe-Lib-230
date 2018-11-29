package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeInterfaceController extends SceneController {

    @FXML
    public Button logoutButton;

    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        if(logoutButton.getText().equals("Logout")) {
            handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
        } else {
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        }
    }

    @FXML
    public void handleBookMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("BookSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleDVDMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("DVDSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleLaptopMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("LaptopSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void changeLogoutToHome(Button logoutButton){
        logoutButton.setText("Home");
    }
}
