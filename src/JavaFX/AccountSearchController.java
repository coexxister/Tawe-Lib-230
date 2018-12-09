package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Performs User search by User ID.
 */
public class AccountSearchController extends SceneController {

    @FXML
    private TextField accountName;
    public static int userID;

    /**
     * Takes in a User ID and loads the Resource Flow Interface for the specific User.
     *
     * @param event Represents the data of the button pressed.
     * @throws IOException Thrown if a non-numeric value is inputted.
     */
    @FXML
    private void handleAccountSearchButtonAction(ActionEvent event) throws IOException {

        try {
            //get entered user id
            userID = Integer.parseInt(accountName.getText());

            //change subscene.
            if (getAccountManager().isExist(userID)) {
                //set the user id in resourceflowmanager to select a user for operations.
                getResourceFlowManager().setUserID(userID);
                loadSubscene(SceneController.getResourceFlowInterface());
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
