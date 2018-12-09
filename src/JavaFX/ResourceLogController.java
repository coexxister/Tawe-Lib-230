package JavaFX;

import Core.Copy;
import Core.LoanEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ResourceLogController extends SceneController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField textResourceID;

    @FXML
    private RadioButton allCopiesRadio;

    @FXML
    private RadioButton loanHistoryRadio;

    @FXML
    private RadioButton overdueCopies;

    private ToggleGroup radioGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add radio buttons to group.
        radioGroup = new ToggleGroup();

        allCopiesRadio.setToggleGroup(radioGroup);
        loanHistoryRadio.setToggleGroup(radioGroup);
        overdueCopies.setToggleGroup(radioGroup);

        radioGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> System.out.println(newVal
                + " was selected"));
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {

        try {

            int resourceID = Integer.parseInt(textResourceID.getText());

            //if the get all copies radio button has been selected then list all copies.
            if (radioGroup.getSelectedToggle() == allCopiesRadio) {
                listAllCopies(resourceID);

            } else if (radioGroup.getSelectedToggle() == loanHistoryRadio) {
                //if all loan history selected then list all loan history.
                listloanHistoryRadio(resourceID);

            } else if (overdueCopies.getToggleGroup() == radioGroup) {
                //if all loan history selected then list all loan history.
                listOverdueCopies(resourceID);
            } else {
                //no radio button selected!
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please select an option!");
                alert.showAndWait();
            }

        } catch (NumberFormatException n) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a numerical resource identifier.");
            alert.showAndWait();

        }

    }

    private void listAllCopies(int resourceID) {

        //the copies to list
        Copy[] resourceCopies = getResourceManager().getCopies(resourceID);

        //wraps labels in a vbox
        VBox vBox = new VBox();

        //for every copy, add the toString to the label.
        for (int iCount = 0; iCount < resourceCopies.length; iCount++) {
            //create label and style
            Label copyLabel = new Label(resourceCopies[iCount].toString());
            copyLabel.setMinWidth(300);
            copyLabel.getStylesheets().add("/Resources/CoreStyle.css");
            copyLabel.getStyleClass().add("UniversalButton");

            vBox.getChildren().add(copyLabel);
        }

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        scrollPane.setContent(vBox);

    }

    private void listloanHistoryRadio(int resourceID) {

        //the copies to list
        LoanEvent[] loanHistory = getResourceFlowManager().
                getBorrowHistoryByResource(resourceID);

        //wraps labels in a vbox
        VBox vBox = new VBox();

        //for every loan history, add to label.
        for (int iCount = 0; iCount < loanHistory.length; iCount++) {
            //create label and style
            Label copyLabel = new Label(loanHistory[iCount].toString());
            copyLabel.setMinWidth(300);
            copyLabel.getStylesheets().add("/Resources/CoreStyle.css");
            copyLabel.getStyleClass().add("UniversalButton");

            //add label to vbox.
            vBox.getChildren().add(copyLabel);
        }

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        scrollPane.setContent(vBox);

    }

    private void listOverdueCopies(int resourceID) {

        //the copies to list
        Copy[] resourceCopies = getResourceFlowManager().showOverdueCopies(resourceID);

        //wraps labels in a vbox
        VBox vBox = new VBox();

        //for every copy, add the toString to the label.
        for (int iCount = 0; iCount < resourceCopies.length; iCount++) {
            //create label and style
            Label copyLabel = new Label(resourceCopies[iCount].toString());
            copyLabel.setMinWidth(300);
            copyLabel.getStylesheets().add("/Resources/CoreStyle.css");
            copyLabel.getStyleClass().add("UniversalButton");

            vBox.getChildren().add(copyLabel);
        }

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        scrollPane.setContent(vBox);

    }

}
