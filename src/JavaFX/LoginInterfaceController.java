package JavaFX;

import Core.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginInterfaceController extends SceneController {

    @FXML
    private TextField loginUsername;
    public static int username;

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws Exception {
        AuthenticationManager login = new AuthenticationManager(loginUsername.getText(), getDatabase());
        username = Integer.parseInt(loginUsername.getText());
        if(login.authenticate()){
            if(login.isStaff()){
                handleLoginButtonAction(event, SceneController.STAFF_INTERFACE, loginUsername.getText());
            }
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        }
    }
}