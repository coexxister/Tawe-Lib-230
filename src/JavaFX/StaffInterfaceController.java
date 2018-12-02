package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffInterfaceController extends SceneController implements Initializable {

    @FXML
    private Label usernameDisplay;

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
    }
    
    @FXML
    protected void handleResourcesButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.RESOURCE_INTERFACE);
    }
    
    @FXML
    protected void handleAccountsButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.ACCOUNTS_PAGE_INTERFACE);
    }
    
    @FXML
    protected void handleAccountCreatorButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.ACCOUNT_CREATOR_INTERFACE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().getAccount(LoginInterfaceController.username).getFirstName());
    }
}