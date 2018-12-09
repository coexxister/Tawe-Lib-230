package JavaFX;

import Core.DateManager;
import Core.Staff;
import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the account creator interface
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AccountCreatorController extends SceneController implements Initializable {
    FileChooser avatarChooser = new FileChooser();
    private Path selectedPath;



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
    private RadioButton user;
    @FXML
    private RadioButton staff;

    private final String DEFAULT_URL = "/DefaultAvatars/Avatar1.png";
    private int avatarID = 0;

    /**
     * Adds a user or staff member to the database based on the information entered in the text fields on the interface
     *
     * @param event the event triggered by clicking the button
     */
    public void handleCreateUserButtonAction(ActionEvent event) {
        int userID;
        if (user.isSelected()) {
            User newAccount = new User(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    avatarID);
            try {
                userID = getAccountManager().addAccount(newAccount);
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (staff.isSelected()) {
            Staff newAccount = new Staff(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    DateManager.returnCurrentDate(), 0, avatarID);
            try {
                userID = getAccountManager().addAccount(newAccount)[0];
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the avatar for the user
     *
     * @param event the event triggered by clicking the button
     */
    public void handleSetAvatarButtonAction(ActionEvent event) {
        avatarID = setAvatar();
    }

    private int setAvatar() {
        try {
            //Make sure the image path uses forward slash
            String path = selectedPath.toString();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Avatar Set");
            alert.showAndWait();
            path = path.replace("\\", "/");

            int avatarID;

            //Attempt to get the avatar image id
            try {
                avatarID = getResourceManager().getImageID(path);
            } catch (IllegalArgumentException e) {
                System.out.println("No avatar exists at that path.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avatarID;
    }

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarID = getResourceManager().getImageID(DEFAULT_URL);
        } catch(SQLException e) {
            System.out.println("Default avatarID is invalid.");
        }
    }
}
