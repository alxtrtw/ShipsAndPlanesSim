package menu.planecreationboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.MapController;

public class AddMilitaryPlaneBox {
    private MapController mapController;

    /** Displays the military plane creation box */
    public void displayBox() throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMilitaryPlaneBox.fxml"));
        Parent root = loader.load();
        AddMilitaryPlaneBoxController addMilitaryPlaneBoxController = loader.getController();
        addMilitaryPlaneBoxController.setStage(boxStage);
        addMilitaryPlaneBoxController.setMapController(mapController);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setTitle("Add Plane");
        boxStage.setScene(new Scene(root, 300, 450));
        boxStage.setX((screenBounds.getWidth() - 300) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 450) / 2 + 50);
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
