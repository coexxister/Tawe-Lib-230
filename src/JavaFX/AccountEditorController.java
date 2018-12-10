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
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AccountEditorController extends ResourceController implements Initializable {

	/**
	 * user ID of the user to be edited.
	 */
	private int id = getResourceFlowManager().getUserID();
	/**
	 * file path for the avatar image.
	 */
	private String path;

	/**
	 * TextField for the first name of the account.
	 */
	@FXML
	private TextField firstName;
	/**
	 * TextField for the surname of the account.
	 */
	@FXML
	private TextField surname;
	/**
	 * TextField for the street name of the account's address.
	 */
	@FXML
	private TextField streetName;
	/**
	 * TextField for the house number of the account's address.
	 */
	@FXML
	private TextField streetNumber;
	/**
	 * TextField for the city of the account's address.
	 */
	@FXML
	private TextField city;
	/**
	 * TextField for the county of the account's address.
	 */
	@FXML
	private TextField county;
	/**
	 * TextField for the post code of the account's address.
	 */
	@FXML
	private TextField postCode;
	/**
	 * TextField for the phone number of the account.
	 */
	@FXML
	private TextField phoneNumber;

	/**
	 * TextField for the balance of the account.
	 */
	@FXML
	private TextField balance;

	/**
	 * avatar image displayed on the avatar selection button.
	 */
	@FXML
	private ImageView avatar;

	/**
	 * Sets the avatar for the user.
	 *
	 * @param event the event triggered by clicking the button.
	 */
	@FXML
	private void handleSetAvatarButtonAction(ActionEvent event) {
		//set the image on the button to set the avatar
		path = setAvatar(event);
		avatar.setImage(new Image(path));
	}

	/**
	 * Saves all details set in text fields to respective variables,
	 * to change the values in the database.
	 *
	 * @param event the event triggered by clicking the button.
	 */
	@FXML
	public void handleSaveAction(ActionEvent event) {
		/*try to get the account by its ID and edit its details. If getting the account from the database fails or the
		avatar image doesn't exist, output an error.
		*/
		try {
			//get the account.
			User account = getAccountManager().getAccount(id);

			/*
			check for each text field if it is empty or not. If not, assign its text value to the equivalent attribute
			of the user in the database.
			 */
			if (!(firstName.getText().isEmpty())) {
				account.setFirstName(firstName.getText());
			}
			if (!(surname.getText().isEmpty())) {
				account.setLastName(surname.getText());
			}
			if (!(streetName.getText().isEmpty())) {
				account.setStreetName(streetName.getText());
			}
			if (!(streetNumber.getText().isEmpty())) {
				account.setStreetNum(streetNumber.getText());
			}
			if (!(city.getText().isEmpty())) {
				account.setCity(city.getText());
			}
			if (!(county.getText().isEmpty())) {
				account.setCounty(county.getText());
			}
			if (!(postCode.getText().isEmpty())) {
				account.setPostCode(postCode.getText());
			}
			if (!(phoneNumber.getText().isEmpty())) {
				account.setTelNum(phoneNumber.getText());
			}

			//set the user's avatar in the database.
			account.setAvatarID(getResourceManager().getImageID(path));

			//edit the account with the new details.
			getAccountManager().editAccount(account);

			//Show the user a window indicating that their save was successful.
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
	 * @param event the event triggered by clicking the button.
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
	 * Initialises the interface to display the current details of the user in the text fields.
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
		/*
		try to get the file path of the existing avatar for the user and set the image for the select avatar button to
		the image at that path. If getting the URL from the database fails, tell the user that the avatar
		could not be loaded.
		*/
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
