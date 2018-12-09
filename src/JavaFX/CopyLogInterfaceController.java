package JavaFX;

import Core.LoanEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for listing all borrow history for a select copy id.
 *
 * @author Noah Lenagan
 * @version 1.0
 */
public class CopyLogInterfaceController extends SceneController implements Initializable {

    /**
     * The border pane which contains all the content.
     */
    @FXML
    private BorderPane borderPane;

    /**
     * The text field where the copy id is entered.
     */
    @FXML
    private TextField textCopy;

    public void initialize(URL location, ResourceBundle resources) {
        //Create a blank scroll pane.
        setScrollPane(-1);
    }

    /**
     * Handles the click of the search button by constructed a scroll pane of borrow history.
     *
     * @param event The event of the click.
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {

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

    /**
     * Sets the scroll pane with the content of the resource history of the specific copy.
     *
     * @param copyID The copy id of the copy.
     */
    private void setScrollPane(int copyID) {

        //Get loan events.
        LoanEvent[] loanEvents = getResourceFlowManager().getBorrowHistoryByCopy(copyID);

        System.out.println(copyID + " " + loanEvents.length);

        if (loanEvents == null) {
            loanEvents = new LoanEvent[0];
        }

        //Create a container for the labels,
        VBox vbox = new VBox();

        //for every loan event create the label and add to vbox.
        for (int iCount = 0; iCount < loanEvents.length; iCount++) {
            Label label = new Label(loanEvents[iCount].toString());
            //styling for the button.
            label.getStylesheets().add("/Resources/CoreStyle.css");
            label.getStyleClass().add("UniversalButton");
            vbox.getChildren().add(label);

        }

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        //wrap the vbox in a scroll pane.
        ScrollPane scrollPane = new ScrollPane(vbox);

        //wrap the scroll pane in a border pane.
        borderPane.setCenter(scrollPane);

    }

}