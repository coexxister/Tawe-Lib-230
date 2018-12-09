package JavaFX;

import Core.Copy;
import Core.Dvd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDVDController extends ResourceController implements Initializable {

    private String path;

    @FXML
    private ChoiceBox loanDuration;

    @FXML
    private TextField title;
    @FXML
    private TextField director;
    @FXML
    private TextField year;
    @FXML
    private TextField language;
    @FXML
    private TextField subtitle;
    @FXML
    private TextField runtime;
    @FXML
    private TextField numOfCopies;

    @FXML
    private ImageView thumbImage;

    @FXML
    public void handleCreateResourceButtonAction(ActionEvent event) {
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !director.getText().isEmpty()
                && !runtime.getText().isEmpty()) {
            try {
                String[] subLang = subtitle.getText().replaceAll(" ", "").split(",");
                getResourceManager().addResource(new Dvd(0, title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), director.getText(),
                        Integer.parseInt(runtime.getText()), language.getText(), subLang));

                int copies = Integer.parseInt(numOfCopies.getText());
                getResourceManager().addBulkCopies(new Copy(0, getResourceManager().getLastAddedID(), (int)loanDuration.getValue(),
                        "", 0, 0), copies);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Resource Created Successfully!\nResource ID = \"\n" +
                        +getResourceManager().getLastAddedID());
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Couldn't load an image.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not enough information.");
            alert.showAndWait();
            loadSubscene(getResourceInterface());
        }
        loadSubscene(getResourceInterface());
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

    /**
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanDuration.getItems().addAll(1, 7, 14, 28);
        loanDuration.setValue(14);
    }
}
