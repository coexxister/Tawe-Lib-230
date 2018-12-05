package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Interface controller for the book search menu
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class BookSearchMenuController extends SceneController{
    @FXML
    public void handlePreviousPaneButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
    }
}
