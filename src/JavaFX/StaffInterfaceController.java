package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Interface controller for the main staff interface
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class StaffInterfaceController extends SceneController implements Initializable {

    @FXML
    private Label usernameDisplay;

    /**
     * Handles the action of clicking the button to return to the home interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
    }
    
    /**
     * Handles the action of clicking the button to change to the resources interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleResourcesButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.RESOURCE_INTERFACE);
    }
    
    /**
     * Handles the action of clicking the button to change to the accounts interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleAccountsButtonAction(ActionEvent event) throws Exception {
//        handleSceneChangeButtonAction(event, SceneController.ACCOUNTS_PAGE_INTERFACE);
    }
    
    /**
     * Handles the action of clicking the button to change to the account creator interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleAccountCreatorButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.ACCOUNT_CREATOR_INTERFACE);
    }

    /**
     * Initialises the label in the interface to display the first name of the user based on the account currently logged in
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().getAccount(SceneController.USER_ID).getFirstName());
    }
}