package map;

import entities.Entity;
import entities.Location;
import entities.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import entities.locations.Airport;
import entities.locations.Crossing;
import menu.mainboxes.AlertBox;
import menu.locationinfoboxes.AirportBox;
import menu.planeinfoboxes.MilitaryPlaneBox;
import menu.planeinfoboxes.PassengerPlaneBox;
import menu.locationinfoboxes.CrossingBox;
import menu.shipinfoboxes.MilitaryShipBox;
import menu.shipinfoboxes.PassengerShipBox;
import entities.vehicles.*;

import java.io.IOException;
import java.lang.Math;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class MapController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private ArrayList<Circle> circleList= new ArrayList<Circle>();

    private ArrayList<Entity> entityList= new ArrayList<Entity>();
    private ArrayList<Vehicle> vehicleList= new ArrayList<Vehicle>();
    private ArrayList<Ship> shipList= new ArrayList<Ship>();
    private ArrayList<Plane> planeList= new ArrayList<Plane>();
    private ArrayList<MilitaryShip> militaryShipList= new ArrayList<MilitaryShip>();


    private ArrayList<Location> locationList= new ArrayList<Location>();
    private ArrayList<Crossing> crossingList= new ArrayList<Crossing>();
    private ArrayList<Crossing> seaportList= new ArrayList<Crossing>();
    private ArrayList<Airport> airportList= new ArrayList<Airport>();

    private List<String> planeCompanyList;
    private List<String> shipCompanyList;
    private List<String> armamentList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // changes *2 wszystko
        addCrossing("New York", 115, 80, true);
        addCrossing("Lisbon", 210, 81, true);
        addCrossing("Fortaleza", 164, 161, true);
        addCrossing("Reykjavik", 201, 36, true);
        addCrossing("Oslo", 240, 47, true);
        addCrossing("Cape Town", 255, 218, true);
        addCrossing("Jakarta", 400, 165, true);
        addCrossing("Adelaide", 439.5, 222.5, true);
        addCrossing("Mogadishu", 297.5, 153.5, true);
        addCrossing("Mumbai", 343, 116.5, true);
        addCrossing("Ho Chi Minh", 403, 134, true);
        addCrossing("Hong Kong", 411, 113, true);
        addCrossing("Tokyo", 439.5, 89, true);

        addCrossing("North Atlantic", 170, 115, false);
        addCrossing("Labrador Sea", 180, 65, false);
        addCrossing("South Atlantic", 205, 195, false);
        addCrossing("Indian Ocean", 340, 226, false);
        addCrossing("Arabic Sea", 350, 170, false);

        connectCrossingWithList("New York", Arrays.asList("Labrador Sea", "North Atlantic", "Fortaleza"));
        connectCrossingWithList("Lisbon", Arrays.asList("Labrador Sea", "North Atlantic", "Reykjavik"));
        connectCrossingWithList("Fortaleza", Arrays.asList("North Atlantic", "South Atlantic"));
        connectCrossingWithList("North Atlantic", Arrays.asList("South Atlantic"));
        connectCrossingWithList("Labrador Sea", Arrays.asList("New York"));
        connectCrossingWithList("Reykjavik", Arrays.asList("Labrador Sea"));
        connectCrossingWithList("Cape Town", Arrays.asList("South Atlantic", "Indian Ocean"));
        connectCrossingWithList("Indian Ocean", Arrays.asList("Arabic Sea", "Mogadishu","Adelaide"));
        connectCrossingWithList("Arabic Sea", Arrays.asList("Mogadishu", "Mumbai", "Jakarta"));
        connectCrossingWithList("Mogadishu", Arrays.asList("Mumbai"));
        connectCrossingWithList("Jakarta", Arrays.asList("Ho Chi Minh"));
        connectCrossingWithList("Ho Chi Minh", Arrays.asList("Hong Kong"));
        connectCrossingWithList("Hong Kong", Arrays.asList("Tokyo"));
        connectCrossingWithList("Oslo", Arrays.asList("Reykjavik"));

        addAirport("Chicago", 94.5, 79, 4);
        addAirport("Mexico city", 65, 116, 3);
        addAirport("Brasilia", 145, 185, 2);
        addAirport("Marrakesh", 214, 96, 3);
        addAirport("Paris", 230, 63.5, 3);
        addAirport("Moscow", 279, 53.5, 2);
        addAirport("Cairo", 275, 99.5, 3);
        addAirport("Johannesburg", 270, 203, 2);
        addAirport("Dubai", 313, 107.5, 5);
        addAirport("New Delhi", 347, 101, 2);
        addAirport("Beijing", 405.5, 81.5, 3);
        addAirport("Sydney", 460, 218, 2);


        setPlaneCompanyList(Arrays.asList("Emirates", "Quantas", "Air France", "United", "Lufthansa", "EVA Air"));
        setShipCompanyList(Arrays.asList("Caribbean", "Crystal", "Regent Seven Seas", "Costa", "Azamara", "Silversea"));
        setArmamentList(Arrays.asList("TOW Missiles", "Heat-seeking AA Missiles", "JDAM GPS-guided Bombs",
                "GAU-8 Avenger Cannon", "B-8M1 Rocket Pods"));

    }

    // Connect crossing with other crossings by IDs
    private void connectCrossingWithList(String crossing1Id, List<String> connectIdList){
        Crossing crossing1 = new Crossing("nullEntity", -1, -1, new Circle(), false);
        ArrayList<Crossing> connectCrossingList = new ArrayList<Crossing>();
        for (Crossing crossing : crossingList){
            if (connectIdList.contains(crossing.getId())){
                connectCrossingList.add(crossing);
            }
            else if (crossing1Id.equals(crossing.getId())){
                crossing1 = crossing;
            }
        }

        for (Crossing crossing2 : connectCrossingList){
            crossing1.addCrossingToList(crossing2);
            crossing2.addCrossingToList(crossing1);

            Line line = new Line();
            line.setStartX(crossing1.getPosX()*2);
            line.setStartY(crossing1.getPosY()*2);
            line.setEndX(crossing2.getPosX()*2);
            line.setEndY(crossing2.getPosY()*2);
            line.setFill(Color.DARKCYAN);
            anchorPane.getChildren().add(line);
            crossing1.getCircle().toFront();
            crossing2.getCircle().toFront();
        }
    }

    // Add circle to map
    private Circle addCircle(double x, double y, String entityType){
        Circle circle = new Circle();
        circle.setLayoutX(x);
        circle.setCenterX(x);
        circle.setLayoutY(y);
        circle.setCenterY(y);

        if (entityType.equals("MilitaryShip")) {
            circle.setRadius(5);
            circle.setFill(Color.DARKGREEN);
        } else if (entityType.equals("PassengerShip")){
            circle.setRadius(4.5);
            circle.setFill(Color.DODGERBLUE);
        }else if (entityType.equals("MilitaryPlane")){
            circle.setRadius(3.5);
            circle.setFill(Color.DARKOLIVEGREEN);
        }else if (entityType.equals("PassengerPlane")){
            circle.setRadius(4.5);
            circle.setFill(Color.MIDNIGHTBLUE);
        } else if (entityType.equals("Airport")){
            circle.setRadius(6);
            circle.setFill(Color.DARKRED);
        } else if (entityType.equals("Crossing")){
            circle.setRadius(6);
            circle.setFill(Color.DARKCYAN);
        }

        anchorPane.getChildren().add(circle);
        circleList.add(circle);


        if (entityType.contains("Plane")){
            circle.toFront();
            for (Airport airport : airportList){
                airport.getCircle().toFront();
            }
        } else if (entityType.contains("Ship")){
            for (Crossing crossing : crossingList){
                crossing.getCircle().toFront();
            }
        }
        return circle;
    }

    /** Creates a PassengerShip object with specified parameters ands places it on the map */
    public PassengerShip addPassengerShip(String id, Crossing startCrossing, String company, int speed, int capacity){
        Circle circle = addCircle(startCrossing.getPosX(), startCrossing.getPosY(), "PassengerShip");
        PassengerShip ship = new PassengerShip(id, startCrossing, circle, speed, company, capacity);
        addShip(ship);

        circle.setOnMouseClicked(event -> {
            if (!ship.isClicked()){
                ship.setClicked(true);
                PassengerShipBox passengerShipBox = new PassengerShipBox();
                passengerShipBox.setMapController(this);
                try {
                    passengerShipBox.displayBox(ship);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return ship;
    }

    /** Creates a MilitaryShip object with specified parameters ands places it on the map */
    public MilitaryShip addMilitaryShip(String id, Crossing startCrossing, String armament, int speed){
        Circle circle = addCircle(startCrossing.getPosX(), startCrossing.getPosY(), "MilitaryShip");
        MilitaryShip ship = new MilitaryShip(id, startCrossing, circle, speed, armament);
        addShip(ship);
        militaryShipList.add(ship);

        circle.setOnMouseClicked(event -> {
            if (!ship.isClicked()){
                ship.setClicked(true);
                MilitaryShipBox militaryShipBox = new MilitaryShipBox();
                militaryShipBox.setMapController(this);
                try {
                    militaryShipBox.displayBox(ship);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return ship;
    }

    // Add a ship to map
    private Ship addShip(Ship ship){
        ship.setCurrentCrossing(ship.getStartCrossing());
        ship.setCurrentLocation(ship.getStartCrossing());
        shipList.add(ship);
        vehicleList.add(ship);
        entityList.add(ship);

        ship.setThread(new Thread(() -> {
            // The tread stays in an infinite loop until plane.die() is called
            while(true) {
            // If ship is not moving it means that the flight has ended
                if (!ship.isMoving()) {
                    Crossing currentCrossing = ship.getCurrentCrossing();
                    // Add to queue
                    if (!currentCrossing.getWaitingVehiclesList().contains(ship)) {
                        currentCrossing.addWaitingVehicle(ship);
                    }
                    // Semaphore check
                    if (currentCrossing.isBusy()) {

                        // Wait and try moving 1 second later
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (currentCrossing.getWaitingVehiclesList().get(0).equals(ship)) {
                        // The crossing is not busy and this ship is first in queue so it can move
                        currentCrossing.setBusy(true);
                        // Ship preparing for the trip, not necessary but it makes the semaphores more visible
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (currentCrossing.getWaitingVehiclesList().contains(ship)) {
                            currentCrossing.removeWaitingVehicle(ship);
                        }

                        ArrayList<Location> nextLocationList = currentCrossing.getNextLocationList();
                        Location chosenLocation = nextLocationList.get(new Random().nextInt(nextLocationList.size()));

                        if (ship instanceof PassengerShip && chosenLocation instanceof Crossing) {
                            if (((Crossing) chosenLocation).isSeaport()) {
                                int capacity = ((PassengerShip) ship).getCapacity();
                                ((PassengerShip) ship).setNumberOfPassengers(ThreadLocalRandom.current().nextInt(20, capacity + 1));
                            }
                        }

                        moveVehicle(ship, chosenLocation, "null");
                        currentCrossing.setBusy(false);
                    }
                }
            }
        }));
        ship.getThread().setDaemon(true);
        ship.getThread().start();

        return ship;
    }

    /** Creates a MilitaryPlane object with specified parameters ands places it on the map */
    public MilitaryPlane addMilitaryPlane(String id, int speed, int staff, ArrayList<Airport> flightPlan,
                                          MilitaryShip startShip,String armament){
        Point2D startShipCoords = startShip.find();
        Airport airport = new Airport("tempLocation", startShipCoords.getX(), startShipCoords.getY(),
                new Circle(), 1);
        Circle circle = addCircle(airport.getPosX(), airport.getPosY(), "MilitaryPlane");
        MilitaryPlane militaryPlane = new MilitaryPlane(id, airport, circle, speed, flightPlan, staff, armament);
        addPlane(militaryPlane, airport);
        militaryPlane.decrementStopCounter();

        circle.setOnMouseClicked(event -> {
            if (!militaryPlane.isClicked()) {
                militaryPlane.setClicked(true);
                MilitaryPlaneBox militaryPlaneBox = new MilitaryPlaneBox();
                militaryPlaneBox.setMapController(this);
                try {
                    militaryPlaneBox.displayBox(militaryPlane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return militaryPlane;
    }

    /** Creates a PassengerPlane object with specified parameters ands places it on the map */
    public PassengerPlane addPassengerPlane(String id, int speed, int staff, ArrayList<Airport> flightPlan,
                                            int capacity, String companyName){

        Airport airport = flightPlan.get(0);
        Circle circle = addCircle(airport.getPosX(), airport.getPosY(), "PassengerPlane");
        PassengerPlane passengerPlane = new PassengerPlane(id, airport, circle, speed, flightPlan, staff, companyName, capacity);
        addPlane(passengerPlane, airport);

        circle.setOnMouseClicked(event -> {
            if (!passengerPlane.isClicked()) {
                passengerPlane.setClicked(true);
                PassengerPlaneBox passengerPlaneBox = new PassengerPlaneBox();
                passengerPlaneBox.setMapController(this);
                try {
                    passengerPlaneBox.displayBox(passengerPlane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return passengerPlane;
    }

    // Add plane to map
    private Plane addPlane(Plane plane, Airport airport){
        plane.setCurrentLocation(airport);
        plane.setCurrentAirport(airport);
        planeList.add(plane);
        vehicleList.add(plane);
        entityList.add(plane);


        for (Airport airport2 : plane.getFlightPlan()){
            airport2.addVisitingPlane(plane);
        }

        plane.setThread(new Thread(() -> {
            double fuelStep = 0.5;
            while(true) {
                try {
                    Thread.sleep(round(fuelStep*1000));
                    if (!plane.isWaiting()) {
                        plane.consumeFuel(fuelStep);
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

                if (!plane.isMoving() && !plane.isBusy()) {
                    Airport currentAirport = plane.getCurrentAirport();

                    // Arrived to airport
                    plane.setWaiting(true);
                    if (!currentAirport.getVehicleList().contains(plane)){
                        plane.getCurrentAirport().addVehicle(plane);
                    }

                    // Plane wants to land
                    if ( currentAirport.getVehicleList().indexOf(plane) < currentAirport.getCapacity() ){
                        // Flight control gives permission for landing
                        plane.setWaiting(false);
                        // Plane preparing for liftoff, not necessary but it makes the waiting system more visible

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        plane.refuel();

                        if (plane instanceof PassengerPlane){
                            int capacity = ((PassengerPlane) plane).getCapacity();
                            ((PassengerPlane) plane).setNumberOfPassengers(ThreadLocalRandom.current().nextInt(5, capacity + 1));
                        }

                        Location chosenLocation = plane.getNextStop();
                        plane.getCurrentAirport().removeVehicle(plane);
                        moveVehicle(plane, chosenLocation, "null");
                    } else{
                        // Plane is rejected and waits in the air for 3 seconds
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }));
        plane.getThread().setDaemon(true);
        plane.getThread().start();

        return plane;
    }

    // Create crossing
    private Crossing addCrossing(String id, double x, double y, boolean isSeaport) {
        Circle circle = addCircle(x, y, "Crossing");
        Crossing crossing = new Crossing(id, x, y, circle, isSeaport);

        circle.setOnMouseClicked(event -> {
            if (!crossing.isClicked()) {
                crossing.setClicked(true);
                CrossingBox crossingBox = new CrossingBox();
                crossingBox.setMapController(this);
                try {
                    crossingBox.displayBox(crossing);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        crossingList.add(crossing);
        locationList.add(crossing);
        entityList.add(crossing);
        if (isSeaport){
            seaportList.add(crossing);
        }


        return crossing;
    }

    // Create airport
    private Airport addAirport(String id, double x, double y, int capacity) {
        Circle circle = addCircle(x, y, "Airport");
        Airport airport = new Airport(id, x, y, circle, capacity);

        circle.setOnMouseClicked(event -> {
            if (!airport.isClicked()) {
                airport.setClicked(true);
                AirportBox airportBox = new AirportBox();
                airportBox.setMapController(this);
                try {
                    airportBox.displayBox(airport);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new AlertBox().display("Alert", "This entity's control box is already open");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        airportList.add(airport);
        locationList.add(airport);
        entityList.add(airport);


        return airport;
    }

    // Move a vehicle to location, instantly or in time based on vehicle's speed
    private void moveVehicle(Vehicle vehicle, Location location, String instantOption){

        vehicle.setMoving(true);
        Circle circle = vehicle.getCircle();

        int speed = vehicle.getSpeed();

        double startX = circle.getLayoutX();
        double startY = circle.getLayoutY();
        double endX = location.getPosX();
        double endY = location.getPosY();
        double shiftX = (endX - startX)*2;
        double shiftY = (endY - startY)*2;

        double time;
        if (instantOption.equals("instant")){
            time = 0;
        } else{
            time = countSpeed(shiftX, shiftY, speed);
        }

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(time));

        transition.setFromX(-shiftX);
        transition.setFromY(-shiftY);
        transition.setToX(0);
        transition.setToY(0);

        transition.setNode(circle);

        transition.setOnFinished(event -> {
            vehicle.setMoving(false);
        });
        transition.play();

        circle.setCenterX(endX);
        circle.setLayoutX(endX);
        circle.setCenterY(endY);
        circle.setLayoutY(endY);
        vehicle.setCurrentLocation(location);
        if (vehicle instanceof Ship){
            ((Ship) vehicle).setCurrentCrossing((Crossing) location);
        }
        if (vehicle instanceof Plane && !location.getId().equals("tempLocation")){
            ((Plane) vehicle).setCurrentAirport((Airport) location);
        }
        vehicle.setLastTransition(transition);
    }

    /** Lands a plane in emergency mode on the closest airport available */
    public void emergencyLanding(Plane plane){

        plane.getLastTransition().stop();
        plane.setBusy(true);
        plane.setMoving(false);

        Point2D point = plane.find();
        double pointX = point.getX();
        double pointY = point.getY();

        moveVehicle(plane, new Location("tempLocation", pointX, pointY, new Circle()), "instant");
        moveVehicle(plane, findClosestAirport(pointX, pointY, airportList), "null");
        plane.decrementStopCounter();
        plane.setBusy(false);
    }

    /** Returns an Airport object that is the shortest distance from given coordinates */
    public Airport findClosestAirport(double currentX, double currentY, ArrayList<Airport> specificAirportsList){
        double minDistance = 1000;
        double distance;
        Airport minAirport = new Airport("nullEntity", -1, -1, new Circle(), -1);
        for (Airport airport : specificAirportsList) {
            distance = distance(currentX, currentY, airport.getPosX(), airport.getPosY());
            if (distance < minDistance) {
                minDistance = distance;
                minAirport = airport;
            }
        }
        if (minDistance == 1000){
            try {
                new AlertBox().display("Error", "No closest airport found...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return minAirport;
    }

    // Calculate time of transition execution
    private double countSpeed(double x, double y, int speed){
        double distance = distance(0,0, x, y);
        return distance/speed;
    }

    // Get distance between two points
    private double distance(double startX, double startY, double endX, double endY){
        return Math.sqrt( pow((endX-startX),2) + pow((endY-startY),2));
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(ArrayList<Ship> shipList) {
        this.shipList = shipList;
    }

    public ArrayList<Plane> getPlaneList() {
        return planeList;
    }

    public void setPlaneList(ArrayList<Plane> planeList) {
        this.planeList = planeList;
    }

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(ArrayList<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }

    public ArrayList<Circle> getCircleList() {
        return circleList;
    }

    public void setCircleList(ArrayList<Circle> circleList) {
        this.circleList = circleList;
    }

    public ArrayList<Crossing> getCrossingList() {
        return crossingList;
    }

    public void setCrossingList(ArrayList<Crossing> crossingList) {
        this.crossingList = crossingList;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public void setAirportList(ArrayList<Airport> airportList) {
        this.airportList = airportList;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public ArrayList<Crossing> getSeaportList() {
        return seaportList;
    }

    public void setSeaportList(ArrayList<Crossing> seaportList) {
        this.seaportList = seaportList;
    }

    public List<String> getPlaneCompanyList() {
        return planeCompanyList;
    }

    public void setPlaneCompanyList(List<String> planeCompanyList) {
        this.planeCompanyList = planeCompanyList;
    }

    public List<String> getShipCompanyList() {
        return shipCompanyList;
    }

    public void setShipCompanyList(List<String> shipCompanyList) {
        this.shipCompanyList = shipCompanyList;
    }

    public List<String> getArmamentList() {
        return armamentList;
    }

    public void setArmamentList(List<String> armamentList) {
        this.armamentList = armamentList;
    }

    public ArrayList<MilitaryShip> getMilitaryShipList() {
        return militaryShipList;
    }

    public void setMilitaryShipList(ArrayList<MilitaryShip> militaryShipList) {
        this.militaryShipList = militaryShipList;
    }
}
