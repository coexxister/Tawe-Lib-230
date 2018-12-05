package JavaFX;

import Core.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Interface controller for the login screen
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class LoginInterfaceController extends SceneController {

    @FXML
    private TextField loginUsername;
    private int username;

    /**
     * Handles the action of clicking the button to login to the user or staff interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IllegalArgumentException, Exception {
        AuthenticationManager login = new AuthenticationManager(loginUsername.getText(), getDatabase());
        username = Integer.parseInt(loginUsername.getText());

        SceneController.USER_ID = username;

        if(login.authenticate()){
            if(login.isStaff()){
                handleLoginButtonAction(event, SceneController.STAFF_INTERFACE, loginUsername.getText());
            } else {
                handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
            }

        } else {
            throw new IllegalArgumentException("Invalid user id");
        }
    }
}