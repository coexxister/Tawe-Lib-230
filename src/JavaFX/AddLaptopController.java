package JavaFX;

import Core.Computer;
import Core.Copy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddLaptopController extends ResourceController implements Initializable {

    private String path;

    @FXML
    private TextField title, manufacturer, year, model, installedOS, numOfCopies;

    @FXML
    private ImageView thumbImage;

    @FXML
    public void handleCreateResourceButtonAction(ActionEvent event) {
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !manufacturer.getText().isEmpty() &&
                !model.getText().isEmpty() && !installedOS.getText().isEmpty()) {
            try {
                getResourceManager().addResource(new Computer(0, title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path),
                        manufacturer.getText(), model.getText(), installedOS.getText()));

                int copies = Integer.parseInt(numOfCopies.getText());
                getResourceManager().addBulkCopies(new Copy(0, getResourceManager().getLastAddedID(), 14,
                        "", 0, 0), copies);

                JOptionPane.showMessageDialog(null, "Resource Set.\nResource ID = "
                                + getResourceManager().getLastAddedID(),
                        "Resource Set", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                loadSubscene(getResourceInterface());
            }

            loadSubscene(getResourceInterface());
        }
    }

    /**
     * Assigns the thumbnail selected to the specific DVD.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        path = setThumbnailImage(event);
        thumbImage.setImage(new Image(path));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}