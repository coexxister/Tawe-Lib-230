package JavaFX;

import Core.Book;
import Core.Computer;
import Core.Dvd;
import Core.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * Interface for resource management.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class ResourceController extends SceneController {

    private FileChooser imageChooser = new FileChooser();

    @FXML
    private TextField searchID;

    /**
     * Loads subscene to Add Book.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddBookButtonAction(ActionEvent event) {
        loadSubscene(getAddBookInterface());
    }

    /**
     * Loads subscene to Add DVD.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddDVDButtonAction(ActionEvent event) {
        loadSubscene(getAddDvdInterface());
    }

    /**
     * Loads subscene to Add Computer.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddComputerButtonAction(ActionEvent event) {
        loadSubscene(getAddLaptopInterface());
    }

    /**
     * Loads subscene to Edit a resource.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleEditResourceButtonAction(ActionEvent event) {
        try {
            loadSubscene(getResourceScene("Edit"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error in database!");
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error: please select a valid resource identifier");
            alert.showAndWait();
        }
    }

    /**
     * Loads subscene to display log for specified resource.
     *
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleResourceLogButtonAction(ActionEvent event) {
        loadSubscene(getResourceLogInterface());
    }

    /**
     * Returns from one of the ResourceInterface subscenes to the superscene.
     */
    @FXML
    public void cancel() {
        loadSubscene(getResourceInterface());
    }

    /**
     * Displays the Add Resource subscene depending on the resource type.
     *
     * @param action Represents the data of the button pressed.
     * @return Subscene depending on resource type.
     */
    public String getResourceScene(String action) throws SQLException {

        Resource addResource;

        if (!searchID.getText().isEmpty()) {
            addResource = getResourceManager().getResource(Integer.parseInt(searchID.getText()));
            if (addResource instanceof Book) {
                setRequestResource(addResource);
                return "/View/" + action + "BookInterface.fxml";
            }
            if (addResource instanceof Dvd) {
                setRequestResource(addResource);
                return "/View/" + action + "DVDInterface.fxml";
            }
            if (addResource instanceof Computer) {
                setRequestResource(addResource);
                return "/View/" + action + "LaptopInterface.fxml";
            }
        } else {
            System.out.println("Specify the Resource ID.");
        }
        return null;
    }

    public String setThumbnailImage(ActionEvent event) {
        imageChooser.setInitialDirectory(new File("src/ResourceImages"));
        Node node = (Node) event.getSource();
        File file = imageChooser.showOpenDialog(node.getScene().getWindow());
        Path selectedPath = Paths.get(file.getAbsolutePath());

        String path = selectedPath.toString();
        path = path.replace("\\", "/");
        final int LENGTH_OF_SRC = 3;
        path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
        return path;
    }

    public String setAvatar(ActionEvent event) {
        imageChooser.setInitialDirectory(new File("src/DefaultAvatars"));
        Node node = (Node) event.getSource();
        File file = imageChooser.showOpenDialog(node.getScene().getWindow());
        Path selectedPath = Paths.get(file.getAbsolutePath());

        String path = selectedPath.toString();
        path = path.replace("\\", "/");
        final int LENGTH_OF_SRC = 3;
        path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
        return path;
    }
}
