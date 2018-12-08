/**
 * Interface controller for the book list scene
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */

package JavaFX;

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

public class BookListController extends SceneController implements Initializable {

    @FXML
    private Pagination bookView;

    private double elementsPerPage = 3;
    private Resource resourceList[] = null;
    private int i;


    /**
     * Initialises
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
                element.getChildren().addAll(image, text);
                element.setAlignment(Pos.TOP_CENTER);
                element.setSpacing(10);
                element.setPadding(new Insets(100, 0, 0, 0));
                box.getChildren().add(element);


                element.getStylesheets().add("/Resources/CoreStyle.css");
                element.getStyleClass().add("UniversalButton");
                element.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    try {
                        setRequestResource(resourceList[Integer.parseInt(element.getId())]);
                        loadSubscene("/View/RequestResourceByUserInterface.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
