package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Handles editing data of an existing user in the database.
 */
public class AccountEditorController extends SceneController implements Initializable {

    private int id = getResourceFlowManager().getUserID();

    private Path selectedPath;
    private int selectImageID;

    private FileChooser avatarChooser = new FileChooser();

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

    /**
     * Prompts with dialog to select and set a custom avatar.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    private void selectCustomAvatar(ActionEvent event) {

        avatarChooser.setInitialDirectory(new File("src/DefaultAvatars"));
        Node node = (Node) event.getSource();
        File file = avatarChooser.showOpenDialog(node.getScene().getWindow());
        selectedPath = Paths.get(file.getAbsolutePath());

        setAvatar();
    }

    /**
     * Assigns the avatar selected to a specific user.
     */
    private void setAvatar() {
        try {

            //Make sure the image path uses forward slash.
            String path = selectedPath.toString();
            path = path.replace("\\", "/");

                //the number of characters in 'src' to increase the index by.
                final int LENGTH_OF_SRC = 3;
                path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);

            //Attempt to get the avatar image id, if does not exist then add the image.
            selectImageID = getResourceManager().getImageID(path);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error in database!");
            alert.showAndWait();
        }
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

            //set the new values
            account.setFirstName(firstName.getText());
            account.setLastName(surname.getText());
            account.setStreetName(streetName.getText());
            account.setStreetNum(streetNumber.getText());
            account.setCity(city.getText());
            account.setCounty(county.getText());
            account.setPostCode(postCode.getText());
            account.setTelNum(phoneNumber.getText());
            account.setAvatarID(selectImageID);

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
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //get account
            User account = getAccountManager().getAccount(id);

            //populate variables
            firstName.setText(account.getFirstName());
            surname.setText(account.getLastName());
            streetName.setText(account.getStreetName());
            streetNumber.setText(account.getStreetNum());
            city.setText(account.getCity());
            county.setText(account.getCounty());
            postCode.setText(account.getPostCode());
            phoneNumber.setText(account.getTelNum());
            selectImageID = account.getAvatarID();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error resource identifier specified doesn't exist!");
            alert.showAndWait();

            goBack();
        }
    }
}
