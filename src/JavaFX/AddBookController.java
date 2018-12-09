package JavaFX;

import Core.Book;
import Core.Copy;
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

/**
 * Handles adding data of a book into the database
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AddBookController extends ResourceController implements Initializable {

    private String path;

    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField genre;
    @FXML
    private TextField publisher;
    @FXML
    private TextField year;
    @FXML
    private TextField isbn;
    @FXML
    private TextField language;
    @FXML
    private TextField numOfCopies;
    @FXML
    private ImageView thumbnail;

    @FXML
    public void handleCreateResourceButtonAction(ActionEvent event) {
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !author.getText().isEmpty()
                && !publisher.getText().isEmpty()) {
            try {
                Book book = new Book(0, title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), author.getText(),
                        publisher.getText(), genre.getText(), isbn.getText(), language.getText());
                getResourceManager().addResource(book);

                int copies = Integer.parseInt(numOfCopies.getText());
                getResourceManager().addBulkCopies(new Copy(0, getResourceManager().getLastAddedID(), 14,
                        "", 0, 0), copies);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Resource Created Successfully!\nResource ID = \"\n" +
                        + getResourceManager().getLastAddedID());
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Couldn't load an image");
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

    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        path = setThumbnailImage(event);
        thumbnail.setImage(new Image(path));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Thumbnail Set");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
