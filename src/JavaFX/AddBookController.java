package JavaFX;

import Core.Book;
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

/**
 * Handles adding data of a book into the database
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AddBookController extends ResourceController implements Initializable {

    private String path;

    @FXML
    private TextField title, author, genre, publisher, year, isbn, language, numOfCopies;
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

                JOptionPane.showMessageDialog(null, "Resource Set.\nResource ID = "
                        + getResourceManager().getLastAddedID(),
                        "Resource Set", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.out.println("Couldn't load an image.");
            }
        } else {
            System.out.println("Not enough information.");
            loadSubscene(getResourceInterface());
        }
        loadSubscene(getResourceInterface());
    }

    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        path = setThumbnailImage(event);
        thumbnail.setImage(new Image(path));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
