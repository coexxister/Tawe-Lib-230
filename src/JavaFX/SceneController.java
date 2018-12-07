package JavaFX;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.fxml.FXMLLoader.load;

/**
 * Superclass of FXML controllers for the user interface.
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class SceneController {

    public static int USER_ID;

    //File paths for user interface FXML files
    public static final String MAIN_INTERFACE = "/View/LoginInterface.fxml";
    public static final String HOME_INTERFACE = "/View/HomeInterface.fxml";
    public static final String USER_DASHBOARD_INTERFACE = "/View/UserDashboardInterface.fxml";
    public static final String POPULAR_LISTING = "/View/PopularListing.fxml";

    //File paths for staff interface FXML files
    public static final String STAFF_INTERFACE = "/View/StaffInterface.fxml";
    public static final String RESOURCE_INTERFACE = "/View/ResourceInterface.fxml";
    public static final String ACCOUNTS_SEARCH_INTERFACE = "/View/AccountSearchInterface.fxml";
    public static final String ACCOUNT_CREATOR_INTERFACE = "/View/AccountCreatorInterface.fxml";
    public static final String AVATAR_CHANGE_INTERFACE ="/View/ProfileImageSelector.fxml";
    public static final String DRAWING_INTERFACE = "/View/DrawingEnvironment.fxml";
    public static final String TRANSACTION_HISTORY_INTERFACE = "/View/TransactionHistoryPage.fxml";
    public static final String LOAN_HISTORY_CONTROLLER = "/View/LoanHistory.fxml";
    public static final String RESERVE_HISTORY_CONTROLLER = "/View/ReservedInterface.fxml";
    public static final String ISSUES_INTERFACE = "/View/IssuesInterface.fxml";
    public static final String ITEMS_DUE = "/View/ItemsDueInterface.fxml";
    public static final String REQUESTED_RESOURCE = "/View/RequestedResourcesInterface.fxml";

    public ArrayList<String> column = new ArrayList<>();
    public ArrayList<String> input = new ArrayList<>();

    //Main BorderPane of the interface for changing scenes
    private static BorderPane mainPane = new BorderPane();

    //Instances of manager classes for use with staff interface
    private DatabaseManager db = new DatabaseManager("./TaweLibDB.db");
    private ResourceManager rm = new ResourceManager(db);
    private AccountManager am = new AccountManager(db);
    private ResourceFlowManager rfm = new ResourceFlowManager(db, am, rm, USER_ID);

    /**
     * Handles the action of clicking a button to change scenes
     * @param event the event triggered by clicking the button
     * @param fxml the fxml file corresponding to the new scene
     * @throws IOException thrown if the fxml file does not exist
     */
    public void handleSceneChangeButtonAction(ActionEvent event, String fxml) throws IOException {
        Parent root = load(getClass().getResource(fxml));
        Scene userScreen = new Scene(root);
        mainPane = (BorderPane) root;

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show();
    }

    /**
     * Sets the mainPane to a different scene
     * @param fxml the fxml file corresponding to the new scene
     * @throws IOException thrown if the fxml file does not exist
     */
    public void loadSubscene(String fxml) throws IOException {
        mainPane.setCenter(load(getClass().getResource(fxml)));
    }

    /**
     * Changes the text of the button to logout to say "Home"
     * @param logoutButton the button to logout
     */
    @FXML
    public void changeLogoutToHome(Button logoutButton){
        logoutButton.setText("Home");
    }

    /**
     * Getter for resource database
     * @return db
     * 			the database
     */
    public DatabaseManager getDatabase() {
        return db;
    }

    /**
     * Getter for account manager
     * @return am
     * 			the account manager
     */
    public AccountManager getAccountManager() { return am; }

    /**
     * Gets the resource flow manager instance.
     * @return The resource flow manager instance.
     */
    public ResourceFlowManager getResurceFlowManager() {
        return rfm;
    }

    /**
     * Getter for resource manager
     * @return rm
     * 			the resource manager
     */
    public ResourceManager getResourceManager(){ return rm;}

    public String[] getColumn(){
        return column.toArray(new String[column.size()]);
    }

    public String[] getInput(){
        return input.toArray(new String[input.size()]);
    }

    public void addColumn(String columnInput){
        column.add(columnInput);
    }

    public void addInput(String userInput){
        input.add(userInput);
    }
}
