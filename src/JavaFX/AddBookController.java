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
 * Handles adding data of a book into the database
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AddBookController extends ResourceController implements Initializable {

    private FileChooser thumbnailChooser = new FileChooser();
    private Path selectedPath;
    private String path;

    @FXML
    private TextField title, author, genre, publisher, year, isbn, language, numOfCopies;
    @FXML
    private ImageView thumbImage;

    @FXML
    public void handleCreateResourceButtonAction(ActionEvent event) {
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !author.getText().isEmpty()
                && !publisher.getText().isEmpty()) {
            try {
                getResourceManager().addResource(new Book(0, title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), author.getText(),
                        publisher.getText(), genre.getText(), isbn.getText(), language.getText()));
            } catch (SQLException e) {
                System.out.println("Couldn't load an image.");
            }
        } else {
            System.out.println("Not enough information.");
            loadSubscene(getResourceInterface());
        }

        int copies = Integer.parseInt(numOfCopies.getText());
        //addCopies(copies);

        loadSubscene(getResourceInterface());
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
