package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Interface controller for the book search menu
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class BookSearchMenuController extends SceneController{

    @FXML
    private TextField title, author, year, genre, isbn;

    /**
     * Executes a search from information specified.
     * @param event Represents the data of the button pressed.
     */
    public void handleSearchByQueryButtonAction(ActionEvent event) {
        getInput();
        loadSubscene("/View/BookList.fxml");
    }

    /**
     * Executes a search by ISBN specified.
     * @param event Represents the data of the button pressed.
     */
    public void handleSearchByISBNButtonAction(ActionEvent event) {
        if(!isbn.getText().isEmpty()) {
            setSqlQuery(getResourceManager().createQuery(new String[]{"ISBN"}, new String[]{isbn.getText()}, "Book"));
            loadSubscene("/View/BookList.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid ISBN.");
            alert.showAndWait();
        }
    }

    /**
     * Reads the input for the query from text fields.
     */
    public void getInput(){
        ArrayList<String> column = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        if(!title.getText().isEmpty()){
            column.add("Title");
            input.add("'%" + title.getText() + "%'");
        }
        if(!author.getText().isEmpty()){
            column.add("Author");
            input.add("'%" + author.getText() + "%'");
        }
        if(!year.getText().isEmpty()){
            column.add("RYear");
            input.add(year.getText());
        }
        if(!genre.getText().isEmpty()){
            column.add("Genre");
            input.add("'%" + genre.getText() + "%'");
        }

        if(!input.isEmpty()) {
            setSqlQuery(getResourceManager().createQuery(column.toArray(new String[column.size()]),
                    input.toArray(new String[input.size()]), "Book"));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid input.");
            alert.showAndWait();
        }
    }
}
