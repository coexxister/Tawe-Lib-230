package JavaFX;

import Core.AuthenticationManager;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

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
     * Handles the action of clicking the button to login to the user or staff interface.
     * @param event the event triggered by clicking the button.
     * @throws Exception thrown if no such interface exists.
     */
    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        AuthenticationManager login = new AuthenticationManager(loginUsername.getText(), getDatabase());
        SceneController.USER_ID = Integer.parseInt(loginUsername.getText());
        getResourceFlowManager().setUserID(SceneController.USER_ID);

        try {
            if (login.authenticate()) {
                try {
                    if (login.isStaff()) {
                        handleSceneChangeButtonAction(event, SceneController.getStaffInterface());
                    } else {
                        handleSceneChangeButtonAction(event, SceneController.getHomeInterface());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {

                throw new IllegalArgumentException("Invalid user id");
            }
        }catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid User ID");
            alert.showAndWait();
        }
    }


    /**
     * When 'Enter' button is pressed, executes the action of the Login Button.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    protected void onEnter(ActionEvent event) {
        handleLoginButtonAction(event);
    }
}