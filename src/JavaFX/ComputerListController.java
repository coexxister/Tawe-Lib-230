package JavaFX;

import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ComputerListController extends SceneController implements Initializable {

    @FXML
    private Pagination computerView;

    private double elementsPerPage = 3;
    private Resource resourceList[] = null;


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
        computerView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    public HBox createPage(int pageIndex) {
        HBox box = new HBox(elementsPerPage);
        computerView.setPageCount((int) (Math.ceil((double) resourceList.length / elementsPerPage)));
        int page = pageIndex * (int)elementsPerPage;
        for (int i = page; i < page + elementsPerPage; i++) {
            if(i < resourceList.length) {

                HBox element = new HBox(elementsPerPage);
                element.setId(String.valueOf(i));
                ImageView image = new ImageView("/Resources/laptopIcon.png");

                image.setFitWidth(100);
                image.setPreserveRatio(true);
                image.setSmooth(true);
                image.setCache(true);

                Label text = new Label(resourceList[i].toString());
                text.wrapTextProperty().setValue(true);
                Label numberOfCopies = new Label (String.valueOf(getResourceManager().getCopies(resourceList[i].getResourceID()).length));
                element.getChildren().addAll(image, text, numberOfCopies);
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
