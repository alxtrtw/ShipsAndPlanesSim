package entities.vehicles;

import javafx.scene.shape.Circle;
import entities.locations.Airport;

import java.util.ArrayList;

public class MilitaryPlane extends Plane {

    private String armament;

    public MilitaryPlane(String id, Airport airport, Circle circle, int speed, ArrayList<Airport> flightPlan,
                         int staff, String armament) {
        super(id, airport, circle, speed, flightPlan, staff, 0.77);
        this.armament = armament;
    }

    public String getArmament() {
        return armament;
    }

    public void setArmament(String armament) {
        this.armament = armament;
    }
}
