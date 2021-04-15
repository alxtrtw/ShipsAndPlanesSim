This application is a simulation of ships and planes moving around the world.

After running the jar file, you can see a control menu and a map with marked locations.
Details about locations will pop up when clicked.

Vehicles can be added from the control menu. Civilian vehicles take random amounts of 
passengers from locations they stop at. Both types have military counterparts that carry 
weapons instead of passengers. A military plane can only be created on an existing 
military ship (aircraft carrier). Military planes will spawn with armament that 
is produced by their mother ship.

When creating a ship, a starting seaport is chosen, and then the ship travels between 
harbors randomly. When creating a plane, a cyclical flight plan must be created, consisting 
of the existing airports.

Every vehicle is a separate thread. If too many vehicles meet in one location, they are put
into a queue and wait for their turn to proceed. A seaport can have only one ship at a time,
but airports have enough space for multiple planes, and they have different capacities.

A vehicle can be clicked to show their info box. 
Plane info boxes are interactive, with options to change vehicle's flight plan or to perform an 
emergency landing on the closest airport. All vehicles can also be deleted from the info box.

The application was written in Java 8, using IntelliJ IDEA. UI was created with Scene Builder 
and JavaFX 15.01