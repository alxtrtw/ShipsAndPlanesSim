package menu.planeinfoboxes;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import entities.locations.Airport;
import map.MapController;
import menu.mainboxes.AlertBox;
import entities.vehicles.PassengerPlane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class PassengerPlaneBoxController {
    private MapController mapController;
    private PassengerPlane passengerPlane;
    private Stage stage;
    private ArrayList<Airport> flightPlan = new ArrayList<Airport>();
    private AnimationTimer refreshTimer;
    @FXML
    private ListView<String> flightPlanListView;
    @FXML
    private ChoiceBox<String> airportChoiceBox;
    @FXML
    private Label companyLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label staffLabel;
    @FXML
    private Label passengersLabel;
    @FXML
    private Label fuelLabel;
    @FXML
    private Label locationLabel;


    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public PassengerPlane getPlane() {
        return passengerPlane;
    }

    /** Initiates the emergency landing procedure of the clicked plane in the map controller*/
    public void emergencyLanding(){
        mapController.emergencyLanding(passengerPlane);
    }

    /** Apart from setting the passenger plane, the function initializes all the parameters in the box */
    public void setPlane(PassengerPlane passengerPlane) {
        this.passengerPlane = passengerPlane;
        idLabel.setText(passengerPlane.getId());
        staffLabel.setText(String.valueOf(passengerPlane.getStaff()));
        companyLabel.setText(String.format("%s plane",passengerPlane.getCompanyName()));

        ArrayList<String> airportNameList = new ArrayList<String>();
        for (Airport airport : mapController.getAirportList()){
            airportNameList.add(airport.getId());
        }
        airportChoiceBox.getItems().addAll(airportNameList);
        resetBox();

        flightPlan = passengerPlane.getFlightPlan();
        for (Airport airport : flightPlan){
            flightPlanListView.getItems().add(airport.getId());
        }
        ArrayList<Airport> tempFlightPlan = flightPlan;
        flightPlan = new ArrayList<Airport>();
        flightPlan.addAll(tempFlightPlan);

        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshPassengersLabel();
                refreshFuelLabel();
                refreshLocationLabel();
            }
        };
        refreshTimer.start();
        stage.setOnCloseRequest(e -> {
            e.consume();
            passengerPlane.setClicked(false);
            refreshTimer.stop();
            stage.close();
        });
    }

    public void refreshLocationLabel() {
        Airport currentAirport = passengerPlane.getCurrentAirport();
        locationLabel.setText(currentAirport.getId());
    }

    /** Deletes the plane from the map */
    public void deletePlane(){
        passengerPlane.die(mapController);
        stage.close();
    }

    private void refreshPassengersLabel(){
        int passengers = passengerPlane.getNumberOfPassengers();
        passengersLabel.setText(String.format("%d", passengers));
    }

    private void refreshFuelLabel(){
        double fuel = passengerPlane.getFuel();
        fuelLabel.setText(String.format("%4.1f", fuel));
    }

    /** Sets the newly created flight plan as the plane's current flight plan */
    public void setNewFlightPlan(){
        try{
            try{
                if (flightPlan.size() == 0) {
                    // Flight plan can't be empty
                    throw new EmptyStackException();
                }
                else if (flightPlan.get(0).equals(flightPlan.get(flightPlan.size() - 1))) {
                    // Check if Flight plan's last airport isn't same as first
                    throw new IllegalArgumentException();
                }

                for (Airport airport : passengerPlane.getFlightPlan()){
                    airport.removeVisitingPlane(passengerPlane);
                }
                for (Airport airport : flightPlan){
                    airport.addVisitingPlane(passengerPlane);
                }

                passengerPlane.setFlightPlan(flightPlan);
                Airport closestAirport = mapController.findClosestAirport(passengerPlane.getCurrentAirport().getPosX(),
                        passengerPlane.getCurrentAirport().getPosY(), flightPlan);
                int closestAirportIndex = flightPlan.indexOf(closestAirport);
                if (closestAirportIndex==0){
                    passengerPlane.setStopCounter(flightPlan.size()-1);
                }
                else{
                    passengerPlane.setStopCounter(closestAirportIndex-1);
                }

                ArrayList<Airport> tempFlightPlan = flightPlan;
                flightPlan = new ArrayList<Airport>();
                flightPlan.addAll(tempFlightPlan);

            }
            catch (EmptyStackException e){
                new AlertBox().display("Error", "The plane must have a flight plan");
            }
            catch(IllegalArgumentException e){
                new AlertBox().display("Error", "Flight plan is cyclical - don't put the same airport as start and end");
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    /** Adds Airport object to the new flight plan list */
    public void addAirportToFlightPlan() {
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

            if (flightPlan.size() > 0) {
                if (foundAirportId.equals(flightPlan.get(flightPlan.size() - 1).getId())) {
                    throw new IllegalArgumentException();
                }
            }

            if (airportIsFound) {
                flightPlan.add(foundAirport);
                flightPlanListView.getItems().add(foundAirportId);
            }

        } catch (IllegalArgumentException e) {
            try {
                new AlertBox().display("Error", "Flight plan can't have same consecutive airports");
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

    /** Removes Airport object from the new flight plan list */
    public void removeLastAirport(){
        if (flightPlan.size()>0){
            airportChoiceBox.setValue("Add to list...");
            flightPlanListView.getItems().remove(flightPlan.size()-1);
            flightPlan.remove(flightPlan.size()-1);
        }
    }

    /** Clears the new flight plan list */
    public void clearFlightPlan(){
        airportChoiceBox.setValue("Add..");
        flightPlanListView.getItems().removeAll(flightPlanListView.getItems());
        flightPlan.removeAll(flightPlan);
    }

    private void resetBox(){
        airportChoiceBox.setValue("Add to list...");
        flightPlanListView.getItems().removeAll(flightPlanListView.getItems());
        flightPlan.removeAll(flightPlan);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
