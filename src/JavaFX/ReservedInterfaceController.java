package JavaFX;

import Core.Copy;
import Core.LoanEvent;
import Core.Resource;
import Core.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

        VBox root = new VBox();


        Copy[] reservedCopies;

        try {
            reservedCopies = getResourceFlowManager().getReservedCopies(SceneController.USER_ID);
        } catch (SQLException e) {
            reservedCopies = new Copy[0];
        }

        Label[] copyText = new Label[reservedCopies.length];

        for (int iCount = 0; iCount < reservedCopies.length; iCount++) {

            Resource resource = null;
            String resourceOut = null;
            try {
                resource = getResourceManager().getResourceList("SELECT * FROM Resource WHERE " +
                        "RID = " + Integer.toString(reservedCopies[iCount].getResourceID()))[0];

                if (resource != null) {
                    resourceOut = resource.toString();
                }
            } catch (SQLException e) {
                resourceOut = "";
            }

            copyText[iCount] = new Label("CopyID: " + reservedCopies[iCount].getCopyID() + "\n" + resourceOut);
            copyText[iCount].getStylesheets().add("/Resources/CoreStyle.css");
            copyText[iCount].getStyleClass().add("ScrollListItem");
            copyText[iCount].setMinHeight(300);
            copyText[iCount].setPrefWidth(400);
            copyText[iCount].setAlignment(Pos.CENTER);
        }

        root.getChildren().addAll(copyText);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
    }

}