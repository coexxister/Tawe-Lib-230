package JavaFX;

import Core.LoanEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class CopyLogInterfaceController extends SceneController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField textCopy;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleSearchAction(ActionEvent event) {

        try {

            String text = textCopy.getText();
            int copyID = Integer.parseInt(text);

            setScrollPane(copyID);


        } catch (NumberFormatException n) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error in Database");
            alert.showAndWait();

        }

    }

    private void setScrollPane(int copyID) {

        //Get loan events.
        LoanEvent[] loanEvents = getResourceFlowManager().getBorrowHistoryByCopy(copyID);

        //Create a container for the labels,
        VBox vbox = new VBox();

        //for every loan event create the label and add to vbox.
        for (int iCount = 0; iCount < loanEvents.length; iCount++) {
            Label label = new Label(loanEvents[iCount].toString());
            //styling for the button.
            label.getStylesheets().add("/Resources/CoreStyle.css");
            label.getStyleClass().add("UniversalButton");
        }

        //wrap the vbox in a scroll pane.
        ScrollPane scrollPane = new ScrollPane(vbox);

        //wrap the scroll pane in a border pane.
        borderPane.setCenter(scrollPane);

    }

}