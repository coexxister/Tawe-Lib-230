package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfileImageSelectorController extends SceneController {
    @FXML
    private void handleDrawingEnvironmentButtonAction(ActionEvent event) throws Exception{
        loadSubscene(SceneController.DRAWING_INTERFACE);
    }
}
