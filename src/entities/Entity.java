package entities;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Entity {
    private String id;
    private double posX;
    private double posY;
    private Circle circle;
    private boolean isClicked;

    protected Entity(String id, double posX, double posY, Circle circle) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.circle = circle;
        this.isClicked = false;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** Returns a Point2D object containing coordinates of the entity */
    public synchronized Point2D find(){

        double actualX, actualY;
        actualX = circle.getTranslateX() / 2 + circle.getLayoutX();
        actualY = circle.getTranslateY() / 2 + circle.getLayoutY();

        return new Point2D(actualX, actualY);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
