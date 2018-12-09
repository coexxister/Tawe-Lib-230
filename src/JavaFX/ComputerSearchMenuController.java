package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface controller for the laptop search menu
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class ComputerSearchMenuController extends SceneController{

    @FXML
    private TextField computerTitle;

    @FXML
    private TextField computerYear;

    @FXML
    private TextField computerModel;

    @FXML
    private TextField computerManufacturer;

    @FXML
    private TextField computerOS;

    /**
     * Executes a search from information specified.
     * @param event Represents the data of the button pressed.
     * @throws IOException Thrown if input is null.
     */
    public void handleSearchComputerButtonAction(ActionEvent event) throws IOException {
        getInput();
        loadSubscene("/View/ComputerList.fxml");
    }

    /**
     * Reads the input for the query from text fields.
     */
    public void getInput(){
        ArrayList<String> column = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        if(!computerTitle.getText().isEmpty()){
            column.add("Title");
            input.add("'%" + computerTitle.getText() + "%'");
        }
        if(!computerYear.getText().isEmpty()){
            column.add("RYear");
            input.add("'%" + computerYear.getText() + "%'");
        }
        if(!computerModel.getText().isEmpty()){
            column.add("Model");
            input.add("'%" + computerModel.getText() + "%'");
        }
        if(!computerManufacturer.getText().isEmpty()){
            column.add("Manufacturer");
            input.add("'%" + computerManufacturer.getText() + "%'");
        }
        if(!computerOS.getText().isEmpty()){
            column.add("Installed_OS");
            input.add("'%" + computerOS.getText() + "%'");
        }

        if(!input.isEmpty()) {
            setSqlQuery(getResourceManager().createQuery(column.toArray(new String[column.size()]),
                    input.toArray(new String[input.size()]), "Computer"));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid input.");
            alert.showAndWait();
        }
    }
}
