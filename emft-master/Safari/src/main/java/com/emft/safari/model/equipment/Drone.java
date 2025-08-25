package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;

/**
 * A MobileDevice absztrakt osztály gyermek osztálya.
 */

public class Drone extends MobileDevice {
    
    public static int PRICE = 100;
    
    public Drone(Position center, Station station, Position start){
        super(center, station, start);
        this.maxFlightTime = 50;
        this.radiusOfVisibility = 80;
        this.sizeICon = 50;
    }
}
