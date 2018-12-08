package JavaFX;

import Core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservedInterfaceController extends SceneController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        FlowPane root = new FlowPane();

        //array of reserved copies
        Copy[] reservedCopies;

        //try to get all reserved copies
        System.out.println(getResourceFlowManager().getUserID());
        try {
            reservedCopies = getResourceFlowManager().getReservedCopies(getResourceFlowManager().getUserID());
        } catch (SQLException e) {
            reservedCopies = new Copy[0];
        }

        GridPane[] reservedPanes = new GridPane[reservedCopies.length];

        //for every reserved copy. Add the copy to the scroll pane.
        for (int iCount = 0; iCount < reservedCopies.length; iCount++) {

            Resource resource = null;
            String resourceOut = null;

            //try and get the resource object.
            try {
                resource = getResourceManager().getResourceList("SELECT * FROM Resource WHERE " +
                        "RID = " + Integer.toString(reservedCopies[iCount].getResourceID()))[0];

                //if the resource is not null then get the summary of the resource.
                if (resource != null) {
                    resourceOut = resource.toString();
                }
            } catch (SQLException e) {
                resourceOut = "";
            }

            //The styling of the label
            Label label = new Label("CopyID: " + reservedCopies[iCount].getCopyID() + "\n" + resourceOut);
            label.getStylesheets().add("/Resources/CoreStyle.css");
            label.getStyleClass().add("ScrollListItem");
            /*label.setMinHeight(300);*/
            label.setPrefWidth(400);
            label.setAlignment(Pos.CENTER);

            reservedPanes[iCount] = new GridPane();
            reservedPanes[iCount].add(label, 0, 0);

            //if the logged in user is a staff member who is managing another user then add an option to borrow
            if (getAccountManager().getAccount(SceneController.USER_ID) instanceof Staff) {
                Button borrowButton = new Button("Borrow");
                borrowButton.getStylesheets().add("/Resources/CoreStyle.css");
                borrowButton.getStyleClass().add("UniversalButton");

                Button unReserveButton = new Button("Un-Reserve");
                unReserveButton.getStylesheets().add("/Resources/CoreStyle.css");
                unReserveButton.getStyleClass().add("UniversalButton");

                reservedPanes[iCount].add(borrowButton, 1, 0);
                reservedPanes[iCount].add(unReserveButton, 1, 1);
            }


            reservedPanes[iCount].add(new Separator(), 0, 2);
            reservedPanes[iCount].add(new Separator(), 1, 2);
        }

        //add the labels to the scroll pane.
        root.getChildren().addAll(reservedPanes);
        //root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
    }

}