package menu.locationinfoboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import entities.locations.Airport;
import map.MapController;

public class AirportBox {
    private MapController mapController;

    /** Displays box providing current information about the clicked Airport */
    public void displayBox(Airport airport) throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AirportBox.fxml"));
        Parent root = loader.load();
        AirportBoxController airportBoxController = loader.getController();
        airportBoxController.setMapController(mapController);
        airportBoxController.setStage(boxStage);
        airportBoxController.setAirport(airport);
        boxStage.setTitle("Airport information");
        boxStage.setScene(new Scene(root, 250, 375));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setX((screenBounds.getWidth() - 250) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 375) / 2 + 50);
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
