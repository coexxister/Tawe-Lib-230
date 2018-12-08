package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AccountSearchController extends SceneController {

    @FXML
    private TextField accountName;
    public static int userID;

    @FXML
    private void handleAccountSearchButtonAction(ActionEvent event) throws IOException {

        try {
            //get entered user id
            userID = Integer.parseInt(accountName.getText());

            //change subscene.
            if (getAccountManager().isExist(userID)) {
                //set the user id in resourceflowmanager to select a user for operations.
                getResourceFlowManager().setUserID(userID);
                loadSubscene(SceneController.RESOURCE_FLOW_INTERFACE);
            } else {
                //create alert that user does not exist.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Specified user does not exist.");
                alert.showAndWait();
            }
        } catch (NumberFormatException n) {

            //create alert if non integer is typed.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Must be a numeric value");
            alert.showAndWait();

        }
    }
}
