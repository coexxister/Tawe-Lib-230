package JavaFX;

import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ProfileImageSelectorController extends SceneController {

    Path selectedPath;

    FileChooser avatarChooser = new FileChooser();

    @FXML
    private void selectDefault1(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar1.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault2(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar2.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault3(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar3.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault4(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar4.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault5(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar5.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault6(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar6.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault7(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar7.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault8(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar8.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault9(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar9.png");
        setAvatar(false);
    }

    @FXML
    private void selectDefault10(ActionEvent event) {
        selectedPath = Paths.get( "/DefaultAvatars/Avatar10.png");
        setAvatar(false);
    }

    @FXML
    private void selectCustomAvatar(ActionEvent event) {
        avatarChooser.setInitialDirectory(new File("src/CustomAvatars"));
        Node node = (Node) event.getSource();
        File file  = avatarChooser.showOpenDialog(node.getScene().getWindow());
        selectedPath = Paths.get(file.getAbsolutePath());

        setAvatar(true);
    }

    private void setAvatar(boolean isCustom) {
        try {

            //Get the user account
            User account = getAccountManager().getAccount(SceneController.USER_ID);

            //Make sure the image path uses foreword slash
            String path = selectedPath.toString();
            JOptionPane.showMessageDialog(null, "Avatar Set", "Avatar Set", JOptionPane.INFORMATION_MESSAGE);
            path = path.replace("\\","/");

            //Create a relative path rather than absolute if custom image selected
            if (isCustom) {
                //the number of characters in 'src' to increase the index by.
                final int LENGTH_OF_SRC = 3;
                path = path.substring(path.indexOf("src") + LENGTH_OF_SRC);
            }

            int avatarID;

            //Attempt to get the avatar image id, if does not exist then add the image.
            try {
                avatarID = getResourceManager().getImageID(path);
            } catch (IllegalArgumentException e) {
                avatarID = getResourceManager().addAvatarImage(path);
            }

            //Replace accounts imaege id.
            account.setAvatarID(avatarID);
            //Parse in edited account.
            getAccountManager().editAccount(account);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDrawingEnvironmentButtonAction(ActionEvent event) throws Exception {
        loadSubscene(SceneController.DRAWING_INTERFACE);
    }
}
