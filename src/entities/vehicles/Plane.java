package entities.vehicles;
import entities.Vehicle;
import javafx.scene.shape.Circle;
import entities.locations.Airport;
import map.MapController;
import menu.mainboxes.AlertBox;

import java.io.IOException;
import java.util.ArrayList;

public class Plane extends Vehicle{
    private double fuel;
    private final double fuelCapacity;
    private final double fuelConsumptionRate;
    private int staff;
    private int stopCounter;
    private boolean busy;
    private boolean waiting;
    private ArrayList<Airport> flightPlan;
    private Airport currentAirport;

    public Plane(String id, Airport startAirport, Circle circle, int speed, ArrayList<Airport> flightPlan, int staff, double fuelConsumptionRate) {
        super(id, startAirport.getPosX(), startAirport.getPosY(), circle, speed);
        this.flightPlan = flightPlan;
        this.staff = staff;
        this.stopCounter = 0;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.fuelCapacity = 1000*fuelConsumptionRate;
        refuel();

        this.waiting = false;
    }

    /** Erases plane from map and stops the object's thread  */
    public void die(MapController mapController){
        mapController.getEntityList().remove(this);
        mapController.getVehicleList().remove(this);
        mapController.getPlaneList().remove(this);
        Circle circle = this.getCircle();
        for (Airport airport : flightPlan){
            airport.removeVisitingPlane(this);
        }
        mapController.getAnchorPane().getChildren().remove(circle);
        this.getCurrentLocation().getVehicleList().remove(this);
        this.setMoving(false);
        this.getThread().stop();
    }

    /** Returns Airport object that is next in plane's flight plan list and increments index of next Airport */
    public Airport getNextStop(){
        iterateStopCounter();
        return flightPlan.get(stopCounter);
    }

    /** Increments index of next Airport in flight plan */
    public void iterateStopCounter(){
        if (stopCounter < getFlightPlan().size() - 1) {
            stopCounter += 1;
        } else{
            stopCounter = 0;
        }
    }
    /** Decrements index of next Airport in flight plan */
    public void decrementStopCounter(){
        if (stopCounter > 0) {
            stopCounter -= 1;
        } else{
            stopCounter = getFlightPlan().size() - 1;
        }
    }

    public int getStopCounter() {
        return stopCounter;
    }

    public void setStopCounter(int stopCounter) {
        this.stopCounter = stopCounter;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    /** Sets current amount of fuel as the plane's max capacity */
    public void refuel(){
        setFuel(this.fuelCapacity);
    }

    /** Decreases the plane's fuel by a value based on plane's speed and fuel consumption rate */
    public void consumeFuel(double n) throws IOException {
        // The consumption rate is hard-coded and is lower for military planes,
        // which tend to carry less fuel, but have more efficient engines
        double consumedFuel = this.getSpeed()*n*fuelConsumptionRate;
        if (getFuel() < consumedFuel){
            // This doesn't happen unless you play with the consumption rate
            // Emergency landing could be implemented here,
            // but we declare planes with enough fuel to get through the whole map
            new AlertBox().display("Uh oh", String.format("Plane %s has ran out of fuel!", getId()));
        }
        else {
            setFuel(getFuel() - consumedFuel);
        }
    }

    public Airport getCurrentAirport(){
        return this.currentAirport;
    }

    public void setCurrentAirport(Airport currentAirport) {
        this.currentAirport = currentAirport;
    }

    public void setCurrentLocationAsCurrentAirport() {
        this.currentAirport = ((Airport) getCurrentLocation());
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) { this.staff = staff; }

    public ArrayList<Airport> getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(ArrayList<Airport> flightPlan) {
        this.flightPlan = flightPlan;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

}
