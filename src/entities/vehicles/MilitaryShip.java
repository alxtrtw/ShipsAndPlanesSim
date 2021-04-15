package entities.vehicles;

import javafx.scene.shape.Circle;
import entities.locations.Crossing;

public class MilitaryShip extends Ship {

    private String armament;

    public MilitaryShip(String id, Crossing startCrossing, Circle circle, int speed, String armament) {
        super(id, startCrossing, circle, speed);
        this.armament = armament;
    }

    public String getArmament() {
        return armament;
    }

    public void setArmament(String armament) {
        this.armament = armament;
    }
}
