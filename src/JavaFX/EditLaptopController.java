package JavaFX;

import Core.Computer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditLaptopController extends ResourceController implements Initializable {

    private Computer computer = (Computer) getRequestResource();
    private String path;

    @FXML
    private TextField title;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField year;
    @FXML
    private TextField model;
    @FXML
    private TextField os;
    @FXML
    private TextField numOfCopies;

    @FXML
    private ImageView thumbnailImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!computer.getTitle().isEmpty()) {
            title.setText(computer.getTitle());
        }
        if (!computer.getManufacturer().isEmpty()) {
            manufacturer.setText(computer.getManufacturer());
        }
        if (computer.getYear() != 0) {
            year.setText(String.valueOf(computer.getYear()));
        }
        if (!computer.getModel().isEmpty()) {
            model.setText(computer.getModel());
        }
        if (!computer.getOs().isEmpty()) {
            os.setText(computer.getOs());
        }
        if (getResourceManager().getCopies(computer.getResourceID()).length != 0) {
            numOfCopies.setText(String.valueOf(getResourceManager().getCopies(computer.getResourceID()).length));
        } else {
            numOfCopies.setText("0");
        }
        if (computer.getThumbImage() != 0) {
            try {
                thumbnailImg.setImage(new Image(getResourceManager().getImageURL(computer.getThumbImage())));
                path = getResourceManager().getImageURL(computer.getThumbImage());
            } catch (SQLException e) {
                System.out.println("Couldn't find image.");
            }
        }
    }

    /**
     * Saves all details set in text fields to respective variables,
     * to change the values in the database.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSaveButtonAction(ActionEvent event) {
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !thumbnailImg.equals(null)
                && !model.getText().isEmpty() && !manufacturer.getText().isEmpty() && !os.getText().isEmpty()) {
            try {
                getResourceManager().editResource(new Computer(computer.getResourceID(), title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), manufacturer.getText(),
                        model.getText(), os.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Changes Saved Successfully!");
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
        }
    }

    /**
     * Cancels all changes.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        cancel();
    }

    /**
     * Assigns the thumbnail selected to the specific DVD.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        path = setThumbnailImage(event);
        thumbnailImg.setImage(new Image(path));
    }
}
