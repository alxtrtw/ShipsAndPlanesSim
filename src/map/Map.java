package map;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Map {

    private MapController mapController;

    /** Creates and displays map window */
    public void displayMap() throws Exception {
        Stage mapStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        Parent root = loader.load();
        mapController = loader.getController();
        mapStage.setTitle("Worldmap");
        double width = 1100;
        double height = 550;
        mapStage.setScene(new Scene(root, width, height));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        mapStage.setX((screenBounds.getWidth() - width) / 2 + 150);
        mapStage.setY((screenBounds.getHeight() - height) / 2 - 50);
        mapStage.setResizable(false);
        mapStage.setOnCloseRequest(e -> {
            e.consume();
            Platform.exit();
        });

        mapStage.show();
    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }


}
