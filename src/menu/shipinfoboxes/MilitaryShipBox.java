package menu.shipinfoboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.MapController;
import entities.vehicles.MilitaryShip;

public class MilitaryShipBox {
    private MapController mapController;

    /** Displays box providing current information about the clicked military ship */
    public void displayBox(MilitaryShip militaryShip) throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MilitaryShipBox.fxml"));
        Parent root = loader.load();
        MilitaryShipBoxController militaryShipBoxController = loader.getController();
        militaryShipBoxController.setMapController(mapController);
        militaryShipBoxController.setStage(boxStage);
        militaryShipBoxController.setShip(militaryShip);
        boxStage.setTitle("Ship info");
        boxStage.setScene(new Scene(root, 225, 275));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setX((screenBounds.getWidth() - 225) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 275) / 2 + 50);
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
