package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public static final String MAIN_INTERFACE = "LoginLoginInterface.fxml";
    public static final String HOME_INTERFACE = "HomeInterface.fxml";
    public static final String DASHBOARD_INTERFACE = "UserDashboardInterface.fxml";


    public void handleButtonAction(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene userScreen = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show();
    }
}
