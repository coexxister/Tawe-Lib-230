package JavaFX;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

/**
 * Superclass of FXML controllers for the user interface.
 *
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class SceneController {

    public static int USER_ID;

    //File paths for interface FXML files
    private static final String MAIN_INTERFACE = "/View/LoginInterface.fxml";
    private static final String HOME_INTERFACE = "/View/HomeInterface.fxml";
    private static final String USER_DASHBOARD_INTERFACE = "/View/UserDashboardInterface.fxml";
    private static final String POPULAR_LISTING = "/View/PopularListing.fxml";
    private static final String STAFF_INTERFACE = "/View/StaffInterface.fxml";
    private static final String RESOURCE_INTERFACE = "/View/ResourceInterface.fxml";
    private static final String ADD_BOOK_INTERFACE = "/View/AddBookInterface.fxml";
    private static final String ADD_DVD_INTERFACE = "/View/AddDVDInterface.fxml";
    private static final String ADD_LAPTOP_INTERFACE = "/View/AddLaptopInterface.fxml";
    private static final String RESOURCE_LOG_INTERFACE = "/View/ResourceLogInterface.fxml";
    private static final String ACCOUNTS_SEARCH_INTERFACE = "/View/AccountSearchInterface.fxml";
    private static final String ACCOUNT_CREATOR_INTERFACE = "/View/AccountCreatorInterface.fxml";
    private static final String ACCOUNT_EDITOR_INTERFACE = "/View/AccountEditorInterface.fxml";
    private static final String AVATAR_CHANGE_INTERFACE = "/View/ProfileImageSelector.fxml";
    private static final String DRAWING_INTERFACE = "/View/DrawingEnvironment.fxml";
    private static final String TRANSACTION_HISTORY_INTERFACE = "/View/TransactionHistoryPage.fxml";
    private static final String LOAN_HISTORY_CONTROLLER = "/View/LoanHistory.fxml";
    private static final String RESERVE_HISTORY_CONTROLLER = "/View/ReservedInterface.fxml";
    private static final String ITEMS_DUE = "/View/ItemsDueInterface.fxml";
    private static final String REQUESTED_RESOURCE = "/View/RequestedResourcesInterface.fxml";
    private static final String RESOURCE_FLOW_INTERFACE = "/View/ResourceFlowInterface.fxml";
    private static final String TRANSACTION_MANAGER_INTERFACE = "/View/TransactionManager.fxml";
    private static final String COPY_LOG_INTERFACE = "/View/CopyLogInterface.fxml";
    private static final String LIST_OVER_DUE_COPIES = "/View/ListAllOverdueCopiesInterface.fxml";

    //Query for requesting resource and the requested resource.
    private static String sqlQuery;
    private static Resource requestResource;

    //Main BorderPane of the interface for changing scenes
    private static BorderPane mainPane = new BorderPane();

    //Instances of manager classes for use with staff interface
    private static DatabaseManager db = new DatabaseManager("./TaweLibDB.db");
    private static ResourceManager rm = new ResourceManager(db);
    private static AccountManager am = new AccountManager(db);
    private static ResourceFlowManager rfm = new ResourceFlowManager(db, am, rm, USER_ID);

    /**
     * Getter for main interface FXML path
     *
     * @return MAIN_INTERFACE the FXML path
     */
    public static String getMainInterface() {
        return MAIN_INTERFACE;
    }

    /**
     * Getter for home interface FXML path
     *
     * @return HOME_INTERFACE the FXML path
     */
    public static String getHomeInterface() {
        return HOME_INTERFACE;
    }

    /**
     * Getter for user dashboard interface FXML path
     *
     * @return USER_DASHBOARD_INTERFACE the FXML path
     */
    public static String getUserDashboardInterface() {
        return USER_DASHBOARD_INTERFACE;
    }

    /**
     * Getter for popular listing interface FXML path
     *
     * @return POPULAR_LISTING the FXML path
     */
    public static String getPopularListing() {
        return POPULAR_LISTING;
    }

    /**
     * Getter for staff listing interface FXML path
     *
     * @return STAFF_INTERFACE the FXML path
     */
    public static String getStaffInterface() {
        return STAFF_INTERFACE;
    }

    /**
     * Getter for resource interface FXML path
     *
     * @return RESOURCE_INTERFACE the FXML path
     */
    public static String getResourceInterface() {
        return RESOURCE_INTERFACE;
    }

    /**
     * Getter for accounts search interface FXML path
     *
     * @return ACCOUNTS_SEARCH_INTERFACE the FXML path
     */
    public static String getAccountsSearchInterface() {
        return ACCOUNTS_SEARCH_INTERFACE;
    }

    /**
     * Getter for account creator interface FXML path
     *
     * @return ACCOUNT_CREATOR_INTERFACE the FXML path
     */
    public static String getAccountCreatorInterface() {
        return ACCOUNT_CREATOR_INTERFACE;
    }

    /**
     * Getter for account editor interface FXML path
     *
     * @return ACCOUNT_EDITOR_INTERFACE the FXML path
     */
    public static String getAccountEditorInterface() {
        return ACCOUNT_EDITOR_INTERFACE;
    }

    /**
     * Getter for avatar change interface FXML path
     *
     * @return AVATAR_CHANGE_INTERFACE the FXML path
     */
    public static String getAvatarChangeInterface() {
        return AVATAR_CHANGE_INTERFACE;
    }

    /**
     * Getter for drawing interface FXML path
     *
     * @return DRAWING_INTERFACE the FXML path
     */
    public static String getDrawingInterface() {
        return DRAWING_INTERFACE;
    }

    /**
     * Getter for transaction history interface FXML path
     *
     * @return TRANSACTION_HISTORY_INTERFACE the FXML path
     */
    public static String getTransactionHistoryInterface() {
        return TRANSACTION_HISTORY_INTERFACE;
    }

    /**
     * Getter for loan history interface FXML path
     *
     * @return LOAN_HISTORY_INTERFACE the FXML path
     */
    public static String getLoanHistoryController() {
        return LOAN_HISTORY_CONTROLLER;
    }

    /**
     * Getter for reserve history interface FXML path
     *
     * @return RESERVE_HISTORY_INTERFACE the FXML path
     */
    public static String getReserveHistoryController() {
        return RESERVE_HISTORY_CONTROLLER;
    }

    /**
     * Getter for items due interface FXML path
     *
     * @return ITEMS_DUE_INTERFACE the FXML path
     */
    public static String getItemsDue() {
        return ITEMS_DUE;
    }

    /**
     * Getter for requested resource interface FXML path
     *
     * @return REQUESTED_RESOURCE the FXML path
     */
    public static String getRequestedResource() {
        return REQUESTED_RESOURCE;
    }

    /**
     * Getter for resource flow interface FXML path
     *
     * @return RESOURCE_FLOW_INTERFACE the FXML path
     */
    public static String getResourceFlowInterface() {
        return RESOURCE_FLOW_INTERFACE;
    }

    /**
     * Getter for transaction manager interface FXML path
     *
     * @return TRANSACTION_MANAGER_INTERFACE the FXML path
     */
    public static String getTransactionManagerInterface() {
        return TRANSACTION_MANAGER_INTERFACE;
    }

    /**
     * Getter for copy log interface FXML path
     *
     * @return COPY_LOG_INTERFACE the FXML path
     */
    public static String getCopyLogInterface() {
        return COPY_LOG_INTERFACE;
    }

    /**
     * Getter for add book interface FXML path
     *
     * @return ADD_BOOK_INTERFACE the FXML path
     */
    public static String getAddBookInterface() {
        return ADD_BOOK_INTERFACE;
    }

    /**
     * Getter for add DVD interface FXML path
     *
     * @return ADD_DVD_INTERFACE the FXML path
     */
    public static String getAddDvdInterface() {
        return ADD_DVD_INTERFACE;
    }

    /**
     * Getter for add laptop interface FXML path
     *
     * @return ADD_LAPTOP_INTERFACE the FXML path
     */
    public static String getAddLaptopInterface() {
        return ADD_LAPTOP_INTERFACE;
    }

    /**
     * Getter for list over due copies interface FXML path.
     * @return The fxml path for list over due copies interface.
     */
    public static String getListOverDueCopies() {
        return LIST_OVER_DUE_COPIES;
    }

    /**
     * Getter for resource log interface FXML path.
     * @return RESOURCE_LOG_INTERFACE the FXML path.
     */
    public static String getResourceLogInterface() {
        return RESOURCE_LOG_INTERFACE;
    }

    /**
     * Handles the action of clicking a button to change scenes.
     *
     * @param event the event triggered by clicking the button.
     * @param fxml  the fxml file corresponding to the new scene.
     */
    public void handleSceneChangeButtonAction(ActionEvent event, String fxml) {
        Parent root = null;
        try {
            root = load(getClass().getResource(fxml));
        } catch (IOException e) {
            System.out.println("Couldn't set scene.");
        }
        Scene userScreen = new Scene(root);
        mainPane = (BorderPane) root;

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show();
    }

    /**
     * Sets the mainPane to a different scene.
     *
     * @param fxml the fxml file corresponding to the new scene.
     */
    public void loadSubscene(String fxml) {
        try {
            mainPane.setCenter(load(getClass().getResource(fxml)));
        } catch (IOException e) {
            System.out.println("Couldn't load specified subscene.");
        }
    }

    /**
     * Changes the text of the button to logout to say "Home".
     *
     * @param logoutButton the button to logout.
     */
    @FXML
    public void changeLogoutToHome(Button logoutButton) {
        logoutButton.setText("Home");
    }

    /**
     * Getter for resource database.
     *
     * @return db the database.
     */
    public static DatabaseManager getDatabase() {
        return db;
    }

    /**
     * Getter for account manager.
     *
     * @return am the account manager.
     */
    public static AccountManager getAccountManager() {
        return am;
    }

    /**
     * Gets the resource flow manager instance.
     *
     * @return The resource flow manager instance.
     */
    public static ResourceFlowManager getResourceFlowManager() {
        return rfm;
    }

    /**
     * Getter for resource manager
     *
     * @return rm the resource manager
     */
    public static ResourceManager getResourceManager() {
        return rm;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String newSqlQuery) {
        sqlQuery = newSqlQuery;
    }

    /**
     * Getter for searched resource.
     *
     * @return Requested resource.
     */
    public Resource getRequestResource() {
        return requestResource;
    }

    /**
     * Setter of searched resource.
     *
     * @param newRequestResource Searched resource.
     */
    public void setRequestResource(Resource newRequestResource) {
        requestResource = newRequestResource;
    }

    /**
     * Method for calling the setter of searched resource when it is clicked on the screen and move to the requesting scene.
     *
     * @param resourceList List of resources.
     * @param element      Searched resource.
     */
    public void getOnMouseClicked(Resource[] resourceList, VBox element) {
        element.getStylesheets().add("/Resources/CoreStyle.css");
        element.getStyleClass().add("UniversalButton");
        element.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            setRequestResource(resourceList[Integer.parseInt(element.getId())]);
            loadSubscene("/View/RequestResourceByUserInterface.fxml");
        });
    }

    public String getAvailableNumberOfCopies(Resource resourceNumber){
        String availability = "Available copies: ";
        int availabilityCounter = 0;
        Copy[] copies = getResourceManager().getCopies(resourceNumber.getResourceID());
        for(Copy copy: copies){
            if(copy.getStateID() == 0) {
                availabilityCounter++;
            }
        }
        availability += availabilityCounter;
        return availability;
    }
}
