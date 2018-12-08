package JavaFX;

import Core.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditBookController extends SceneController implements Initializable {

    private Book book = (Book) getRequestResource();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!book.getTitle().isEmpty()){
            title.setText(book.getTitle());
        }
        if(!book.getAuthor().isEmpty()){
            author.setText(book.getAuthor());
        }
        if(!book.getGenre().isEmpty()){
            genre.setText(book.getGenre());
        }
        if(!book.getPublisher().isEmpty()){
            publisher.setText(book.getPublisher());
        }
        if(book.getYear() != 0){
            year.setText(String.valueOf(book.getYear()));
        }
        if(!book.getIsbn().isEmpty()){
            isbn.setText(book.getIsbn());
        }
        if(!book.getLang().isEmpty()){
            language.setText(book.getLang());
        }
        if(getResourceManager().getCopies(book.getResourceID()).length != 0){
            numOfCopies.setText(String.valueOf(getResourceManager().getCopies(book.getResourceID()).length));
        } else {
            numOfCopies.setText("0");
        }
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent event){
        getResourceManager().editResource(new Book(book.getResourceID(), title.getText(), Integer.parseInt(year.getText()),
                book.getThumbImage(), author.getText(), publisher.getText(), genre.getText(), isbn.getText(), language.getText()));
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event){
        loadSubscene("/View/ResourceInterface.fxml");
    }
}
