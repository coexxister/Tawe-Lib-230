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
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Creates a list of computers from a query.
 */
public class ComputerListController extends SceneController implements Initializable {

    @FXML
    private Pagination computerView;

    private double elementsPerPage = 3;
    private Resource resourceList[] = null;


    /**
     * Initialises Computer List.
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
        computerView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    /**
     * Creates pages using pagination to fill the list with computers.
     *
     * @param pageIndex Index of the page
     * @return box An HBox containing pagination with the list of computers.
     */
    public HBox createPage(int pageIndex) {
        HBox box = new HBox(elementsPerPage);
        computerView.setPageCount((int) (Math.ceil((double) resourceList.length / elementsPerPage)));
        int page = pageIndex * (int)elementsPerPage;
        for (int i = page; i < page + elementsPerPage; i++) {
            if(i < resourceList.length) {

                VBox element = new VBox(elementsPerPage);
                element.setId(String.valueOf(i));
                ImageView image = new ImageView();
                try {
                    image.setImage(new Image(getResourceManager().
                            getImageURL(resourceList[i].getThumbImage())));
                } catch (SQLException e) {
                    image.setImage(new Image("/Resources/laptopIcon.png"));
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
