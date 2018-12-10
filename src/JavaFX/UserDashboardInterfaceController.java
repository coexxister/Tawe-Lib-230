package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
     * @param event Represents the data of the button pressed.
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
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleAvatarChangeButtonAction(ActionEvent event) {
        loadSubscene(SceneController.getAvatarChangeInterface());
        changeDashboardButton();
    }

    /**
     * Displays Transaction History subscene.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleTransactionHistoryAction(ActionEvent event) {
        loadSubscene(SceneController.getTransactionHistoryInterface());
        changeDashboardButton();
    }

    /**
     * Displays Loan History subscene.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleLoanAction(ActionEvent event) {
        loadSubscene(SceneController.getLoanHistoryController());
        changeDashboardButton();
    }

    /**
     * Displays Reserve History subscene.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleReservedAction(ActionEvent event) {
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
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleRequestedResourceAction(ActionEvent event) {
        loadSubscene(SceneController.getRequestedResource());
        changeDashboardButton();
    }

    /**
     * Displays Items Due subscene.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void handleItemsDueAction(ActionEvent event) {
        loadSubscene(SceneController.getItemsDue());
        changeDashboardButton();
    }

    /**
     * Updates Balance label.
     */
    private void updateBalanceLabel() {

        float balance;
        try {
            //get balance and round to 2 decimal places.
            balance = Math.round(getAccountManager().getAccountBalance(SceneController.USER_ID) * 100) / 100;
        } catch (SQLException e) {
            balance = 0.0F;
        }

        currentBalance.setText("£" + balance);

        //if the balance is less than 0, then change background color to red. Otherwise change to green.
        if (balance < 0) {
            currentBalance.setStyle("-fx-background-color: #ff644e; -fx-text-fill: WHITE;");
        } else {
            currentBalance.setStyle("-fx-background-color: #228022; -fx-text-fill: WHITE;");
        }
    }
}