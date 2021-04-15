package menu.planeinfoboxes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.MapController;
import entities.vehicles.PassengerPlane;

public class PassengerPlaneBox {
    private MapController mapController;

    /** Displays box providing current information about the clicked passenger plane */
    public void displayBox(PassengerPlane passengerPlane) throws Exception {
        Stage boxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PassengerPlaneBox.fxml"));
        Parent root = loader.load();
        PassengerPlaneBoxController passengerPlaneBoxController = loader.getController();
        passengerPlaneBoxController.setMapController(mapController);
        passengerPlaneBoxController.setStage(boxStage);
        passengerPlaneBoxController.setPlane(passengerPlane);
        boxStage.setTitle("Plane info");
        boxStage.setScene(new Scene(root, 275, 425));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        boxStage.setX((screenBounds.getWidth() - 275) / 2 - 575);
        boxStage.setY((screenBounds.getHeight() - 425) / 2 + 50);
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
