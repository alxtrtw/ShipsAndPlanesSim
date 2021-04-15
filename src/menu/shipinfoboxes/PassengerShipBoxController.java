package menu.shipinfoboxes;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.locations.Crossing;
import map.MapController;
import entities.vehicles.PassengerShip;

public class PassengerShipBoxController {
    private MapController mapController;
    private PassengerShip passengerShip;
    private Stage stage;
    private AnimationTimer refreshTimer;
    @FXML
    private Label idLabel;
    @FXML
    private Label companyLabel;
    @FXML
    private Label passengersLabel;
    @FXML
    private Label locationLabel;


    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public PassengerShip getShip() {
        return passengerShip;
    }

    /** Apart from setting the passenger ship, the function initializes all the parameters in the box */
    public void setShip(PassengerShip passengerShip) {
        this.passengerShip = passengerShip;
        idLabel.setText(passengerShip.getId());
        companyLabel.setText(String.format("Affiliated with %s",passengerShip.getCompanyName()));
        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshPassengersLabel();
                refreshLocationLabel();
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            passengerShip.setClicked(false);
            refreshTimer.stop();
            stage.close();
        });
    }

    /** Deletes the ship from the map */
    public void deleteShip(){
        passengerShip.die(mapController);
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void refreshPassengersLabel(){
        int passengers = passengerShip.getNumberOfPassengers();
        passengersLabel.setText(String.format("Passengers on board: %d", passengers));
    }

    private void refreshLocationLabel(){
        Crossing currentCrossing = passengerShip.getCurrentCrossing();
        if (currentCrossing.isSeaport()){
            locationLabel.setText(String.format("Cruising to %s", currentCrossing.getId()));
        }
        else{
            locationLabel.setText(String.format("Cruising on %s", currentCrossing.getId()));

        }

    }
}
