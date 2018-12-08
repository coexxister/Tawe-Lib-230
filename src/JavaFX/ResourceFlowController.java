package JavaFX;

import Core.Copy;
import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResourceFlowController extends SceneController implements Initializable {

    @FXML
    private ImageView profileImage;

    @FXML
    private Text textName;

    @FXML
    private TextField textIDField;

    private int selectedCopyID = -1;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Set the users profile image.
            int id = getResourceFlowManager().getUserID();
            User user = getAccountManager().getAccount(id);

            //set profile image
            int imageID = user.getAvatarID();
            String imageURL = getResourceManager().getImageURL(imageID);
            profileImage.setImage(new Image(imageURL));

            //set name
            String name = user.getFirstName() + " " + user.getLastName();
            textName.setText(name);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("User has an undefined profile image.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleTransactionAction(ActionEvent event) {
        loadSubscene(SceneController.TRANSACTION_MANAGER_INTERFACE);
    }

    @FXML
    private void handleViewLoanHistoryAction(ActionEvent event) {
        loadSubscene(SceneController.LOAN_HISTORY_CONTROLLER);
    }

    @FXML
    private void handleDueItemsAction(ActionEvent event) {
        loadSubscene(SceneController.ITEMS_DUE);
    }

    @FXML
    private void handleReservedResourcesAction(ActionEvent event) {
        loadSubscene(SceneController.RESERVE_HISTORY_CONTROLLER);
    }

    @FXML
    private void handleEditAccountAction(ActionEvent event) {
        loadSubscene(SceneController.ACCOUNT_EDITOR_INTERFACE);
    }

    /**
     * Takes the entered user id, validates it and marks it as the selected id in which operation will be
     * performed upon.
     * @param event The action event.
     */
    @FXML
    private void handleSelectIDAction(ActionEvent event) {

        try {

            String textData = textIDField.getText();

            int copyID = Integer.parseInt(textData);

            if (getResourceManager().getCopy(copyID) != null) {

                selectedCopyID = copyID;
                textIDField.setStyle("-fx-background-color: #2acb5a; -fx-text-fill: WHITE;");

            } else {
                textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Specified copy does not exist!");
                alert.showAndWait();
            }

        } catch (NumberFormatException n) {
            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Non numeric value entered!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBorrowCopyAction(ActionEvent event) {

        try {
            if (selectedCopyID != -1) {

                getResourceFlowManager().borrowCopy(selectedCopyID, getResourceFlowManager().getUserID());

                textIDField.setStyle("-fx-background-color: #2acb5a; -fx-text-fill: WHITE;");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully borrowed the copy: " + selectedCopyID);
                alert.showAndWait();

                selectedCopyID = -1;

            } else {
                textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No copy selected!");
                alert.showAndWait();
            }
        } catch (SQLException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Borrow Failed: Error in the Database!");
            alert.showAndWait();

        } catch (IllegalStateException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } finally {
            textIDField.setText("");
        }
    }

    @FXML
    private void handleReturnCopyAction(ActionEvent event) {

        try {
            if (selectedCopyID != -1) {

                getResourceFlowManager().returnCopy(selectedCopyID, getResourceFlowManager().getUserID());

                textIDField.setStyle("-fx-background-color: #2acb5a; -fx-text-fill: WHITE;");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully return the copy: " + selectedCopyID);
                alert.showAndWait();

                selectedCopyID = -1;

            } else {
                textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No copy selected!");
                alert.showAndWait();
            }
        } catch (SQLException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Return Failed: Error in the Database!");
            alert.showAndWait();

        } catch (IllegalStateException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } finally {
            textIDField.setText("");
        }
    }

    @FXML
    private void handleUnReserveAction(ActionEvent event) {

        try {
            if (selectedCopyID != -1) {

                getResourceFlowManager().unreserveCopy(selectedCopyID, getResourceFlowManager().getUserID());
                textIDField.setStyle("-fx-background-color: #2acb5a; -fx-text-fill: WHITE;");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully unreserved the copy: " + selectedCopyID);
                alert.showAndWait();

                selectedCopyID = -1;

            } else {
                textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No copy selected!");
                alert.showAndWait();
            }
        } catch (SQLException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unreserve Failed: Error in the Database!");
            alert.showAndWait();

        } catch (IllegalStateException e) {

            textIDField.setStyle("-fx-background-color: #ff5b5f; -fx-text-fill: WHITE;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } finally {
            textIDField.setText("");
        }
    }



}
