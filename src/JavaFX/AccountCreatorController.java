package JavaFX;

import Core.DateManager;
import Core.Staff;
import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Interface controller for the account creator interface
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class AccountCreatorController extends SceneController {
    FileChooser avatarChooser = new FileChooser();

    @FXML
    private TextField firstName, surname, streetName, streetNumber, city, county, postCode, phoneNumber, balance;

    @FXML
    private ImageView avatar;

    @FXML
    private RadioButton user, staff;

    private int avatarID; //Needed to instantiate User/Staff; need to add method to get the right ID

    public void handleCreateUserButtonAction(ActionEvent event) {
        if (user.isSelected()) {
            User newAccount = new User(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    avatarID);
            try {
                int userID = getAccountManager().addAccount(newAccount);
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else if (staff.isSelected()) {
            Staff newAccount = new Staff(0, firstName.getText(), surname.getText(), phoneNumber.getText(),
                    streetNumber.getText(), streetName.getText(), county.getText(), city.getText(), postCode.getText(),
                    DateManager.returnCurrentDate(), 0, avatarID);
            try {
                int userID = getAccountManager().addAccount(newAccount)[0];
                getAccountManager().changeBalance(userID, Float.parseFloat(balance.getText()));
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //not specified method, should set avatar, is here only to make the scene work
    public void setAvatar(){

    }
}
