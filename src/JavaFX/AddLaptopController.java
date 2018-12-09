package JavaFX;

import Core.Computer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddLaptopController extends ResourceController implements Initializable {

    private FileChooser thumbnailChooser = new FileChooser();
    private Path selectedPath;
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
            } catch (SQLException e) {
                loadSubscene(getResourceInterface());
            }

            int copies = Integer.parseInt(numOfCopies.getText());
            //addCopies(copies);

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
        thumbImage.setImage(new Image(setThumbnailImage(event, path)));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}