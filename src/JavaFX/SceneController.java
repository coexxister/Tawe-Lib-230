package JavaFX;

import Core.AccountManager;
import Core.DatabaseManager;
import Core.ResourceFlowManager;
import Core.ResourceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

/**
 * Superclass of FXML controllers for the user interface.
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class SceneController {

    public static final String MAIN_INTERFACE = "/View/LoginInterface.fxml";
    public static final String HOME_INTERFACE = "/View/HomeInterface.fxml";
    public static final String USER_DASHBOARD_INTERFACE = "/View/UserDashboardInterface.fxml";

    public static final String STAFF_INTERFACE = "/View/StaffInterface.fxml";
    public static final String RESOURCE_INTERFACE = "/View/ResourceInterface.fxml";
    public static final String ACCOUNTS_PAGE_INTERFACE = "/View/AccountsPageInterface.fxml";
    public static final String ACCOUNT_CREATOR_INTERFACE = "/View/AccountsCreatorInterface.fxml";

    private static BorderPane mainPane = new BorderPane();
    private DatabaseManager db = new DatabaseManager("./TaweLibDB.db");
    private ResourceManager rm = new ResourceManager(db);
    private AccountManager am = new AccountManager(db);
    private ResourceFlowManager rfm;

    public void handleLoginButtonAction(ActionEvent event, String fxml, String loginUsername) throws Exception {
        Parent root = load(getClass().getResource(fxml));
        Scene userScreen = new Scene(root);
        mainPane = (BorderPane) root;

        rfm = new ResourceFlowManager(db, am, rm, Integer.parseInt(loginUsername));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show();
    }

    public void handleSceneChangeButtonAction(ActionEvent event, String fxml) throws IOException {
        Parent root = load(getClass().getResource(fxml));
        Scene userScreen = new Scene(root);
        mainPane = (BorderPane) root;

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show();
    }

    public void loadSubscene(String fxml) throws IOException {
        mainPane.setCenter(load(getClass().getResource(fxml)));
    }

    @FXML
    public void changeLogoutToHome(Button logoutButton){
        logoutButton.setText("Home");
    }

    public DatabaseManager getDatabase() {
        return db;
    }

    public AccountManager getAccountManager() { return am; }

    public ResourceManager getResourceManager(){ return rm;}
}
