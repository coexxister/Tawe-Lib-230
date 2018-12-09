package JavaFX;

import Core.Book;
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

/**
 * Handles editing data of an existing book in the database.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class EditBookController extends ResourceController implements Initializable {

    private Book book = (Book) getRequestResource();
    private FileChooser thumbnailChooser = new FileChooser();
    private Path selectedPath;
    private String path;

    @FXML
    private TextField title, author, genre, publisher, year, isbn, language, numOfCopies;

    @FXML
    private ImageView thumbImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!book.getTitle().isEmpty()) {
            title.setText(book.getTitle());
        }
        if (!book.getAuthor().isEmpty()) {
            author.setText(book.getAuthor());
        }
        if (!book.getGenre().isEmpty()) {
            genre.setText(book.getGenre());
        }
        if (!book.getPublisher().isEmpty()) {
            publisher.setText(book.getPublisher());
        }
        if (book.getYear() != 0) {
            year.setText(String.valueOf(book.getYear()));
        }
        if (!book.getIsbn().isEmpty()) {
            isbn.setText(book.getIsbn());
        }
        if (!book.getLang().isEmpty()) {
            language.setText(book.getLang());
        }
        if (getResourceManager().getCopies(book.getResourceID()).length != 0) {
            numOfCopies.setText(String.valueOf(getResourceManager().getCopies(book.getResourceID()).length));
        } else {
            numOfCopies.setText("0");
        }
        if (book.getThumbImage() != 0) {
            try {
                thumbImage.setImage(new Image(getResourceManager().getImageURL(book.getThumbImage())));
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
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !thumbImage.equals(null)
                && !author.getText().isEmpty() && !publisher.getText().isEmpty()) {
            try {
                getResourceManager().editResource(new Book(book.getResourceID(), title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), author.getText(),
                        publisher.getText(), genre.getText(), isbn.getText(), language.getText()));
            } catch (SQLException e) {
                System.out.println("Couldn't load an image.");
            }
        } else {
            System.out.println("Not enough information.");
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
     * Assigns the thumbnail selected to the specific book.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        thumbnailChooser.setInitialDirectory(new File("src/ResourceImages"));
        Node node = (Node) event.getSource();
        File file = thumbnailChooser.showOpenDialog(node.getScene().getWindow());
        selectedPath = Paths.get(file.getAbsolutePath());

        path = selectedPath.toString();
        path = path.replace("\\", "/");
        final int LENGTH_OF_SRC = 3;
        path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
        thumbImage.setImage(new Image(path));
    }
}
