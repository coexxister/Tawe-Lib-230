/**
 * Interface controller for the book list scene
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */

package JavaFX;

import Core.Copy;
import Core.Resource;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Creates a list of books from a query.
 */
public class BookListController extends SceneController implements Initializable {

    @FXML
    private Pagination bookView;

    private double elementsPerPage = 3;
    private Resource resourceList[] = null;
    private int i;


    /**
     * Initialises Book List.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        {
            try {
                resourceList = getResourceManager().getResourceList(getSqlQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        bookView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    /**
     * Creates pages using pagination to fill the list with books.
     *
     * @param pageIndex Index of the page
     * @return box An HBox containing pagination with the list of books.
     */
    public HBox createPage(int pageIndex) {
        HBox box = new HBox(elementsPerPage);
        bookView.setPageCount((int) (Math.ceil((double) resourceList.length / elementsPerPage)));
        int page = pageIndex * (int)elementsPerPage;
        for (i = page; i < page + elementsPerPage; i++) {
            if(i < resourceList.length) {

                HBox element = new HBox(elementsPerPage);
                element.setId(String.valueOf(i));
                ImageView image = new ImageView("/Resources/bookIcon.png");

                image.setFitWidth(100);
                image.setPreserveRatio(true);
                image.setSmooth(true);
                image.setCache(true);

                Label text = new Label(resourceList[i].toString());
                text.wrapTextProperty().setValue(true);
                Label availability = new Label();
                availability.setDisable(true);
                Copy[] copies = getResourceManager().getCopies(resourceList[i].getResourceID());
                for(Copy copy: copies){
                    if(copy.getStateID() == 0) {
                        availability.setDisable(false);
                        availability = new Label("Available");
                    }
                }
                element.getChildren().addAll(image, text, availability);
                element.setAlignment(Pos.TOP_CENTER);
                element.setSpacing(10);
                element.setPadding(new Insets(100, 0, 0, 0));
                box.getChildren().add(element);
                getOnMouseClicked(resourceList, element);
            }
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
