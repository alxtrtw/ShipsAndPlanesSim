package entities.vehicles;

import javafx.scene.shape.Circle;
import entities.locations.Airport;

import java.util.ArrayList;

public class PassengerPlane extends Plane{

    private String companyName;
    private int numberOfPassengers;
    private int capacity;

    public PassengerPlane(String id, Airport startAirport, Circle circle, int speed, ArrayList<Airport> flightPlan,
                          int staff, String companyName, int capacity) {
        super(id, startAirport, circle, speed, flightPlan, staff, 1.23);
        this.companyName = companyName;
        this.capacity = capacity;
        this.numberOfPassengers = capacity;
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
