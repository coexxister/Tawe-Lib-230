package JavaFX;

import java.sql.SQLException;

import Core.ResourceFlowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Interface controller for the issues interface
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class IssuesInterfaceController extends SceneController {

	@FXML
	private TextField userIDBox, copyIDBox;

	private ResourceFlowManager rfManager;

	private int userID = Integer.parseInt(userIDBox.getText());
	private int copyID = Integer.parseInt(copyIDBox.getText());

	/**
	 * Handles the action of clicking the button to issue a copy of a resource
	 * @param event the event triggered by clicking the button
	 * @throws Exception
	 */
	protected void handleIssueButtonAction(ActionEvent event) throws Exception {
		try {
			rfManager.borrowCopy(copyID, userID);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}