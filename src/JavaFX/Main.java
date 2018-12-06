package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Extends Application to launch the user interface.
 * @author Grzegorz Debicki, Marcos Pallikaras, Dominic Woodman
 * @version 1.0
 */
public class Main extends Application {
	/**
	 * Main method to launch the program.
	 */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the program.
     */
    @Override
    public void start(Stage primaryStage) {
    	//Tries to load the main interface screen and show it to the user
        try {
            Parent stage = FXMLLoader.load(getClass().getResource(SceneController.MAIN_INTERFACE));
            Scene scene = new Scene(stage);

            primaryStage.setScene(scene);
            primaryStage.setTitle("TaweLib");
            primaryStage.getIcons().add(new Image("/Resources/bookIcon.png"));
            primaryStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
