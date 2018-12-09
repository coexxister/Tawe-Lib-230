package JavaFX;

import Core.Dvd;
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
 */
public class EditDVDController extends ResourceController implements Initializable {

    private Dvd dvd = (Dvd) getRequestResource();
    private FileChooser thumbnailChooser = new FileChooser();
    private Path selectedPath;
    private String path;

    @FXML
    private TextField title;

    @FXML
    private TextField director;

    @FXML
    private TextField year;

    @FXML
    private TextField runtime;

    @FXML
    private TextField language;

    @FXML
    private TextField subtitle;

    @FXML
    private TextField numOfCopies;

    @FXML
    private ImageView thumbImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!dvd.getTitle().isEmpty()){
            title.setText(dvd.getTitle());
        }
        if(!dvd.getDirector().isEmpty()){
            director.setText(dvd.getDirector());
        }
        if(dvd.getSubLang().length != 0){
            subtitle.setText(dvd.getSubLang().toString());
        }
        if(dvd.getRunTime() != 0){
            runtime.setText(String.valueOf(dvd.getRunTime()));
        }
        if(dvd.getYear() != 0){
            year.setText(String.valueOf(dvd.getYear()));
        }
        if(!dvd.getLanguage().isEmpty()){
            language.setText(dvd.getLanguage());
        }
        if(getResourceManager().getCopies(dvd.getResourceID()).length != 0){
            numOfCopies.setText(String.valueOf(getResourceManager().getCopies(dvd.getResourceID()).length));
        } else {
            numOfCopies.setText("0");
        }
        if(dvd.getThumbImage() != 0){
            try {
                thumbImage.setImage(new Image(getResourceManager().getImageURL(dvd.getThumbImage())));
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
    public void handleSaveButtonAction(ActionEvent event){
        if(!title.getText().isEmpty() && !year.getText().isEmpty() && !thumbImage.equals(null)
                && !director.getText().isEmpty() && !runtime.getText().isEmpty()) {
            try {
                String[] subLang = subtitle.getText().split(", ");
                getResourceManager().editResource(new Dvd(dvd.getResourceID(), title.getText(),
                        Integer.parseInt(year.getText()), getResourceManager().getImageID(path), director.getText(),
                        Integer.parseInt(runtime.getText()), language.getText(), subLang));
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
    public void handleCancelButtonAction(ActionEvent event){
        cancel();
    }

    /**
     * Assigns the thumbnail selected to the specific DVD.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleSetThumbnailButtonAction(ActionEvent event) {
        thumbnailChooser.setInitialDirectory(new File("src/ResourceImages"));
        Node node = (Node) event.getSource();
        File file  = thumbnailChooser.showOpenDialog(node.getScene().getWindow());
        selectedPath = Paths.get(file.getAbsolutePath());

        path = selectedPath.toString();
        path = path.replace("\\","/");
        final int LENGTH_OF_SRC = 3;
        path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
        thumbImage.setImage(new Image(path));
    }
}
