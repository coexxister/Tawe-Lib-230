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
import java.util.ResourceBundle;

public class ListAllController extends SceneController implements Initializable {

    @FXML
    private Pagination resourceView;

    private int elementsPerPage = 3;

    public void initialize(URL location, ResourceBundle resources) {
        resourceView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    public HBox createPage(int pageIndex) {
        HBox box = new HBox(elementsPerPage);
        Resource resourceList[] = getResourceManager().getResourceList();
        resourceView.setPageCount(resourceList.length/elementsPerPage);
        int page = pageIndex * elementsPerPage;
        for (int i = page; i < page + elementsPerPage; i++) {
            HBox element = new HBox(elementsPerPage);
            ImageView image = new ImageView();
            if(resourceList[i].toString().contains("Type - Book")){
                image.setImage(new Image("/Resources/bookIcon.png"));
            } else if(resourceList[i].toString().contains("Type - Dvd")){
                image.setImage(new Image("/Resources/dvdIcon.png"));
            } else if(resourceList[i].toString().contains("Type - Computer")){
                image.setImage(new Image("/Resources/laptopIcon.png"));
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
            element.setPadding(new Insets(100,0,0,0));
            box.getChildren().add(element);
        }
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
