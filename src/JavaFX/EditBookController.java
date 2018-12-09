package JavaFX;

import Core.Book;
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
 * Handles editing data of an existing book in the database.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class EditBookController extends ResourceController implements Initializable {

    private Book book = (Book) getRequestResource();
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
    private ImageView thumbImage;

    /**
     * @param location  The location used to resolve relative paths for the root object
     * @param resources The resources used to localize the root object
     */
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
                path = getResourceManager().getImageURL(book.getThumbImage());
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
     * Assigns the thumbnail selected to the specific book.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        path = setThumbnailImage(event);
        thumbImage.setImage(new Image(path));
    }
}
