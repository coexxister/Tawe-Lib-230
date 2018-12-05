package JavaFX;

import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the list all subscene of the staff interface
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */

public class ListAllController extends SceneController implements Initializable {

    @FXML
    private Pagination resourceView;

    private double elementsPerPage = 3;
    private Resource resourceList[] = getResourceManager().getResourceList();

    /**
     * Initialises the paginated list of resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        resourceView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    /**
     * Generates a list of pages as a horizontal box in the interface
     * @param pageIndex ?
     * @return box
     * 			The generated horizontal box, containing the pages of the list
     */
    public HBox createPage(int pageIndex) {
        HBox box = new HBox(elementsPerPage);
        resourceView.setPageCount((int) (Math.ceil((double) resourceList.length / elementsPerPage)));
        int page = pageIndex * (int)elementsPerPage;
        for (int i = page; i < page + elementsPerPage; i++) {
            if(i < resourceList.length) {
                HBox element = new HBox(elementsPerPage);
                ImageView image = new ImageView();

                if (resourceList[i].toString().contains("Type - Book")) {
                    try {
                        image.setImage(new Image(getResourceManager().
                                getImageURL(resourceList[i].getThumbImage())));
                    } catch (SQLException e) {
                        image.setImage(new Image("/Resources/bookIcon.png"));
                    }
                } else if (resourceList[i].toString().contains("Type - Dvd")) {
                    try {
                        image.setImage(new Image(getResourceManager().
                                getImageURL(resourceList[i].getThumbImage())));
                    } catch (SQLException e) {
                        image.setImage(new Image("/Resources/dvdIcon.png"));
                    }
                } else if (resourceList[i].toString().contains("Type - Computer")) {
                    try {
                        image.setImage(new Image(getResourceManager().
                                getImageURL(resourceList[i].getThumbImage())));
                    } catch (SQLException e) {
                        image.setImage(new Image("/Resources/laptopIcon.png"));
                    }
                }
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
            }
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
