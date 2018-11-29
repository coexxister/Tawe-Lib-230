package JavaFX;

import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ListAllSceneController extends SceneController implements Initializable {

    @FXML
    private Label r1;
    @FXML
    private Label r2;
    @FXML
    private Label r3;

    public void initialize(URL location, ResourceBundle resources) {
        r2.setText("dupa");
        Resource resourceList[] = getResourceManager().getResourceList();
        for(int i = 0; i <= resourceList.length; i++){
            r1.setText(resourceList[0].toString());
        }
    }
}
