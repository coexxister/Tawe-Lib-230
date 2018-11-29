package JavaFX;

import Core.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginInterfaceController extends SceneController {

    @FXML
    private TextField loginUsername;

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws Exception {
        AuthenticationManager login = new AuthenticationManager(loginUsername.getText(), getDatabase());
        if(login.authenticate()){
            if(login.isStaff()){
                handleLoginButtonAction(event, SceneController.STAFF_INTERFACE, loginUsername.getText());
            }
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        }
    }
}