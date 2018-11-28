package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class VistaTest2Controller extends SceneController{
    @FXML
    public void handlePreviousPaneButtonAction(ActionEvent event) throws IOException {
        handleSubsceneChangeButtonAction("VistaTest1.fxml");
    }
}
