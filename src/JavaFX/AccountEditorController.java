package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
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

        setAvatar(true);
    }

    /**
     * Assigns the avatar selected to a specific user.
     *
     * @param isCustom Boolean that's true if it's image is custom.
     */
    private void setAvatar(final boolean isCustom) {
        try {

            //Get the user account.
            User account = getAccountManager().getAccount(id);

            //Make sure the image path uses forward slash.
            String path = selectedPath.toString();
            JOptionPane.showMessageDialog(null, "Avatar Set", "Avatar Set",
                    JOptionPane.INFORMATION_MESSAGE);
            path = path.replace("\\", "/");

            //Create a relative path rather than absolute if custom image selected.
            if (isCustom) {
                //the number of characters in 'src' to increase the index by.
                final int LENGTH_OF_SRC = 3;
                path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
            }

            int avatarID;

            //Attempt to get the avatar image id, if does not exist then add the image.
            try {
                avatarID = getResourceManager().getImageID(path);
            } catch (IllegalArgumentException e) {
                avatarID = getResourceManager().addAvatarImage(path);
            }

            //Replace accounts image id.
            account.setAvatarID(avatarID);
            //Parse in edited account.
            getAccountManager().editAccount(account);

        } catch (SQLException e) {
            e.printStackTrace();
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
            User account = getAccountManager().getAccount(id);
            Boolean isEdited = false;
            if (!(firstName.getLength() == 0)) {
                account.setFirstName(firstName.getText());
                isEdited = true;
            }
            if (!(surname.getLength() == 0)) {
                account.setLastName(surname.getText());
                isEdited = true;
            }
            if (!(streetName.getLength() == 0)) {
                account.setStreetName(streetName.getText());
                isEdited = true;
            }
            if (!(streetNumber.getLength() == 0)) {
                account.setStreetNum(streetNumber.getText());
                isEdited = true;
            }
            if (!(city.getLength() == 0)) {
                account.setCity(city.getText());
                isEdited = true;
            }
            if (!(county.getLength() == 0)) {
                account.setCounty(county.getText());
                isEdited = true;
            }
            if (!(postCode.getLength() == 0)) {
                account.setPostCode(postCode.getText());
                isEdited = true;
            }
            if (!(phoneNumber.getLength() == 0)) {
                account.setTelNum(phoneNumber.getText());
                isEdited = true;
            }

            if (isEdited) {
                getAccountManager().editAccount(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancels all changes and returns back to Resource Flow Interface.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleCancelAction(ActionEvent event) {
        loadSubscene(SceneController.getResourceFlowInterface());
    }

    @FXML
    public void setAvatar(){

    }

    /**
     * Initialises the interface to display the current details of the user in the text fields
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            User account = getAccountManager().getAccount(id);
            firstName.setText(account.getFirstName());
            surname.setText(account.getLastName());
            streetName.setText(account.getStreetName());
            streetNumber.setText(account.getStreetNum());
            city.setText(account.getCity());
            county.setText(account.getCounty());
            postCode.setText(account.getPostCode());
            phoneNumber.setText(account.getTelNum());
//    avatar.setImage();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
