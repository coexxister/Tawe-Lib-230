package JavaFX;

import Core.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;


public class TransactionManagerController extends SceneController implements Initializable {

    @FXML
    private BorderPane borderPane;

    public void initialize(URL location, ResourceBundle resources) {

        //get user id
        int userID = getResourceFlowManager().getUserID();

        borderPane.setRight(constructTransactionHistory(userID));

    }

    /**
     * Constructs a scroll pane of transaction history for the user.
     * @param userID The user id of the user.
     * @return The scroll pane containing transaction history.
     */
    private ScrollPane constructTransactionHistory(int userID) {
        //create scroll pane to list transaction history
        ScrollPane scrollPane = new ScrollPane();


        //get all of the transactions
        Transaction[] transactions = getAccountManager().getTransactionHistory(userID);
        BorderPane[] transacPanes = new BorderPane[transactions.length];

        for (int iCount = 0; iCount < transactions.length; iCount++) {
            Label textLabel = new Label(transactions[iCount].toString());
            transacPanes[iCount] = new BorderPane();
            transacPanes[iCount].setCenter(textLabel);
            transacPanes[iCount].setPrefWidth(300);
        }

        //wrap all transaction border panes into a flow pane
        VBox vBox = new VBox();
        vBox.getChildren().addAll(transacPanes);

        //wrap flow pane into a scroll pane
        scrollPane.setContent(vBox);

        return scrollPane;

    }

}
