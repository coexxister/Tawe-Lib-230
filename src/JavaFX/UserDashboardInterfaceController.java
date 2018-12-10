package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Displays the dashboard of the current user.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class UserDashboardInterfaceController extends SceneController {

	/**
	 * Button to return back to the homepage.
	 */
	@FXML
	private Button homeButton;

	/**
	 * A label that displays the current balance of the user.
	 */
	@FXML
	private Label currentBalance;

	/**
	 * Returns user to the home scene.
	 *
	 * @param event The event triggered by clicking the button.
	 */
	@FXML
	private void handleHomeButtonAction(ActionEvent event) {
		if (homeButton.getText().contains("Home")) {
			handleSceneChangeButtonAction(event, SceneController.getHomeInterface());
		} else {
			handleSceneChangeButtonAction(event, SceneController.getUserDashboardInterface());
		}
	}

	/**
	 * Displays Profile Image Selector subscene.
	 */
	@FXML
	private void handleAvatarChangeButtonAction() {
		loadSubscene(SceneController.getAvatarChangeInterface());
		changeDashboardButton();
	}

	/**
	 * Displays Transaction History subscene.
	 */
	@FXML
	private void handleTransactionHistoryAction() {
		loadSubscene(SceneController.getTransactionHistoryInterface());
		changeDashboardButton();
	}

	/**
	 * Displays Loan History subscene.
	 */
	@FXML
	private void handleLoanAction() {
		loadSubscene(SceneController.getLoanHistoryController());
		changeDashboardButton();
	}

	/**
	 * Displays Reserve History subscene.
	 */
	@FXML
	private void handleReservedAction() {
		loadSubscene(SceneController.getReserveHistoryController());
		changeDashboardButton();
	}

	/**
	 * Changes home button text to 'Dashboard'.
	 */
	@FXML
	private void changeDashboardButton() {
		homeButton.setText("Dashboard");
	}

	/**
	 * Displays Requested Resources subscene.
	 */
	@FXML
	private void handleRequestedResourceAction() {
		loadSubscene(SceneController.getRequestedResource());
		changeDashboardButton();
	}

	/**
	 * Displays Items Due subscene.
	 */
	@FXML
	private void handleItemsDueAction() {
		loadSubscene(SceneController.getItemsDue());
		changeDashboardButton();
	}
}
