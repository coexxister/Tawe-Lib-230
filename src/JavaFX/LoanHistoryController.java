package JavaFX;

import Core.LoanEvent;
import Core.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Lists all loaned items to date for the specific user.
 */
public class LoanHistoryController extends SceneController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int userID = getResourceFlowManager().getUserID();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox root = new VBox();

        LoanEvent[] loanHistory = getResourceFlowManager().getBorrowHistory(userID);

        Label[] loanText = new Label[loanHistory.length];

        //For every loan history event. Add to a label.
        for (int iCount = 0; iCount < loanHistory.length; iCount++) {
            loanText[iCount] = new Label(loanHistory[iCount].toString());
            loanText[iCount].getStylesheets().add("/Resources/CoreStyle.css");
            loanText[iCount].getStyleClass().add("ScrollListItem");
            loanText[iCount].setMinHeight(100);
            loanText[iCount].setPrefWidth(300);
            loanText[iCount].setAlignment(Pos.CENTER);
        }

        //Wrap all labels in a vBox
        root.getChildren().addAll(loanText);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        //Set the content of the scroll pane to the vBox
        scrollPane.setContent(root);

    }

}
