package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BookSearchMenuController extends SceneController{

    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private TextField publisher;

    @FXML
    private TextField year;

    @FXML
    private TextField genre;

    @FXML
    private TextField language;

    @FXML
    public void handleSearchByQueryButtonAction(ActionEvent event) throws IOException {
        createQuery();
        loadSubscene("/View/BookList.fxml");
    }

    public void handleSearchByISBNButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/BookList.fxml");
    }

    public void createQuery(){
        if(title.getText() != null){
            addColumn("Title");
            addInput(title.getText());
        }
/*        if(author.getText() != null){
            SceneController.columns += "Author";
            SceneController.resource += author.getText();
        }
        if(publisher.getText() != null){
            SceneController.columns += "Publisher";
            SceneController.resource += publisher.getText();
        }
        if(year.getText() != null){
            SceneController.columns += "RYear";
            SceneController.resource += year.getText();
        }
        if(genre.getText() != null){
            SceneController.columns += "Genre";
            SceneController.resource += genre.getText();
        }
        if(language.getText() != null){
            SceneController.columns += "Language";
            SceneController.resource += language.getText();
        }
*/
    }
}
