package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * Allows the user to select an avatar.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class ProfileImageSelectorController extends SceneController {

	/**
	 * File path of the selected avatar.
	 */
	private Path selectedPath;

	/**
	 * FileChooser to select the avatar.
	 */
	private FileChooser avatarChooser = new FileChooser();

	/**
	 * selects the first default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault1(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar1.png");
		setAvatar(false);
	}

	/**
	 * selects the second default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault2(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar2.png");
		setAvatar(false);
	}

	/**
	 * selects the third default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault3(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar3.png");
		setAvatar(false);
	}

	/**
	 * selects the fourth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault4(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar4.png");
		setAvatar(false);
	}

	/**
	 * selects the fifth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault5(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar5.png");
		setAvatar(false);
	}

	/**
	 * selects the sixth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault6(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar6.png");
		setAvatar(false);
	}

	/**
	 * selects the seventh default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault7(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar7.png");
		setAvatar(false);
	}

	/**
	 * selects the eighth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault8(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar8.png");
		setAvatar(false);
	}

	/**
	 * selects the ninth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault9(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar9.png");
		setAvatar(false);
	}

	/**
	 * selects the tenth default avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectDefault10(ActionEvent event) {
		selectedPath = Paths.get("/DefaultAvatars/Avatar10.png");
		setAvatar(false);
	}

	/**
	 * selects a custom avatar.
	 *
	 * @param event Event triggered by interface action.
	 */
	@FXML
	private void selectCustomAvatar(ActionEvent event) {
		avatarChooser.setInitialDirectory(new File("src/CustomAvatars"));
		Node node = (Node) event.getSource();
		File file = avatarChooser.showOpenDialog(node.getScene().getWindow());
		selectedPath = Paths.get(file.getAbsolutePath());

		setAvatar(true);
	}

	/**
	 * Sets the avatar of the user and adds it to the database.
	 *
	 * @param isCustom True if user has selected a custom avatar.
	 */
	private void setAvatar(boolean isCustom) {
		try {

			//Get the user account
			User account = getAccountManager().getAccount(SceneController.USER_ID);

			//Make sure the image path uses forward slash
			String path = selectedPath.toString();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Avatar Set!");
			alert.showAndWait();
			path = path.replace("\\", "/");

			//Create a relative path rather than absolute if custom image selected
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
	 * Changes to the subscene for the drawing environment.
	 *
	 * @param event the event triggered by clicking the button.
	 */
	@FXML
	private void handleDrawingEnvironmentButtonAction(ActionEvent event) {
		loadSubscene(SceneController.getDrawingInterface());
	}
}
