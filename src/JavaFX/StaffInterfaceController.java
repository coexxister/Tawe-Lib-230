package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Interface controller for the main staff interface
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class StaffInterfaceController extends SceneController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameDisplay;

    /**
     * Handles the action of clicking the button to return to the home interface
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) {
        if(logoutButton.getText().equals("Logout")) {
            handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
        } else {
            handleSceneChangeButtonAction(event, SceneController.STAFF_INTERFACE);
        }
    }
    
    /**
     * Handles the action of clicking the button to change to the resources interface
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleResourcesButtonAction(ActionEvent event) {
        loadSubscene(SceneController.RESOURCE_INTERFACE);
        changeLogoutToHome(logoutButton);
    }
    
    /**
     * Handles the action of clicking the button to change to the accounts interface
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleAccountsButtonAction(ActionEvent event) {
        loadSubscene(SceneController.ACCOUNTS_SEARCH_INTERFACE);
        changeLogoutToHome(logoutButton);
    }
    
    /**
     * Handles the action of clicking the button to change to the account creator interface
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleAccountCreatorButtonAction(ActionEvent event) {
        loadSubscene(SceneController.ACCOUNT_CREATOR_INTERFACE);
        changeLogoutToHome(logoutButton);
    }
    /**
     * Initialises the label in the interface to display the first name of the user based on the account currently logged in
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().getAccount(SceneController.USER_ID).getFirstName());
    }

    @FXML
    public void handleCopiesAction(ActionEvent event) {
        loadSubscene(SceneController.COPY_LOG_INTERFACE);
    }
}