package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDashboardInterfaceController extends SceneController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Label currentBalance;

    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        if(homeButton.getText().contains("Home")) {
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        } else {
            handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
        }
    }

    @FXML
    private void handleAvatarChangeButtonAction(ActionEvent event) {
        loadSubscene(SceneController.AVATAR_CHANGE_INTERFACE);
        changeDashboardButton();
    }

    @FXML
    private void handleTransactionHistoryAction(ActionEvent event) {
        loadSubscene(SceneController.TRANSACTION_HISTORY_INTERFACE);
        changeDashboardButton();
    }

    @FXML
    private void handleLoanAction(ActionEvent event) {
        loadSubscene(SceneController.LOAN_HISTORY_CONTROLLER);
        changeDashboardButton();
    }

    @FXML
    private void handleReservedAction(ActionEvent event) {
        loadSubscene(SceneController.RESERVE_HISTORY_CONTROLLER);
        changeDashboardButton();
    }

    @FXML
    private void changeDashboardButton(){
        homeButton.setText("Dashboard");
    }
    @FXML
    private void handleRequestedResourceAction(ActionEvent event) {
        loadSubscene(SceneController.REQUESTED_RESOURCE);
        changeDashboardButton();
    }

    @FXML
    private void handleItemsDueAction(ActionEvent event) {
        loadSubscene(SceneController.ITEMS_DUE);
        changeDashboardButton();
    }

    private void updateBalanceLabel() {

        float balance;
        try {
            //get balance and round to 2 decimal places.
            balance = Math.round(getAccountManager().getAccountBalance(SceneController.USER_ID)*100)/100;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateBalanceLabel();
    }

}