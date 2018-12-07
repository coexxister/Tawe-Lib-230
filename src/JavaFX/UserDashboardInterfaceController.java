package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;

import java.io.IOException;

public class UserDashboardInterfaceController extends SceneController {

    @FXML
    private Button homeButton;

    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        if(homeButton.getText().contains("Home")) {
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        } else {
            handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
        }
    }

    @FXML
    private void handleAvatarChangeButtonAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.AVATAR_CHANGE_INTERFACE);
        changeDashboardButton();
    }

    @FXML
    private void handleTransactionHistoryAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.TRANSACTION_HISTORY_INTERFACE);
        changeDashboardButton();
    }

    @FXML
    private void handleLoanAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.LOAN_HISTORY_CONTROLLER);
        changeDashboardButton();
    }

    @FXML
    private void handleReservedAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.RESERVE_HISTORY_CONTROLLER);
        changeDashboardButton();
    }

    @FXML
    private void changeDashboardButton(){
        homeButton.setText("Dashboard");
    }
}