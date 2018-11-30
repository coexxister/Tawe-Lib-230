package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeInterfaceController extends SceneController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameDisplay;

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
        loadSubscene("/View/BookSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleDVDMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/DVDSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleLaptopMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/LaptopSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleListAllButtonAction(ActionEvent event) throws IOException{
        loadSubscene("/View/ListAll.fxml");
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void changeLogoutToHome(Button logoutButton){
        logoutButton.setText("Home");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().getAccount(LoginInterfaceController.username).getFirstName());
    }
}
