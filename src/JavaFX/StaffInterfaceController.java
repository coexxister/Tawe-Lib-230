package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StaffInterfaceController extends SceneController {
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
}