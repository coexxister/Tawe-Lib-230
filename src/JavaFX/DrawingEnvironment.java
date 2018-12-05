package JavaFX;

/**
 * Drawing Environment Class
 *
 * @author Marcos Pallikaras
 * @version 1.1
 */

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawingEnvironment implements Initializable{
    // The ID of the current user
    private static final String USER_ID = "123456";
    // The number of avatars in user's folder
    private static long aNo = 0;

    // Holds the coordinates for drawing straight lines
    private Pair<Double, Double> initialTouch;

    @FXML
    private ToggleButton penButton;

    @FXML
    private ToggleButton straightButton;

    @FXML
    private ToggleButton eraserButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button saveButton;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Canvas canvas;

    /**
     * Create the GUI.
     *
     * @return The panel that contains the created GUI.
     */
    @SuppressWarnings("unchecked")
    private void buildGUI() {

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
//        root.setCenter(canvas);
        BufferedImage image = new BufferedImage((int)canvas.getWidth(), (int)canvas.getHeight(), BufferedImage.TYPE_INT_RGB);

        final ToggleGroup group = new ToggleGroup();
        penButton.setToggleGroup(group);
        straightButton.setToggleGroup(group);
        eraserButton.setToggleGroup(group);

        // Creates event handlers for mouse actions on canvas

        // Begins the path for particle drawing on MOUSE_PRESSED
        EventHandler beginFreeDraw = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.setLineWidth(5);
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.stroke();

            }
        };

        // Continues the path for particle drawing on MOUSE_DRAGGED
        EventHandler dragFreeDraw = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.setLineWidth(5);
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.stroke();

            }
        };

        // Begins the path for erasing on MOUSE_PRESSED
        EventHandler beginErase = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.setLineWidth(25);
                graphicsContext.setStroke(Color.WHITE);
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        };

        // Continues the path for erasing on MOUSE_DRAGGED
        EventHandler dragErase = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.setLineWidth(25);
                graphicsContext.setStroke(Color.WHITE);
                graphicsContext.stroke();
            }
        };

        // Closes the current path on MOUSE_RELEASE
        EventHandler closePath = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.closePath();
            }
        };

        // Begins the creation of a straight line by holding initial X,Y coordinates
        EventHandler startStraightLine = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.setLineWidth(5);
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.stroke();
            }
        };

        // Draws straight line after taking final X,Y coordinates
        EventHandler endStraightLine = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }

        };

        // Allow the buttons to grow in width to fill the panel space
        colorPicker.setMaxWidth(Double.MAX_VALUE);
        penButton.setMaxWidth(Double.MAX_VALUE);
        straightButton.setMaxWidth(Double.MAX_VALUE);
        eraserButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);

        // Set events for Pen button and remove other events
        penButton.setOnAction(e -> {
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, beginErase);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragErase);
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, startStraightLine);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, endStraightLine);

            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, beginFreeDraw);
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragFreeDraw);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, closePath);
        });

        // Set events for Straight Line button and remove other events
        straightButton.setOnAction(e -> {
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, beginFreeDraw);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragFreeDraw);
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, beginErase);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragErase);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, closePath);

            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, startStraightLine);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, endStraightLine);
        });

        // Set events for Eraser button and remove other events
        eraserButton.setOnAction(e -> {
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, beginFreeDraw);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragFreeDraw);
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, startStraightLine);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, endStraightLine);

            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, beginErase);
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragErase);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, closePath);

        });

        // Set action for Clear Canvas button
        clearButton.setOnAction(e -> {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphicsContext.setFillRule(null);
        });

        // Set action for Save Avatar button
        // Saves canvas in png format under the user's avatar folder and notifies with pop up
        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // Gets path of CustomAvatars folder
                Path pathCA = Paths.get( "src/CustomAvatars");
                // Counts number of avatars in user's folder and adds 1 to aNo
                try (Stream<Path> files = Files.list(Paths.get(String.valueOf(pathCA)))) {
                    aNo = files.count() + 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Creates file under user's avatar folder with the name USER_ID(aNo).png
                File file = new File(pathCA + "\\" + USER_ID + "(" + aNo + ").png");

                if (file != null) {
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        JOptionPane.showMessageDialog(null, "Successfully saved as " + USER_ID + "(" + aNo + ").png", "Avatar Saved", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(DrawingEnvironment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildGUI();
    }
}