package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface controller for the DVD search menu
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class DVDSearchMenuController extends SceneController{
    @FXML
    private TextField dvdTitle;

    @FXML
    private TextField dvdDirector;

    @FXML
    private TextField dvdYear;

    /**
     * Executes a search from information specified.
     * @param event Represents the data of the button pressed.
     * @throws IOException Thrown if input is null.
     */
    public void handleDVDSearchButton(ActionEvent event) throws IOException {
        getInput();
        loadSubscene("/View/DVDList.fxml");
    }

    /**
     * Reads the input for the query from text fields.
     */
    public void getInput(){
        ArrayList<String> column = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        if(!dvdTitle.getText().isEmpty()){
            column.add("Title");
            input.add("'%" + dvdTitle.getText() + "%'");
        }
        if(!dvdDirector.getText().isEmpty()){
            column.add("Director");
            input.add("'%" + dvdDirector.getText() + "%'");
        }
        if(!dvdYear.getText().isEmpty()){
            column.add("RYear");
            input.add(dvdYear.getText());
        }

        setSqlQuery(getResourceManager().createQuery(column.toArray(new String[column.size()]),
                input.toArray(new String[input.size()]), "Dvd"));
        System.out.println(getSqlQuery());
    }
}
