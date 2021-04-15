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
import entities.vehicles.Ship;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddPassengerShipBoxController {
    private MapController mapController;
    @FXML
    private TextField idField;
    @FXML
    private ChoiceBox<String> crossingChoiceBox;
    @FXML
    private ChoiceBox<String> companyChoiceBox;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label speedLabel;
    @FXML
    private Slider capacitySlider;
    @FXML
    private Label capacityLabel;

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
                ArrayList<Vehicle> vehicleList = mapController.getVehicleList();
                for (Vehicle vehicle : vehicleList) {
                    if (vehicle.getId().equals(id)) {
                        throw new NameAlreadyBoundException();
                    }
                }

                Ship ship = mapController.addPassengerShip(id, foundCrossing, companyChoiceBox.getValue(), (int)speedSlider.getValue(), (int)capacitySlider.getValue());
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
        } catch (IOException ee) {
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

        companyChoiceBox.getItems().addAll(mapController.getShipCompanyList());
        companyChoiceBox.setValue(companyChoiceBox.getItems().get(0));

        speedSlider.setMin(15);
        speedSlider.setMax(25);
        speedSlider.setBlockIncrement(1);

        speedLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        speedSlider.valueProperty()
                )
        );

        capacitySlider.setMin(20);
        capacitySlider.setMax(200);
        capacitySlider.setBlockIncrement(1);

        capacityLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        capacitySlider.valueProperty()
                )
        );
    }
}
