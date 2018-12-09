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
 *
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
     *
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) {
        if (logoutButton.getText().equals("Logout")) {
            handleSceneChangeButtonAction(event, SceneController.getMainInterface());
        } else {
            handleSceneChangeButtonAction(event, SceneController.getStaffInterface());
        }
    }

    /**
     * Handles the action of clicking the button to change to the resources interface
     *
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleResourcesButtonAction(ActionEvent event) {
        loadSubscene(SceneController.getResourceInterface());
        changeLogoutToHome(logoutButton);
    }

    /**
     * Handles the action of clicking the button to change to the accounts interface
     *
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleAccountsButtonAction(ActionEvent event) {
        loadSubscene(SceneController.getAccountsSearchInterface());
        changeLogoutToHome(logoutButton);
    }

    /**
     * Handles the action of clicking the button to change to the account creator interface
     *
     * @param event the event triggered by clicking the button
     */
    @FXML
    protected void handleAccountCreatorButtonAction(ActionEvent event) {
        loadSubscene(SceneController.getAccountCreatorInterface());
        changeLogoutToHome(logoutButton);
    }

    /**
     * Initialises the label in the interface to display the first name of the user based on the account currently logged in
     *
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().getAccount(SceneController.USER_ID).getFirstName());
    }

    @FXML
    public void handleCopiesAction(ActionEvent event) {
        loadSubscene(SceneController.getCopyLogInterface());
        changeLogoutToHome(logoutButton);
    }

    @FXML
    public void handleAllOverdueCopies(ActionEvent event) {
        loadSubscene(SceneController.getListOverDueCopies());
        changeLogoutToHome(logoutButton);
    }
}