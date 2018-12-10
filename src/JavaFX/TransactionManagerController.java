package JavaFX;

import Core.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Handles transactions for paying fines and updating balance.
 *
 * @author Noah Lenagan
 * @version 1.0
 */
public class TransactionManagerController extends SceneController implements Initializable {

    /**
     * The background pane for the scene.
     */
    @FXML
    private BorderPane borderPane;
    /**
     * The textfield to change the balance of the user of the transaction.
     */
    @FXML
    private TextField balanceChangeText;
    /**
     * label for the current balance of the user.
     */
    @FXML
    Label currentBalance;

    /**
     * intialiszes the connection to the resources.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    public void initialize(URL location, ResourceBundle resources) {

        //get user id
        int userID = getResourceFlowManager().getUserID();

        borderPane.setRight(constructTransactionHistory(userID));

        updateBalance();

    }

    /**
     * Performs calculations and sets the balance for the user.
     *
     * @param event Represents the data of the button pressed.
     * @throws IOException Thrown when input is null.
     */
    @FXML
    private void handleSubmitAction(ActionEvent event) throws IOException {

        try {

            //Get the balance change.
            float change = Float.parseFloat(balanceChangeText.getText());

            //Change balance
            getAccountManager().changeBalance(getResourceFlowManager().getUserID(), change);

            //update history
            borderPane.setRight(constructTransactionHistory(getResourceFlowManager().getUserID()));

            updateBalance();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to change balance: Database Error");
            alert.showAndWait();
        }


    }

    /**
     * Updates the balance label.
     */
    private void updateBalance() {

        float balance;
        try {
            //get balance and round to 2 decimal places.
            balance = Math.round(getAccountManager().getAccountBalance(getResourceFlowManager().getUserID()) *
                    100.0F) / 100.0F;
        } catch (SQLException e) {
            balance = 0.0F;
        }

        currentBalance.setText("£" + Float.toString(balance));

        //if the balance is less than 0, then change background color to red. Otherwise change to green.
        if (balance < 0) {
            currentBalance.setStyle("-fx-background-color: #ff644e; -fx-text-fill: WHITE; -fx-padding: 5px;");
        } else {
            currentBalance.setStyle("-fx-background-color: #228022; -fx-text-fill: WHITE; -fx-padding: 5px;");
        }

    }

    /**
     * Constructs a scroll pane of transaction history for the user.
     *
     * @param userID The user id of the user.
     * @return The scroll pane containing transaction history.
     */
    private ScrollPane constructTransactionHistory(int userID) {
        //create scroll pane to list transaction history
        ScrollPane scrollPane = new ScrollPane();

        //Add styling to scroll pane.
        scrollPane.getStylesheets().add("/Resources/CoreStyle.css");
        scrollPane.getStyleClass().add("UniversalButton");

        //Disable horizontal scrollbar
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        //get all of the transactions
        Transaction[] transactions = getAccountManager().getTransactionHistory(userID);
        BorderPane[] transacPanes = new BorderPane[transactions.length];

        //for every transaction add to scroll pane
        for (int iCount = 0; iCount < transactions.length; iCount++) {
            Label textLabel = new Label(transactions[(transactions.length - 1) - iCount].toString());

            transacPanes[iCount] = new BorderPane();
            transacPanes[iCount].setCenter(textLabel);
            transacPanes[iCount].setPrefWidth(300);

            if (transactions[(transactions.length - 1) - iCount].getChange() < 0) {
                textLabel.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE; -fx-padding: 20px;");
            } else {
                textLabel.setStyle("-fx-background-color: #2acb5a; -fx-text-fill: WHITE; -fx-padding: 20px;");
            }
        }

        //wrap all transaction border panes into a flow pane
        VBox vBox = new VBox();
        vBox.getChildren().addAll(transacPanes);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        //wrap flow pane into a scroll pane
        scrollPane.setContent(vBox);

        return scrollPane;

    }

}
