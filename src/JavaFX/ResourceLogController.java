package JavaFX;

import Core.LoanEvent;
import Core.Resource;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class ResourceLogController extends SceneController {

    @FXML
    ScrollPane scrollPane = new ScrollPane();


    public void setReservedListing() {

        FlowPane root = new FlowPane();

        //array of resource log
        LoanEvent[] resourceLog;

        //try to get all reserved copies
        System.out.println(getResourceFlowManager().getUserID());
        resourceLog = getResourceFlowManager().getBorrowHistoryByResource(getRequestResource().getResourceID());

        Label[] resourceLogLabel = new Label[resourceLog.length];

        //for every reserved copy. Add the copy to the scroll pane.
        for (int iCount = 0; iCount < resourceLog.length; iCount++) {

            resourceLogLabel[iCount] = new Label(resourceLog.toString());

            //The styling of the label
            resourceLogLabel[iCount].getStylesheets().add("/Resources/CoreStyle.css");
            resourceLogLabel[iCount].getStyleClass().add("ScrollListItem");
            /*label.setMinHeight(300);*/
            resourceLogLabel[iCount].setPrefWidth(380);
            resourceLogLabel[iCount].setAlignment(Pos.CENTER);

        }

        //add the labels to the scroll pane.
        root.getChildren().addAll(resourceLogLabel);
        //root.setSpacing(10);
        scrollPane.setContent(root);

    }
}
