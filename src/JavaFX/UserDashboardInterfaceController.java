package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Interface controller handling the user dashboard.
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @ version 1.0
 */
public class UserDashboardInterfaceController extends SceneController {

	/**
	 * Handles the action of clicking the button to return to the home interface
	 * @param event the event triggered by clicking the button
	 * @throws Exception thrown if no such interface exists
	 */
    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws Exception {
        handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
    }
}
