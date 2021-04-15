package menu.locationinfoboxes;

import entities.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import entities.locations.Airport;
import map.MapController;
import entities.vehicles.Plane;

import java.util.ArrayList;

public class AirportBoxController {

    private MapController mapController;
    private Airport airport;
    private Stage stage;
    private AnimationTimer refreshTimer;
    @FXML
    private Label idLabel;
    @FXML
    private Label capacityLabel;
    @FXML
    private ListView<String> visitingListView;
    @FXML
    private ListView<String> waitingListView;
    @FXML
    private ListView<String> landedListView;

    // Refresh all list views of airport info box
    private void refreshListViews(){
        ArrayList<String> visitingPlanesIdList = new ArrayList<String>();
        ArrayList<String> landedPlanesIdList = new ArrayList<String>();
        ArrayList<String> waitingPlanesIdList = new ArrayList<String>();
        int counter = 0;

        for (Plane plane : airport.getVisitingPlanesList()) {
            visitingPlanesIdList.add(plane.getId());
        }
        for (Vehicle plane : airport.getVehicleList()){
            if (counter < airport.getCapacity()){
                landedPlanesIdList.add(plane.getId());
            }else{
                waitingPlanesIdList.add(plane.getId());
            }
            counter++;
        }
        visitingListView.getItems().removeAll(visitingListView.getItems());
        landedListView.getItems().removeAll(landedListView.getItems());
        waitingListView.getItems().removeAll(waitingListView.getItems());

        visitingListView.getItems().addAll(visitingPlanesIdList);
        landedListView.getItems().addAll(landedPlanesIdList);
        waitingListView.getItems().addAll(waitingPlanesIdList);


    }

    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public Airport getAirport() {
        return airport;
    }

    /** Apart from setting the airport field, the function gets data from the Airport object to be shown */
    public void setAirport(Airport airport) {
        this.airport = airport;
        idLabel.setText(airport.getId());
        capacityLabel.setText(String.format("Landing spots: %d",airport.getCapacity()));

        refreshListViews();
        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshListViews();
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            airport.setClicked(false);
            refreshTimer.stop();
            stage.close();
        });
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
