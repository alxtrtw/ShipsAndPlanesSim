package menu.mainboxes;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import map.Map;
import map.MapController;
import menu.planecreationboxes.AddMilitaryPlaneBox;
import menu.planecreationboxes.AddPassengerPlaneBox;
import menu.shipcreationboxes.AddMilitaryShipBox;
import menu.shipcreationboxes.AddPassengerShipBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public CheckBox militaryCheckBox;

    private MapController mapController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startSimulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSimulation() throws Exception {
        Map worldMap= new Map();
        worldMap.displayMap();
        mapController = worldMap.getMapController();
    }

    /** Opens a box for creating a ship accordingly to the "Military" check box */
    public void addShip() throws Exception {

        boolean isMilitary = militaryCheckBox.isSelected();
        if (isMilitary){
            addMilitaryShip();
        }
        else{
            addPassengerShip();
        }
    }

    private void addMilitaryShip() throws Exception {
        AddMilitaryShipBox addMilitaryShipBox = new AddMilitaryShipBox();
        addMilitaryShipBox.setMapController(mapController);
        addMilitaryShipBox.displayBox();
    }

    private void addPassengerShip() throws Exception {
        AddPassengerShipBox addPassengerShipBox = new AddPassengerShipBox();
        addPassengerShipBox.setMapController(mapController);
        addPassengerShipBox.displayBox();
    }

    /** Opens a box for creating a plane accordingly to the "Military" check box */
    public void addPlane() throws Exception {

        boolean isMilitary = militaryCheckBox.isSelected();
        if (isMilitary){
            addMilitaryPlane();
        }
        else{
            addPassengerPlane();
        }
    }

    private void addMilitaryPlane() throws Exception {
        if (mapController.getMilitaryShipList().size()>0) {
            AddMilitaryPlaneBox addMilitaryPlaneBox = new AddMilitaryPlaneBox();
            addMilitaryPlaneBox.setMapController(mapController);
            addMilitaryPlaneBox.displayBox();
        }
        else{
            try {
                new AlertBox().display("Error", "You can't create a Military Aircraft with no Aircraft Carriers!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPassengerPlane() throws Exception {
        AddPassengerPlaneBox addPassengerPlaneBox = new AddPassengerPlaneBox();
        addPassengerPlaneBox.setMapController(mapController);
        addPassengerPlaneBox.displayBox();
    }

}
