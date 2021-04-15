package menu.planecreationboxes;

import entities.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import entities.locations.Airport;
import entities.locations.Crossing;
import map.MapController;
import menu.mainboxes.AlertBox;
import entities.vehicles.MilitaryPlane;
import entities.vehicles.MilitaryShip;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class AddMilitaryPlaneBoxController {

    private MapController mapController;
    private ArrayList<MilitaryShip> militaryShipList = new ArrayList<MilitaryShip>();
    private AnimationTimer refreshTimer;
    private Stage stage;
    @FXML
    private TextField idField;
    @FXML
    private ListView<String> flightPlanListView;
    @FXML
    private ChoiceBox<String> airportChoiceBox;
    @FXML
    private ChoiceBox<String> startShipChoiceBox;
    @FXML
    private Label armamentLabel;
    @FXML
    private Label staffLabel;
    @FXML
    private Slider staffSlider;
    @FXML
    private Label speedLabel;
    @FXML
    private Slider speedSlider;

    private ArrayList<Airport> flightPlan = new ArrayList<Airport>();

    /** Orders the map controller to create a new military plane with parameters specified in the box */
    public void addPlane() throws Exception{
        try {
            try {
                String id = idField.getText();
                if (id.equals("")) {
                    // ID field empty
                    throw new NullPointerException();
                } else if (id.equals("nullEntity")) {
                    // nullEntity is an id reserved for creating dummy entities
                    throw new InvalidNameException();
                }

                // Check if ID not already taken
                ArrayList<Vehicle> vehicleList = mapController.getVehicleList();
                for (Vehicle vehicle : vehicleList) {
                    if (vehicle.getId().equals(id)) {
                        throw new NameAlreadyBoundException();
                    }
                }

                if (flightPlan.size() == 0) {
                    // Flight plan can't be empty
                    throw new EmptyStackException();
                }
                else if (flightPlan.get(0).equals(flightPlan.get(flightPlan.size() - 1))) {
                    // Check if Flight plan's last airport isn't same as first
                    throw new IllegalArgumentException();
                }

                MilitaryShip foundStartShip = new MilitaryShip("nullEntity", new Crossing("nullEntity", -1,
                        -1, new Circle(), false), new Circle(), -1, "nullArmament");
                String foundStartShipId = startShipChoiceBox.getValue();
                for (MilitaryShip militaryShip : militaryShipList) {
                    if (militaryShip.getId().equals(foundStartShipId)) {
                        foundStartShip = militaryShip;
                    }
                }
                String armament = foundStartShip.getArmament();

                MilitaryPlane militaryPlane = mapController.addMilitaryPlane(id, (int) speedSlider.getValue(),
                        (int) staffSlider.getValue(), flightPlan, foundStartShip, armament);


                ArrayList<Airport> tempFlightPlan = flightPlan;
                flightPlan = new ArrayList<Airport>();
                flightPlan.addAll(tempFlightPlan);

                idField.setText("");
            } catch (EmptyStackException e) {
                new AlertBox().display("Error", "The plane must have a flight plan");
            } catch (IllegalArgumentException e) {
                new AlertBox().display("Error", "Flight plan is cyclical - don't put the same airport as start and end");
            } catch (NullPointerException e) {
                new AlertBox().display("Error", "ID field is empty");
            } catch (NameAlreadyBoundException e) {
                new AlertBox().display("Error", "ID already taken");
            } catch (InvalidNameException e) {
                new AlertBox().display("Error", "nullEntity is an illegal ID");
            }
        }
        catch (IOException ee){
            ee.printStackTrace();
        }
    }

    /** Adds an Airport object to the FlightPlan list, based on which the plane will move */
    public void addAirportToFlightPlan(){
        try {
            boolean airportIsFound = false;
            Airport foundAirport = new Airport("nullEntity", -1, -1, new Circle(), -1);
            String foundAirportId = airportChoiceBox.getValue();
            ArrayList<Airport> airportList = mapController.getAirportList();
            for (Airport airport : airportList) {
                if (airport.getId().equals(foundAirportId)) {
                    airportIsFound = true;
                    foundAirport = airport;
                }
            }

            if (flightPlan.size()>0){
                if (foundAirportId.equals(flightPlan.get(flightPlan.size() - 1).getId())) {
                    throw new IllegalArgumentException();
                }
            }

            if (airportIsFound) {
                flightPlan.add(foundAirport);
                flightPlanListView.getItems().add(foundAirportId);
            }

        }
        catch(IllegalArgumentException e){
            try {
                new AlertBox().display("Error", "Flight plan can't have same consecutive airports");
            } catch (IOException ee) {
                e.printStackTrace();
            }
        }
    }

    /** Removes last Airport object from the FlightPlan list */
    public void removeLastAirport(){
        if (flightPlan.size()>0){
            flightPlanListView.getItems().remove(flightPlan.size()-1);
            flightPlan.remove(flightPlan.size()-1);
        }
    }

    /** Clears the FlightPlan list */
    public void clearFlightPlan(){
        airportChoiceBox.setValue("Add to list...");
        flightPlanListView.getItems().removeAll(flightPlanListView.getItems());
        flightPlan.removeAll(flightPlan);
    }

    public MapController getMapController() {
        return mapController;
    }

    /** Apart from setting the map controller, the function initializes all the parameters in the box */
    public void setMapController(MapController mapController) {
        this.mapController = mapController;
        militaryShipList.addAll(mapController.getMilitaryShipList());

        ArrayList<String> airportNameList = new ArrayList<String>();
        for (Airport airport : mapController.getAirportList()){
            airportNameList.add(airport.getId());
        }
        airportChoiceBox.getItems().addAll(airportNameList);
        airportChoiceBox.setValue(airportChoiceBox.getItems().get(0));

        startShipChoiceBox.setOnAction(event -> {
            String startShipId = startShipChoiceBox.getValue();
            for (MilitaryShip militaryShip : militaryShipList){
                if (militaryShip.getId().equals(startShipId)){
                    armamentLabel.setText(militaryShip.getArmament());
                }
            }
        });
        refreshStartShipChoiceBox();

        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startShipChoiceBox.getItems().size() != mapController.getMilitaryShipList().size()) {
                    if (mapController.getMilitaryShipList().size() == 0){
                        try {
                            new AlertBox().display("Error", "There are no Aircraft Carriers present!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.close();
                        this.stop();
                    }else{
                        refreshStartShipChoiceBox();
                    }
                }
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            refreshTimer.stop();
            stage.close();
        });

        resetBox();

        staffSlider.setMin(1);
        staffSlider.setMax(8);
        staffSlider.setBlockIncrement(1);

        staffLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        staffSlider.valueProperty()
                )
        );

        speedSlider.setMin(30);
        speedSlider.setMax(40);
        speedSlider.setBlockIncrement(1);

        speedLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        speedSlider.valueProperty()
                )
        );
    }

    // Refresh the starting aricraft carrier choice box in case a a new one is created
    private void refreshStartShipChoiceBox(){
        militaryShipList = mapController.getMilitaryShipList();
        startShipChoiceBox.getItems().removeAll(startShipChoiceBox.getItems());
        ArrayList<String> startShipIDList = new ArrayList<String>();
        for (MilitaryShip militaryShip : militaryShipList) {
            startShipIDList.add(militaryShip.getId());
        }
        startShipChoiceBox.getItems().addAll(startShipIDList);
        startShipChoiceBox.setValue(startShipChoiceBox.getItems().get(0));
    }

    // Reset the box after creating a plane
    private void resetBox(){
        airportChoiceBox.setValue("Add to list...");
        flightPlanListView.getItems().removeAll(flightPlanListView.getItems());
        flightPlan.removeAll(flightPlan);
        idField.setPromptText("Plane's name...");
        idField.setText("");
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
