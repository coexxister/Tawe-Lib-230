package JavaFX;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

import java.util.ResourceBundle;
import java.net.URL;

/**
 * Interface controller for the staff resource interface
 * @author Dominic Woodman
 * @version 0.1
 */
public class ResourceInterfaceController extends SceneController implements Initializable {

	private ComboBox selectType;

	protected void addResourceScene(ActionEvent Event) {

	}

	protected void selectResourceImage(ActionEvent Event) {

	}

	public void initialize(URL location, ResourceBundle resources) {
		selectType.getItems().removeAll(selectType.getItems());
		selectType.getItems().addAll("Book", "Computer", "DVD");
	}
}
