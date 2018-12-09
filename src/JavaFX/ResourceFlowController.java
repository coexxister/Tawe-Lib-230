package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Handles user specific operations regarding resources.
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class ResourceFlowController extends SceneController implements Initializable {

    @FXML
    private ImageView profileImage;

    @FXML
    private Text textName;

    @FXML
    private TextField textIDField;

    private int selectedCopyID = -1;

    /**
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
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

    /**
     * Displays Transaction Manager subscene.
     *
     * @param event the event of clicking the button.
     */
    @FXML
    private void handleTransactionAction(ActionEvent event) {
        loadSubscene(SceneController.getTransactionManagerInterface());
    }

    /**
     * Displays Loan History subscene.
     *
     * @param event the event of clicking the loan history option.
     */
    @FXML
    private void handleViewLoanHistoryAction(ActionEvent event) {
        loadSubscene(SceneController.getLoanHistoryController());
    }

    /**
     * Displays Items Due subscene.
     *
     * @param event the event triggered by clicking the due items button.
     */
    @FXML
    private void handleDueItemsAction(ActionEvent event) {
        loadSubscene(SceneController.getItemsDue());
    }

    /**
     * Displays Reserve History subscene.
     *
     * @param event the event triggered by selecting the reserved resources option.
     */
    @FXML
    private void handleReservedResourcesAction(ActionEvent event) {
        loadSubscene(SceneController.getReserveHistoryController());
    }

    /**
     * Displays Account Editor subscene.
     *
     * @param event the event triggered by clicking the button.
     */
    @FXML
    private void handleEditAccountAction(ActionEvent event) {
        loadSubscene(SceneController.getAccountEditorInterface());
    }

    /**
     * Takes the entered user id, validates it and marks it as the selected id in which operation will be
     * performed upon.
     *
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

    /**
     * Borrows a copy of specified resource to selected user.
     *
     * @param event The action event.
     */
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

    /**
     * Returns a copy for the specific user.
     *
     * @param event The action event.
     */
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

    /**
     * Unreserves a resource for the specified user.
     *
     * @param event The action event.
     */
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
