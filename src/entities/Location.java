package entities;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Location extends  Entity{

    private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
    ArrayList<Location> nextLocationList = new ArrayList<Location>();

    public Location(String id, double posX, double posY, Circle circle) {
        super(id, posX, posY, circle);
    }

    public ArrayList<Location> getNextLocationList() {
        return nextLocationList;
    }

    public void setNextLocationList(ArrayList<Location> nextLocationList) {
        this.nextLocationList = nextLocationList;
    }

    /** Adds a location to the list of next (connected) entities.locations */
    public void addLocationToList(Location location){
        if (!nextLocationList.contains(location)) {
            this.nextLocationList.add(location);
        }
    }

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(ArrayList<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    /** Adds Vehicle object to location's vehicle list*/
    public void addVehicle(Vehicle vehicle){
        this.vehicleList.add(vehicle);
    }

    /** Removes Vehicle object from location's vehicle list*/
    public void removeVehicle(Vehicle vehicle){
        this.vehicleList.remove(vehicle);
    }
}
