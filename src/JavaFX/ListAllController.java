package JavaFX;

import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ListAllController extends SceneController implements Initializable {

    @FXML
    private Pagination resourceView;

    public void initialize(URL location, ResourceBundle resources) {
        resourceView.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    public Pane createPage(int pageIndex) {
        Pane pane = new Pane();
        HBox box = new HBox(3);
        Resource resourceList[] = getResourceManager().getResourceList();
        int page = pageIndex * 3;
        for (int i = page; i < page + 3; i++) {
            HBox element = new HBox();
            Label link = new Label("Item " + (i + 1));
            Label text = new Label(resourceList[i].toString());
            element.getChildren().addAll(link, text);
            box.getChildren().add(element);
        }
        resourceView.setPageCount(resourceList.length/3);
        pane.getChildren().add(box);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(100,10,0,10));
        return pane;
    }
}
