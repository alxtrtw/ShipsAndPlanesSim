package entities.vehicles;
import entities.Vehicle;
import javafx.scene.shape.Circle;
import entities.locations.Crossing;
import map.MapController;

public class Ship extends Vehicle {
    private Crossing currentCrossing;
    private Crossing startCrossing;


    public Ship(String id, Crossing startCrossing, Circle circle, int speed) {
        super(id, startCrossing.getPosX(), startCrossing.getPosY(), circle, speed);
        this.startCrossing = startCrossing;

    }

    /** Erases ship from map and stops the object's thread  */
    public void die(MapController mapController){
        mapController.getEntityList().remove(this);
        mapController.getVehicleList().remove(this);
        mapController.getShipList().remove(this);
        if (this instanceof MilitaryShip){
            mapController.getMilitaryShipList().remove(this);
        }

        Circle circle = this.getCircle();
        mapController.getAnchorPane().getChildren().remove(circle);
        this.getThread().stop();
    }


    public Crossing getCurrentCrossing() {
        return currentCrossing;
    }

    public void setCurrentCrossing(Crossing currentCrossing) {
        this.currentCrossing = currentCrossing;
    }

    public Crossing getStartCrossing() {
        return startCrossing;
    }

    public void setStartCrossing(Crossing startCrossing) {
        this.startCrossing = startCrossing;
    }
}
