package JavaFX;

import Core.Book;
import Core.Computer;
import Core.Dvd;
import Core.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface for resource management.
 */
public class ResourceController extends SceneController {

    @FXML
    private TextField searchID;

    /**
     * Loads subscene to Add Book.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddBookButtonAction(ActionEvent event){
        loadSubscene("/View/AddBookInterface.fxml");
    }

    /**
     * Loads subscene to Add DVD.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddDVDButtonAction(ActionEvent event){
        loadSubscene("/View/AddDVDInterface.fxml");
    }

    /**
     * Loads subscene to Add Computer.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleAddComputerButtonAction(ActionEvent event){
        loadSubscene("/View/AddLaptopInterface.fxml");
    }

    /**
     * Loads subscene to Edit a resource.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleEditResourceButtonAction(ActionEvent event){
        loadSubscene(getResourceScene("Edit"));
    }

    /**
     * Loads subscene to display log for specified resource.
     * @param event Represents the data of the button pressed.
     */
    @FXML
    public void handleResourceLogButtonAction(ActionEvent event){
        Resource addResource[] = new Resource[1];
        try {
            setRequestResource(getResourceManager().getResource(Integer.parseInt(searchID.getText())));
            loadSubscene("/View/ResourceLogInterface.fxml");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error in Database");
            alert.showAndWait();
        }
    }

    /**
     * Returns from one of the ResourceInterface subscenes to the superscene
     */
    @FXML
    public void cancel() {
        loadSubscene("/View/ResourceInterface.fxml");
    }

    /**
     * Displays the Add Resource subscene depending on the resource type.
     * @param action Represents the data of the button pressed.
     * @return Subscene depending on resource type.
     */
    public String getResourceScene(String action){
        Resource addResource[] = new Resource[1];
        try {
            if(!searchID.getText().isEmpty()) {
                addResource[0] = getResourceManager().getResource(Integer.parseInt(searchID.getText()));
                if(addResource[0] instanceof Book){
                    setRequestResource(addResource[0]);
                    return "/View/" + action + "BookInterface.fxml";
                }
                if(addResource[0] instanceof Dvd){
                    setRequestResource(addResource[0]);
                    return "/View/" + action + "DVDInterface.fxml";
                }
                if(addResource[0] instanceof Computer){
                    setRequestResource(addResource[0]);
                    return "/View/" + action + "LaptopInterface.fxml";
                }
            } else {
                System.out.println("Specify the Resource ID.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't find specific resource.");
        }
        return null;
    }
}
