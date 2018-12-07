package JavaFX;

import Core.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PopularListingController extends SceneController implements Initializable {

    @FXML
    private PieChart popularPie;

    public void initialize(URL location, ResourceBundle resources) {
        popularPie.setData(FXCollections.observableArrayList(new PieChart.Data("Grapefruit", 13)));
    }

}