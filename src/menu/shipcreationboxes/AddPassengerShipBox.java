package menu.shipcreationboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.MapController;

public class AddPassengerShipBox {
    private MapController mapController;

    /** Displays the passenger ship creation box */
    public void displayBox() throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPassengerShipBox.fxml"));
        Parent root = loader.load();
        AddPassengerShipBoxController addPassengerShipBoxController = loader.getController();
        addPassengerShipBoxController.setMapController(mapController);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setTitle("Add Cruiser");
        boxStage.setScene(new Scene(root, 250, 350));
        boxStage.setX((screenBounds.getWidth() - 250) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 350) / 2 - 0);
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
