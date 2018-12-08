package JavaFX;

import Core.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AccountSearchController extends SceneController {

    @FXML
    private TextField accountName;
    public static int userID;

    @FXML
    private void handleAccountSearchButtonAction(ActionEvent event) throws IOException {
        userID = Integer.parseInt(accountName.getText());

        //change subscene.
        if (getAccountManager().isExist(SceneController.USER_ID)) {
            //set the user id in resourceflowmanager to select a user for operations.
            getResurceFlowManager().setUserID(SceneController.USER_ID);

            loadSubscene(SceneController.RESOURCE_FLOW_INTERFACE);
        }
    }
}
