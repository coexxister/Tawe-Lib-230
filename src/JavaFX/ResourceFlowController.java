package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResourceFlowController extends SceneController implements Initializable {

    @FXML
    private ImageView profileImage;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Set the users profile image.
            int id = getResourceFlowManager().getUserID();
            int imageID = getAccountManager().getAccount(getResourceFlowManager().getUserID()).getAvatarID();
            String imageURL = getResourceManager().getImageURL(imageID);
            profileImage.setImage(new Image(imageURL));


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("User has an undefined profile image.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleTransactionAction(ActionEvent event) throws IOException {
        loadSubscene(SceneController.TRANSACTION_MANAGER_INTERFACE);
    }

}
