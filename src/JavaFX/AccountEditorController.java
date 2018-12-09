package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Handles editing data of an existing user in the database.
 */
public class AccountEditorController extends ResourceController implements Initializable {

    private int id = getResourceFlowManager().getUserID();
    private String path;


    @FXML
    private TextField firstName;

    @FXML
    private TextField surname;

    @FXML
    private TextField streetName;

    @FXML
    private TextField streetNumber;

    @FXML
    private TextField city;

    @FXML
    private TextField county;

    @FXML
    private TextField postCode;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField balance;

    @FXML
    private ImageView avatar;

    @FXML
    private void handleSetAvatarButtonAction(ActionEvent event) {
        path = setAvatar(event);
        avatar.setImage(new Image(path));
    }

    /**
     * Saves all details set in text fields to respective variables,
     * to change the values in the database.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSaveAction(ActionEvent event) {
        try {
            //get the account
            User account = getAccountManager().getAccount(id);
            Boolean isEdited = false;
            if (!(firstName.getText().isEmpty())) {
                account.setFirstName(firstName.getText());
                isEdited = true;
            }
            if (!(surname.getText().isEmpty())) {
                account.setLastName(surname.getText());
                isEdited = true;
            }
            if (!(streetName.getText().isEmpty())) {
                account.setStreetName(streetName.getText());
                isEdited = true;
            }
            if (!(streetNumber.getText().isEmpty())) {
                account.setStreetNum(streetNumber.getText());
                isEdited = true;
            }
            if (!(city.getText().isEmpty())) {
                account.setCity(city.getText());
                isEdited = true;
            }
            if (!(county.getText().isEmpty())) {
                account.setCounty(county.getText());
                isEdited = true;
            }
            if (!(postCode.getText().isEmpty())) {
                account.setPostCode(postCode.getText());
                isEdited = true;
            }
            if (!(phoneNumber.getText().isEmpty())) {
                account.setTelNum(phoneNumber.getText());
                isEdited = true;
            }

            account.setAvatarID(getResourceManager().getImageID(path));

            if (isEdited) {
                getAccountManager().editAccount(account);
            }
            //edit the account
            getAccountManager().editAccount(account);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Save success.");
            alert.showAndWait();

            goBack();

        } catch (SQLException e) {
            //sql error in database.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error in database!");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            //image specified does not exist.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Image specified must already exist!");
            alert.showAndWait();
        }
    }

    /**
     * Cancels all changes and returns back to Resource Flow Interface.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleCancelAction(ActionEvent event) {
        goBack();
    }

    /**
     * Changes the scene to the resource flow interface.
     */
    private void goBack() {
        loadSubscene(SceneController.getResourceFlowInterface());
    }

    /**
     * Initialises the interface to display the current details of the user in the text fields
     *
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User account = getAccountManager().getAccount(id);
        firstName.setText(account.getFirstName());
        surname.setText(account.getLastName());
        streetName.setText(account.getStreetName());
        streetNumber.setText(account.getStreetNum());
        city.setText(account.getCity());
        county.setText(account.getCounty());
        postCode.setText(account.getPostCode());
        phoneNumber.setText(account.getTelNum());
        try {
            path = getResourceManager().getImageURL(account.getAvatarID());
            avatar.setImage(new Image(path));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Couldn't load an avatar.");
            alert.showAndWait();
        }
    }
}
