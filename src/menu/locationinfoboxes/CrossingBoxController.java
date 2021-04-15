package menu.locationinfoboxes;

import entities.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import entities.locations.Crossing;
import map.MapController;

import java.util.ArrayList;

public class CrossingBoxController {

    private MapController mapController;
    private Crossing crossing;
    private Stage stage;
    private AnimationTimer refreshTimer;

    @FXML
    private Label idLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private ListView<String> shipsListView;

    // Refresh list view of ships in queue
    private void refreshShipsListView(){
        ArrayList<String> shipIdList = new ArrayList<String>();
        for (Vehicle ship : crossing.getWaitingVehiclesList()) {
            shipIdList.add(ship.getId());
        }
        shipsListView.getItems().removeAll(shipsListView.getItems());
        shipsListView.getItems().addAll(shipIdList);
    }

    public Crossing getCrossing() {
        return crossing;
    }

    /** Apart from setting the crossing field, the function gets data from the Crossing object to be shown */
    public void setCrossing(Crossing crossing) {
        this.crossing = crossing;
        idLabel.setText(crossing.getId());
        if(crossing.isSeaport()){
            typeLabel.setText("Seaport");
        }
        else{
            typeLabel.setText("Sea route crossing");
        }

        refreshShipsListView();
        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshShipsListView();
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            crossing.setClicked(false);
            refreshTimer.stop();
            stage.close();
        });
    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
