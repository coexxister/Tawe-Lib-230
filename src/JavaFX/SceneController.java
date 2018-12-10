package JavaFX;

import Core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

	/**
	 * File path for main interface FXML.
	 */
	private static final String MAIN_INTERFACE = "/View/LoginInterface.fxml";
	/**
	 * File path for home interface FXML.
	 */
	private static final String HOME_INTERFACE = "/View/HomeInterface.fxml";
	/**
	 * File path for user dashboard interface FXML.
	 */
	private static final String USER_DASHBOARD_INTERFACE = "/View/UserDashboardInterface.fxml";
	/**
	 * File path for popular listing interface FXML.
	 */
	private static final String POPULAR_LISTING = "/View/PopularListing.fxml";
	/**
	 * File path for staff interface FXML.
	 */
	private static final String STAFF_INTERFACE = "/View/StaffInterface.fxml";
	/**
	 * File path for resource interface FXML.
	 */
	private static final String RESOURCE_INTERFACE = "/View/ResourceInterface.fxml";
	/**
	 * File path for add book interface FXML.
	 */
	private static final String ADD_BOOK_INTERFACE = "/View/AddBookInterface.fxml";
	/**
	 * File path for add DVD interface FXML.
	 */
	private static final String ADD_DVD_INTERFACE = "/View/AddDVDInterface.fxml";
	/**
	 * File path for add laptop interface FXML.
	 */
	private static final String ADD_LAPTOP_INTERFACE = "/View/AddLaptopInterface.fxml";
	/**
	 * File path for book list interface FXML.
	 */
	private static final String BOOK_LIST_INTERFACE = "/View/BookList.fxml";
	/**
	 * File path for computer list interface FXML.
	 */
	private static final String COMPUTER_LIST_INTERFACE = "/View/ComputerList.fxml";
	/**
	 * File path for book search interface FXML.
	 */
	private static final String BOOK_SEARCH_INTERFACE = "/View/BookSearchMenu.fxml";
	/**
	 * File path for DVD search interface FXML.
	 */
	private static final String DVD_SEARCH_INTERFACE = "/View/DVDSearchMenu.fxml";
	/**
	 * File path for laptop search interface FXML.
	 */
	private static final String LAPTOP_SEARCH_INTERFACE = "/View/ComputerSearchMenu.fxml";
	/**
	 * File path for list all interface FXML.
	 */
	private static final String LIST_ALL_INTERFACE = "/View/ListAll.fxml";
	/**
	 * File path for resource log interface FXML.
	 */
	private static final String RESOURCE_LOG_INTERFACE = "/View/ResourceLogInterface.fxml";
	/**
	 * File path for accounts search interface FXML.
	 */
	private static final String ACCOUNTS_SEARCH_INTERFACE = "/View/AccountSearchInterface.fxml";
	/**
	 * File path for account creator interface FXML.
	 */
	private static final String ACCOUNT_CREATOR_INTERFACE = "/View/AccountCreatorInterface.fxml";
	/**
	 * File path for account editor interface FXML.
	 */
	private static final String ACCOUNT_EDITOR_INTERFACE = "/View/AccountEditorInterface.fxml";
	/**
	 * File path for avatar change interface FXML.
	 */
	private static final String AVATAR_CHANGE_INTERFACE = "/View/ProfileImageSelector.fxml";
	/**
	 * File path for drawing environment interface FXML.
	 */
	private static final String DRAWING_INTERFACE = "/View/DrawingEnvironment.fxml";
	/**
	 * File path for transaction history interface FXML.
	 */
	private static final String TRANSACTION_HISTORY_INTERFACE = "/View/TransactionHistoryPage.fxml";
	/**
	 * File path for loan history interface FXML.
	 */
	private static final String LOAN_HISTORY_CONTROLLER = "/View/LoanHistory.fxml";
	/**
	 * File path for reserve history interface FXML.
	 */
	private static final String RESERVE_HISTORY_CONTROLLER = "/View/ReservedInterface.fxml";
	/**
	 * File path for items due interface FXML.
	 */
	private static final String ITEMS_DUE = "/View/ItemsDueInterface.fxml";
	/**
	 * File path for requested resource interface FXML.
	 */
	private static final String REQUESTED_RESOURCE = "/View/RequestedResourcesInterface.fxml";
	/**
	 * File path for resource flow manager interface FXML.
	 */
	private static final String RESOURCE_FLOW_INTERFACE = "/View/ResourceFlowInterface.fxml";
	/**
	 * File path for transaction manager interface FXML.
	 */
	private static final String TRANSACTION_MANAGER_INTERFACE = "/View/TransactionManager.fxml";
	/**
	 * File path for copy log interface FXML.
	 */
	private static final String COPY_LOG_INTERFACE = "/View/CopyLogInterface.fxml";
	/**
	 * File path for list overdue copies interface FXML.
	 */
	private static final String LIST_OVERDUE_COPIES_INTERFACE = "/View/ListAllOverdueCopiesInterface.fxml";
	/**
	 * Instance of DatabaseManager class for use with other interfaces.
	 */
	private static final DatabaseManager db = new DatabaseManager("./TaweLibDB.db");
	/**
	 * Instance of ResourceManager class for use with other interfaces.
	 */
	private static final ResourceManager rm = new ResourceManager(db);
	/**
	 * Instance of AccountManager class for use with other interfaces.
	 */
	private static final AccountManager am = new AccountManager(db);
	/**
	 * User ID used in various methods.
	 */
	public static int USER_ID;
	/**
	 * Instance of ResourceFlowManager class for use with other interfaces.
	 */
	private static final ResourceFlowManager rfm = new ResourceFlowManager(db, am, rm, USER_ID);
	/**
	 * Query for requesting resource and the requested resource.
	 */
	private static String sqlQuery;
	/**
	 * Requested resource used in various subclasses.
	 */
	private static Resource requestResource;
	/**
	 * Main BorderPane of the interface for changing scenes.
	 */
	private static BorderPane mainPane = new BorderPane();

	/**
	 * Getter for main interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getMainInterface() {
		return MAIN_INTERFACE;
	}

	/**
	 * Getter for home interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getHomeInterface() {
		return HOME_INTERFACE;
	}

	/**
	 * Getter for user dashboard interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getUserDashboardInterface() {
		return USER_DASHBOARD_INTERFACE;
	}

	/**
	 * Getter for popular listing interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getPopularListing() {
		return POPULAR_LISTING;
	}

	/**
	 * Getter for staff interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getStaffInterface() {
		return STAFF_INTERFACE;
	}

	/**
	 * Getter for resource interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getResourceInterface() {
		return RESOURCE_INTERFACE;
	}

	/**
	 * Getter for accounts search interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAccountsSearchInterface() {
		return ACCOUNTS_SEARCH_INTERFACE;
	}

	/**
	 * Getter for account creator interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAccountCreatorInterface() {
		return ACCOUNT_CREATOR_INTERFACE;
	}

	/**
	 * Getter for account editor interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAccountEditorInterface() {
		return ACCOUNT_EDITOR_INTERFACE;
	}

	/**
	 * Getter for avatar change interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAvatarChangeInterface() {
		return AVATAR_CHANGE_INTERFACE;
	}

	/**
	 * Getter for drawing interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getDrawingInterface() {
		return DRAWING_INTERFACE;
	}

	/**
	 * Getter for transaction history interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getTransactionHistoryInterface() {
		return TRANSACTION_HISTORY_INTERFACE;
	}

	/**
	 * Getter for loan history interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getLoanHistoryController() {
		return LOAN_HISTORY_CONTROLLER;
	}

	/**
	 * Getter for reserve history interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getReserveHistoryController() {
		return RESERVE_HISTORY_CONTROLLER;
	}

	/**
	 * Getter for items due interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getItemsDue() {
		return ITEMS_DUE;
	}

	/**
	 * Getter for requested resource interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getRequestedResource() {
		return REQUESTED_RESOURCE;
	}

	/**
	 * Getter for resource flow interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getResourceFlowInterface() {
		return RESOURCE_FLOW_INTERFACE;
	}

	/**
	 * Getter for transaction manager interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getTransactionManagerInterface() {
		return TRANSACTION_MANAGER_INTERFACE;
	}

	/**
	 * Getter for copy log interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getCopyLogInterface() {
		return COPY_LOG_INTERFACE;
	}

	/**
	 * Getter for add book interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAddBookInterface() {
		return ADD_BOOK_INTERFACE;
	}

	/**
	 * Getter for add DVD interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAddDvdInterface() {
		return ADD_DVD_INTERFACE;
	}

	/**
	 * Getter for add laptop interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getAddLaptopInterface() {
		return ADD_LAPTOP_INTERFACE;
	}

	/**
	 * Getter for list overdue copies interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getListOverdueCopiesInterface() {
		return LIST_OVERDUE_COPIES_INTERFACE;
	}

	/**
	 * Getter for resource log interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getResourceLogInterface() {
		return RESOURCE_LOG_INTERFACE;
	}

	/**
	 * Getter for book list interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getBookListInterface() {
		return BOOK_LIST_INTERFACE;
	}

	/**
	 * Getter for laptop list interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getComputerListInterface() {
		return COMPUTER_LIST_INTERFACE;
	}

	/**
	 * Getter for book search interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getBookSearchInterface() {
		return BOOK_SEARCH_INTERFACE;
	}

	/**
	 * Getter for DVD search interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getDvdSearchInterface() {
		return DVD_SEARCH_INTERFACE;
	}

	/**
	 * Getter for laptop search interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getLaptopSearchInterface() {
		return LAPTOP_SEARCH_INTERFACE;
	}

	/**
	 * Getter for list all interface FXML path.
	 *
	 * @return The FXML path.
	 */
	public static String getListAllInterface() {
		return LIST_ALL_INTERFACE;
	}

	/**
	 * Getter for resource database manager instance.
	 *
	 * @return The database manager instance.
	 */
	public static DatabaseManager getDatabase() {
		return db;
	}

	/**
	 * Getter for account manager instance.
	 *
	 * @return The account manager instance.
	 */
	public static AccountManager getAccountManager() {
		return am;
	}

	/**
	 * Getter for the resource flow manager instance.
	 *
	 * @return The resource flow manager instance.
	 */
	public static ResourceFlowManager getResourceFlowManager() {
		return rfm;
	}

	/**
	 * Getter for the resource manager instance.
	 *
	 * @return The resource manager instance.
	 */
	public static ResourceManager getResourceManager() {
		return rm;
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
	 * Getter for the SQL query.
	 *
	 * @return The SQL query.
	 */
	public String getSqlQuery() {
		return sqlQuery;
	}

	/**
	 * Setter for the SQL query.
	 *
	 * @param newSqlQuery The SQL query of which the value is to be assigned to the SQL query in SceneController.
	 */
	public void setSqlQuery(String newSqlQuery) {
		sqlQuery = newSqlQuery;
	}

	/**
	 * Getter for requested resource.
	 *
	 * @return Requested resource.
	 */
	public Resource getRequestResource() {
		return requestResource;
	}

	/**
	 * Setter for requested resource.
	 *
	 * @param newRequestResource The requested resource.
	 */
	public void setRequestResource(Resource newRequestResource) {
		requestResource = newRequestResource;
	}

	/**
	 * Method for calling the setter of requested resource when it is clicked on the screen and
	 * move to the requesting scene.
	 *
	 * @param resourceList List of resources.
	 * @param element      Requested resource.
	 */
	public void getOnMouseClicked(Resource[] resourceList, VBox element) {
		element.getStylesheets().add("/Resources/CoreStyle.css");
		element.getStyleClass().add("UniversalButton");
		element.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
			setRequestResource(resourceList[Integer.parseInt(element.getId())]);
			loadSubscene("/View/RequestResourceByUserInterface.fxml");
		});
	}

	/**
	 * Gets available number of copies for a resource.
	 *
	 * @param resourceNumber The ID of the resource.
	 * @return The number of available copies for the resource.
	 */
	public String getAvailableNumberOfCopies(Resource resourceNumber) {
		String availability = "Available copies: ";
		int availabilityCounter = 0;
		Copy[] copies = getResourceManager().getCopies(resourceNumber.getResourceID());
		for (Copy copy : copies) {
			if (copy.getStateID() == 0) {
				availabilityCounter++;
			}
		}
		availability += availabilityCounter;
		return availability;
	}
}
