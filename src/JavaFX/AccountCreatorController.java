package JavaFX;

import Core.DateManager;
import Core.Staff;
import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the account creator interface
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AccountCreatorController extends ResourceController implements Initializable {

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
        consentPopUp(event);
        int userID;
        if (user.isSelected()) {
            User newAccount = new User(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    avatarID);
            try {
                newAccount.setAvatarID(getResourceManager().getImageID(path));
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
                newAccount.setAvatarID(getResourceManager().getImageID(path));
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
        path = setAvatar(event);
        avatar.setImage(new Image(path));
    }

    /**
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarID = getResourceManager().getImageID(DEFAULT_URL);
            path = getResourceManager().getImageURL(avatarID);
            avatar.setImage(new Image(getResourceManager().getImageURL(avatarID)));
        } catch (SQLException e) {
            System.out.println("Default avatarID is invalid.");
        }
    }
}
