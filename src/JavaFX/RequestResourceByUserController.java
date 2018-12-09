package JavaFX;

import Core.Book;
import Core.Computer;
import Core.Dvd;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RequestResourceByUserController extends SceneController implements Initializable {
    @FXML
    private ImageView resourceImg;

    @FXML
    private Label resourceTitle;

    @FXML
    private GridPane descriptionPane;

    @FXML
    public void handleRequestResourceButtonAction() {
        try {
            getResourceFlowManager().requestResource(getRequestResource().getResourceID(), SceneController.USER_ID);
            JOptionPane.showMessageDialog(null, "Requested Successfully!", "Resource Requested", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Failed request.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            resourceImg.setImage(new Image(getResourceManager().getImageURL(getRequestResource().getThumbImage())));
        } catch (SQLException e) {
            System.out.println("Couldn't find image");
        }
        resourceTitle.setText(getRequestResource().getTitle());
        resourceTitle.wrapTextProperty().setValue(Boolean.TRUE);
        setResourceDesc();
    }

    public void setResourceDesc(){
        int rowCounter = 0;
        if(getRequestResource() instanceof Book){
            Book book = (Book) getRequestResource();
            if(!book.getAuthor().isEmpty()){
                Label author = new Label("Author:\t " + book.getAuthor());
                descriptionPane.addRow(rowCounter, author);
                rowCounter++;
            }
            if(!book.getGenre().isEmpty()){
                Label genre = new Label("Genre:\t " + book.getGenre());
                descriptionPane.addRow(rowCounter, genre);
                rowCounter++;
            }
            if(!book.getPublisher().isEmpty()){
                Label publisher = new Label("Publisher:\t " + book.getPublisher());
                descriptionPane.addRow(rowCounter, publisher);
                rowCounter++;
            }
            if(book.getYear() != 0){
                Label year = new Label("Year:\t\t " + String.valueOf(book.getYear()));
                descriptionPane.addRow(rowCounter, year);
                rowCounter++;
            }
            if(!book.getIsbn().isEmpty()){
                Label isbn = new Label("ISBN:\t " + book.getIsbn());
                descriptionPane.addRow(rowCounter, isbn);
                rowCounter++;
            }
            if(!book.getLang().isEmpty()){
                Label language = new Label("Language: " + book.getLang());
                descriptionPane.addRow(rowCounter, language);
                rowCounter++;
            }
        }

        if(getRequestResource() instanceof Dvd) {
            Dvd dvd = (Dvd) getRequestResource();
            if(!dvd.getDirector().isEmpty()){
                Label director = new Label("Director: " + dvd.getDirector());
                descriptionPane.addRow(rowCounter, director);
                rowCounter++;
            }
            if(dvd.getYear() != 0){
                Label year = new Label("Year:\t\t " + dvd.getYear());
                descriptionPane.addRow(rowCounter, year);
                rowCounter++;
            }
            if(!dvd.getLanguage().isEmpty()){
                Label language = new Label("Language: " + dvd.getLanguage());
                descriptionPane.addRow(rowCounter, language);
                rowCounter++;
            }
            if(dvd.getRunTime() != 0){
                Label runtime = new Label("Year:\t\t " + dvd.getRunTime());
                descriptionPane.addRow(rowCounter, runtime);
                rowCounter++;
            }
            if(dvd.getSubLang().length != 0){
                String subLangText = "Subtitle:\t";
                for(String element: dvd.getSubLang()){
                    subLangText += element + "\n\t\t";
                }
                Label subLang = new Label(subLangText);
                descriptionPane.addRow(rowCounter, subLang);
                rowCounter++;
            }
        }

        if(getRequestResource() instanceof Computer) {
            Computer computer = (Computer) getRequestResource();
            if(!computer.getModel().isEmpty()){
                Label model = new Label("Model:\t " + computer.getModel());
                descriptionPane.addRow(rowCounter, model);
                rowCounter++;
            }
            if(computer.getYear() != 0){
                Label year = new Label("Year:\t\t " + computer.getYear());
                descriptionPane.addRow(rowCounter, year);
                rowCounter++;
            }
            if(!computer.getManufacturer().isEmpty()){
                Label manufacturer = new Label("Manufacturer:\t " + computer.getManufacturer());
                descriptionPane.addRow(rowCounter, manufacturer);
                rowCounter++;
            }
            if(!computer.getOs().isEmpty()){
                Label os = new Label("System:\t " + computer.getOs());
                descriptionPane.addRow(rowCounter, os);
                rowCounter++;
            }
        }

        descriptionPane.setVgap(5);
        descriptionPane.getRowConstraints().setAll(new RowConstraints(20));
    }
}
