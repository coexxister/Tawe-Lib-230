package JavaFX;

import Core.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.text.Text;

public class TransactionHistoryController extends SceneController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox root = new VBox();

        Transaction[] transactions = getAccountManager().
                    getTransactionHistory(SceneController.USER_ID);

        Label[] transactionText = new Label[transactions.length];

        for (int iCount = 0; iCount < transactions.length; iCount++) {
            transactionText[iCount] = new Label(transactions[iCount].toString());
            transactionText[iCount].getStylesheets().add("/Resources/CoreStyle.css");
            transactionText[iCount].getStyleClass().add("ScrollListItem");
            transactionText[iCount].setMinHeight(200);
            transactionText[iCount].setPrefWidth(400);
            transactionText[iCount].setAlignment(Pos.CENTER);
        }

        root.getChildren().addAll(transactionText);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
    }

}
