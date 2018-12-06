package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class UserDashboardInterfaceController extends SceneController {

    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
    }

    @FXML
    private void handleAvatarChangeButtonAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.AVATAR_CHANGE_INTERFACE);
    }

    @FXML
    private void handleTransactionHistoryAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.TRANSACTION_HISTORY_INTERFACE);
    }

    @FXML
    private void handleLoanAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.LOAN_HISTORY_CONTROLLER);
    }
}