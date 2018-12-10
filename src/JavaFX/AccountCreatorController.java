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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the account creator interface.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AccountCreatorController extends ResourceController implements Initializable {

    /**
     * file path for the avatar image.
     */
    private String path;

    /**
     * TextField for the first name of a new account.
     */
    @FXML
    private TextField firstName;
    /**
     * TextField for the surname of a new account.
     */
    @FXML
    private TextField surname;
    /**
     * TextField for the street name of a new account's address.
     */
    @FXML
    private TextField streetName;
    /**
     * TextField for the house number of a new account's address.
     */
    @FXML
    private TextField streetNumber;
    /**
     * TextField for the city of a new account's address.
     */
    @FXML
    private TextField city;
    /**
     * TextField for the county of a new account's address.
     */
    @FXML
    private TextField county;
    /**
     * TextField for the post code of a new account's address.
     */
    @FXML
    private TextField postCode;
    /**
     * TextField for the phone number of a new account.
     */
    @FXML
    private TextField phoneNumber;
    /**
     * TextField for the balance of a new account.
     */
    @FXML
    private TextField balance;

    /**
     * avatar image displayed on the avatar selection button.
     */
    @FXML
    private ImageView avatar;

    /**
     * Radio button to set created account as a user.
     */
    @FXML
    private RadioButton user;
    /**
     * Radio button to set created account as a staff.
     */
    @FXML
    private RadioButton staff;
    /**
     * ID of the user's avatar.
     */
    private int avatarID = 0;

    /**
     * Adds a user or staff member to the database based on the information entered in the text fields on the interface.
     *
     * @param event the event triggered by clicking the button.
     */
    public void handleCreateUserButtonAction(ActionEvent event) {
        consentPopUp(event);
        int userID;
        //if the radio button for user is selected, add the details of a user to the database.
        if (user.isSelected()) {
            User newAccount = new User(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    avatarID);
			/*
			try to set the user's avatar, add them to the database and then set their balance. If adding user to
			 database fails, output an error.
			 */
            try {
                newAccount.setAvatarID(getResourceManager().getImageID(path));
                userID = getAccountManager().addAccount(newAccount);
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch (SQLException e) {
                System.out.println("Invalid user in the database.");
            }
            //if the radio button for staff is selected, add the details of a user as a staff member to the database.
        } else if (staff.isSelected()) {
            Staff newAccount = new Staff(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    DateManager.returnCurrentDate(), 0, avatarID);
			/*
			try to set the user's avatar, add them to the database and then set their balance. If adding user to
			 database fails, output an error.
			 */
            try {
                newAccount.setAvatarID(getResourceManager().getImageID(path));
                userID = getAccountManager().addAccount(newAccount)[0];
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch (SQLException e) {
                System.out.println("Invalid user in the database.");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("User added.");
        alert.showAndWait();
        loadSubscene(getResourceInterface());
    }

    /**
     * Sets the avatar for the user.
     *
     * @param event the event triggered by clicking the button.
     */
    public void handleSetAvatarButtonAction(ActionEvent event) {
        //set the image on the button to set the avatar
        path = setAvatar(event);
        avatar.setImage(new Image(path));
    }

    /**
     * initialises the values of the avatarID and the file path and the image on the button to set the avatar
     *
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		/*
		try to set the values of avatarID and path and the image of avatar. If getting the avatarID fails,
		output an error.
		*/
		try {
			String DEFAULT_URL = "/DefaultAvatars/Avatar1.png";
			avatarID = getResourceManager().getImageID(DEFAULT_URL);
			path = getResourceManager().getImageURL(avatarID);
			avatar.setImage(new Image(getResourceManager().getImageURL(avatarID)));
		} catch (SQLException e) {
			System.out.println("Default avatarID is invalid.");
		}
	}
}
