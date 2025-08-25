package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;

/**
 * A MobileDevice absztrakt osztály gyermek osztálya.
 */

public class Airship extends MobileDevice{
    public static int PRICE = 150;
    
    public Airship(Position center, Station station, Position start){
        super(center,station,start);
        this.maxFlightTime = 140;
        this.radiusOfVisibility = 120;
        this.sizeICon = 60;
    }
}
