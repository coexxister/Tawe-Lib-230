package JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RequestResourceByUserController extends SceneController implements Initializable {
    @FXML
    private ImageView resourceImg;

    @FXML
    private Label resourceDesc;

    @FXML
    public void handleRequestResourceButtonAction() {
        try {
            getResourceFlowManager().requestResource(getRequestResource().getResourceID(), SceneController.USER_ID);
            JOptionPane.showMessageDialog(null, "Requested Successfully!", "Resource Requested", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Failed request.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            resourceImg.setImage(new Image(getResourceManager().getImageURL(getRequestResource().getThumbImage())));
        } catch (SQLException e) {
            System.out.println("Couldn't find image");
        }
        resourceDesc.setText(getRequestResource().toString());
        resourceDesc.wrapTextProperty().setValue(Boolean.TRUE);
    }
}