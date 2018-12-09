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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

                VBox element = new VBox(elementsPerPage);
                element.setId(String.valueOf(i));
                ImageView image = new ImageView();
                try {
                    image.setImage(new Image(getResourceManager().
                            getImageURL(resourceList[i].getThumbImage())));
                } catch (SQLException e) {
                    image.setImage(new Image("/Resources/bookIcon.png"));
                }

                image.setFitWidth(100);
                image.setPreserveRatio(true);
                image.setSmooth(true);
                image.setCache(true);

                Label text = new Label(resourceList[i].getTitle());
                text.wrapTextProperty().setValue(true);
                Label availability = new Label(getAvailableNumberOfCopies(resourceList[i]));
                element.getChildren().addAll(availability, image, text);
                element.setAlignment(Pos.TOP_CENTER);
                element.setSpacing(10);
                element.setPrefWidth(200);
                element.setPadding(new Insets(100, 0, 0, 0));
                box.getChildren().add(element);
                getOnMouseClicked(resourceList, element);
            }
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
