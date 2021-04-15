package entities.locations;
import entities.Vehicle;
import entities.Location;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


public class Crossing extends Location{

    private ArrayList<Crossing> nextCrossingList = new ArrayList<Crossing>();
    private ArrayList<Vehicle> waitingVehiclesList = new ArrayList<Vehicle>();
    private boolean isSeaport;
    private boolean isBusy;

    public Crossing(String id, double posX, double posY, Circle circle, Boolean isSeaport) {
        super(id, posX, posY, circle);
        this.isSeaport = isSeaport;
        this.isBusy = false;
    }

    public ArrayList<Crossing> getNextCrossingList() {
        return nextCrossingList;
    }

    public void setNextCrossingList(ArrayList<Crossing> nextCrossingList) {
        this.nextCrossingList = nextCrossingList;
    }

    /** Adds crossing to next crossing list */
    public void addCrossingToList(Crossing crossing){
        if (!nextCrossingList.contains(crossing)) {
            this.nextCrossingList.add(crossing);
            addLocationToList(crossing);
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isSeaport() {
        return isSeaport;
    }

    public void setSeaport(boolean seaport) {
        isSeaport = seaport;
    }

    public ArrayList<Vehicle> getWaitingVehiclesList() {
        return waitingVehiclesList;
    }

    public void setWaitingVehiclesList(ArrayList<Vehicle> waitingVehicles) {
        this.waitingVehiclesList = waitingVehicles;
    }

    /** Adds a Vehicle object to waiting queue */
    public void addWaitingVehicle(Vehicle vehicle){
        this.waitingVehiclesList.add(vehicle);
    }

    /** Removes a Vehicle object from waiting queue */
    public void removeWaitingVehicle(Vehicle vehicle){
        this.waitingVehiclesList.remove(vehicle);
    }
}

