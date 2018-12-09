package JavaFX;

import Core.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Interface controller for the login screen
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class LoginInterfaceController extends SceneController{

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginUsername;

    /**
     * Handles the action of clicking the button to login to the user or staff interface
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IllegalArgumentException, Exception {
        AuthenticationManager login = new AuthenticationManager(loginUsername.getText(), getDatabase());
        SceneController.USER_ID = Integer.parseInt(loginUsername.getText());
        getResourceFlowManager().setUserID(SceneController.USER_ID);

        if(login.authenticate()){
            if(login.isStaff()){
                handleSceneChangeButtonAction(event, SceneController.STAFF_INTERFACE);
            } else {
                handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
            }

        } else {
            throw new IllegalArgumentException("Invalid user id");
        }
    }

    /**
     * When 'Enter' button is pressed, executes the action of the Login Button.
     * @param event Represents the data of the button pressed.
     * @throws Exception Thrown if input is null.
     */
    @FXML
    protected void onEnter(ActionEvent event) throws Exception {
        handleLoginButtonAction(event);
    }
}