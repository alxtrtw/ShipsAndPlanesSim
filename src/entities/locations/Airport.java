package entities.locations;
import javafx.scene.shape.Circle;
import entities.vehicles.Plane;
import entities.Location;

import java.util.ArrayList;

public class Airport extends Location{
    private ArrayList<Plane> visitingPlanesList = new ArrayList<Plane>();
    private int capacity;

    public Airport(String id, double posX, double posY, Circle circle, int capacity) {
        super(id, posX, posY, circle);
        this.capacity = capacity;
    }

    public ArrayList<Plane> getVisitingPlanesList() {
        return visitingPlanesList;
    }

    public void setVisitingPlanes(ArrayList<Plane> visitingPlanesList) {
        this.visitingPlanesList = visitingPlanesList;
    }

    /** Adds a Plane object to the list of visiting planes */
    public void addVisitingPlane(Plane plane){
        this.visitingPlanesList.add(plane);
    }

    /** Removes a Plane object from list of visiting planes */
    public void removeVisitingPlane(Plane plane){
        this.visitingPlanesList.remove(plane);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
