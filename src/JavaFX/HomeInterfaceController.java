package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the main user interface home scene
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class HomeInterfaceController extends SceneController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameDisplay;

    @FXML
    private ImageView avatarImage;

    @FXML
    private Label currentBalance;

    /**
     * Handles the action of clicking the button to open the user dashboard interface
     * @param event the event triggered by clicking the button
     * @throws IOException thrown when?
     */
    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        handleSceneChangeButtonAction(event, SceneController.USER_DASHBOARD_INTERFACE);
    }

    /**
     * Handles the action of clicking the button to logout of the user interface, or to return to the home scene if another scene is displayed 
     * @param event the event triggered by clicking the button
     * @throws Exception thrown if no such interface exists
     */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws Exception {
        if(logoutButton.getText().equals("Logout")) {
            handleSceneChangeButtonAction(event, SceneController.MAIN_INTERFACE);
        } else {
            handleSceneChangeButtonAction(event, SceneController.HOME_INTERFACE);
        }
    }

    /**
     * Handles the action of clicking the button to load the book menu
     * @param event the event triggered by clicking the button
     * @throws IOException thrown if the fxml file for the scene does not exist
     */
    @FXML
    public void handleBookMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/BookSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    /**
     * Handles the action of clicking the button to load the DVD menu
     * @param event the event triggered by clicking the button
     * @throws IOException thrown if the fxml file for the scene does not exist
     */
    @FXML
    public void handleDVDMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/DVDSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    /**
     * Handles the action of clicking the button to load the laptop menu
     * @param event the event triggered by clicking the button
     * @throws IOException thrown if the fxml file for the scene does not exist
     */
    @FXML
    public void handleLaptopMenuButtonAction(ActionEvent event) throws IOException {
        loadSubscene("/View/LaptopSearchMenu.fxml");
        changeLogoutToHome(logoutButton);
    }

    /**
     * Handles the action of clicking the button to list all resources
     * @param event the event triggered by clicking the button
     * @throws IOException thrown if the fxml file for the scene does not exist
     */
    @FXML
    public void handleListAllButtonAction(ActionEvent event) throws IOException{
        loadSubscene("/View/ListAll.fxml");
        changeLogoutToHome(logoutButton);
    }

    /**
     * Changes the text of the button to logout to say "Home"
     * @param logoutButton the button to logout
     */
    @FXML
    public void changeLogoutToHome(Button logoutButton){
        logoutButton.setText("Home");
    }

    private void updateBalanceLabel() {

        float balance;
        try {
            //get balance and round to 2 decimal places.
            balance = Math.round(getAccountManager().getAccountBalance(SceneController.USER_ID)*100)/100;
        } catch (SQLException e) {
            balance = 0.0F;
        }

        currentBalance.setText("£" + balance);

        //if the balance is less than 0, then change background color to red. Otherwise change to green.
        if (balance < 0) {
            currentBalance.setStyle("-fx-background-color: #ff644e; -fx-text-fill: WHITE;");
        } else {
            currentBalance.setStyle("-fx-background-color: #228022; -fx-text-fill: WHITE;");
        }
    }

    /**
     * Initialises the label in the interface to display the first name of the user based on the account currently logged in
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameDisplay.setText("Welcome, " + getAccountManager().
                getAccount(SceneController.USER_ID).getFirstName());

        try {
            avatarImage.setImage(new Image(getResourceManager().getImageURL(getAccountManager().getAccount(SceneController.USER_ID).getAvatarID())));
        } catch (SQLException e) {
            avatarImage.setImage(new Image("/DefaultAvatar/Avatar1.png"));
        }

        updateBalanceLabel();
    }
}
