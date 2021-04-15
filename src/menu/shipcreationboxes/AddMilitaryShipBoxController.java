package menu.shipcreationboxes;

import entities.Vehicle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import entities.locations.Crossing;
import map.MapController;
import menu.mainboxes.AlertBox;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddMilitaryShipBoxController {
    private MapController mapController;
    @FXML
    private TextField idField;
    @FXML
    private ChoiceBox<String> crossingChoiceBox;
    @FXML
    private ChoiceBox<String> armamentChoiceBox;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label speedLabel;

    /** Orders the map controller to create a new military plane with parameters specified in the box */
    public void addShip() throws Exception{
        try{
            try{
                String id = idField.getText();
                if (id.isEmpty()){
                    throw new NullPointerException();
                }
                else if (id.equals("nullEntity")){
                    // nullEntity is an id reserved for creating dummy entities
                    throw new InvalidNameException();
                }

                Crossing foundCrossing = new Crossing("nullEntity", -1, -1, new Circle(), false);
                String foundCrossingId = crossingChoiceBox.getValue();
                for (Crossing crossing : mapController.getCrossingList()) {
                    if (crossing.getId().equals(foundCrossingId)) {
                        foundCrossing = crossing;
                        break;
                    }
                }

                // Check if ID not already taken
                // And get armament
                ArrayList<Vehicle> vehicleList = mapController.getVehicleList();
                for (Vehicle vehicle : vehicleList) {
                    if (vehicle.getId().equals(id)) {
                        throw new NameAlreadyBoundException();
                    }
                }



                mapController.addMilitaryShip(id, foundCrossing, armamentChoiceBox.getValue(), (int)speedSlider.getValue());
                idField.setText("");
            }
            catch (NullPointerException e){
                new AlertBox().display("Error", "ID field is empty");
            }
            catch (NameAlreadyBoundException e){
                new AlertBox().display("Error", "ID already taken");
            }
            catch (InvalidNameException e){
                new AlertBox().display("Error", "nullEntity is an illegal ID");
            }
        } catch (
                IOException ee) {
            ee.printStackTrace();
        }
    }

    public MapController getMapController() {
        return mapController;
    }

    /** Apart from setting the map controller, the function initializes all the parameters in the box */
    public void setMapController(MapController mapController) {
        this.mapController = mapController;
        ArrayList<String> crossingNameList = new ArrayList<String>();
        for (Crossing crossing : mapController.getSeaportList()){
            crossingNameList.add(crossing.getId());
        }
        crossingChoiceBox.getItems().addAll(crossingNameList);
        crossingChoiceBox.setValue(crossingNameList.get(0));

        armamentChoiceBox.getItems().addAll(mapController.getArmamentList());
        armamentChoiceBox.setValue(armamentChoiceBox.getItems().get(0));

        speedSlider.setMin(4);
        speedSlider.setMax(12);
        speedSlider.setBlockIncrement(1);

        speedLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        speedSlider.valueProperty()
                )
        );
    }
}
