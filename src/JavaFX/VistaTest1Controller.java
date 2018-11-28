package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class VistaTest1Controller extends SceneController{
    @FXML
    public void handleNextPaneButtonAction(ActionEvent event) throws IOException {
        handleSubsceneChangeButtonAction("VistaTest2.fxml");
    }
}
