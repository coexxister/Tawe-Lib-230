package JavaFX;

import Core.Copy;
import Core.DateManager;
import Core.LoanEvent;
import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RequestedResourcesInterfaceController extends SceneController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox root = new VBox();

        Resource[] requestedResources = getResurceFlowManager().
                getRequestedResources(SceneController.USER_ID);

        Resource[] requestedHistory;

        //If returned resources is null, then create empty array to avoid null pointer exceptions.
        if (requestedResources == null) {
            requestedResources = new Resource[0];
        }

        GridPane[] titleContainer = new GridPane[requestedResources.length];
        //For every copy create the grid pane and the labels inside it.
        for (int iCount = 0; iCount < requestedResources.length; iCount++) {

            //The labels to be contained in the grid pane (copy container).
            Label titleLabel = new Label();

            //Setup the copy container and add styling.
            titleContainer[iCount] = new GridPane();
            titleContainer[iCount].getStylesheets().add("/Resources/CoreStyle.css");
            titleContainer[iCount].getStyleClass().add("ScrollListItem");
            titleContainer[iCount].setMinHeight(50);
            titleContainer[iCount].setMinWidth(300);
            titleContainer[iCount].setAlignment(Pos.CENTER);

            //Setup title label and styling.
            titleLabel.getStylesheets().add("/Resources/CoreStyle.css");
            titleLabel.getStyleClass().add("ScrollTitle");
            titleLabel.setMinHeight(100);
            titleLabel.setText(requestedResources[iCount].getTitle());

            //Set the titleLabel and contentLabel in the copyContainer.
            titleContainer[iCount].add(titleLabel, 1, 1);

        }

        root.getChildren().addAll(titleContainer);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);

    }

}