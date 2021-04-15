package entities.vehicles;

import javafx.scene.shape.Circle;
import entities.locations.Crossing;

public class PassengerShip extends Ship {

    private String companyName;
    private int numberOfPassengers;
    private int capacity;

    public PassengerShip(String id, Crossing startCrossing, Circle circle, int speed, String companyName, int capacity) {
        super(id, startCrossing, circle, speed);
        this.companyName = companyName;
        this.capacity = capacity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
