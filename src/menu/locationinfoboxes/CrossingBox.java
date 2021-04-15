package menu.locationinfoboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import entities.locations.Crossing;
import map.MapController;

public class CrossingBox {

    private MapController mapController;

    /** Displays box providing current information about the clicked Crossing */
    public void displayBox(Crossing crossing) throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CrossingBox.fxml"));
        Parent root = loader.load();
        CrossingBoxController crossingBoxController = loader.getController();
        crossingBoxController.setMapController(mapController);
        crossingBoxController.setStage(boxStage);
        crossingBoxController.setCrossing(crossing);
        boxStage.setTitle("Crossing information");
        boxStage.setScene(new Scene(root, 200, 250));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setX((screenBounds.getWidth() - 200) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 250) / 2 + 50);
        boxStage.setResizable(false);
        boxStage.show();
    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

}
