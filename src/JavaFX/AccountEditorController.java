package JavaFX;

import Core.ResourceManager;
import Core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class AccountEditorController extends SceneController{

    int id = getResourceFlowManager().getUserID();

    Path selectedPath;

    FileChooser avatarChooser = new FileChooser();

    @FXML
    private void selectCustomAvatar(ActionEvent event) {
        avatarChooser.setInitialDirectory(new File("src/DefaultAvatars"));
        Node node = (Node) event.getSource();
        File file  = avatarChooser.showOpenDialog(node.getScene().getWindow());
        selectedPath = Paths.get(file.getAbsolutePath());

        setAvatar(true);
    }

    private void setAvatar(boolean isCustom) {
        try {

            //Get the user account
            User account = getAccountManager().getAccount(id);

            //Make sure the image path uses forward slash
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

            //Replace accounts image id.
            account.setAvatarID(avatarID);
            //Parse in edited account.
            getAccountManager().editAccount(account);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
