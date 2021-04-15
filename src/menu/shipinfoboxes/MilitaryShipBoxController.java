package menu.shipinfoboxes;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.locations.Crossing;
import map.MapController;
import entities.vehicles.MilitaryShip;

public class MilitaryShipBoxController {
    private MapController mapController;
    private MilitaryShip militaryShip;
    private Stage stage;
    private AnimationTimer refreshTimer;
    @FXML
    private Label idLabel;
    @FXML
    private Label armamentLabel;
    @FXML
    private Label locationLabel;


    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public MilitaryShip getShip() {
        return militaryShip;
    }

    /** Apart from setting the military ship, the function initializes all the parameters in the box */
    public void setShip(MilitaryShip militaryShip) {
        this.militaryShip = militaryShip;
        idLabel.setText(militaryShip.getId());
        armamentLabel.setText(String.format("Capable of producing %s",militaryShip.getArmament()));
        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshLocationLabel();
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            militaryShip.setClicked(false);
            refreshTimer.stop();
            stage.close();
        });
    }

    /** Deletes the ship from the map */
    public void deleteShip(){
        militaryShip.die(mapController);
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void refreshLocationLabel(){
        Crossing currentCrossing = militaryShip.getCurrentCrossing();
        if (currentCrossing.isSeaport()){
            locationLabel.setText(String.format("Cruising to %s", currentCrossing.getId()));
        }
        else{
            locationLabel.setText(String.format("Cruising on %s", currentCrossing.getId()));

        }

    }
}
