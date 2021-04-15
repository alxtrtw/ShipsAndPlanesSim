package entities;

import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;

public class Vehicle extends Entity{

    private int Speed;
    private volatile boolean isMoving = false;
    protected Location currentLocation;
    private Thread thread;
    private TranslateTransition lastTransition;

    protected Vehicle(String id, double posX, double posY, Circle circle, int speed) {
        super(id, posX, posY, circle);
        Speed = speed;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public TranslateTransition getLastTransition() {
        return lastTransition;
    }

    public void setLastTransition(TranslateTransition lastTransition) {
        this.lastTransition = lastTransition;
    }
}
