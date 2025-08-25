package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;

/**
 * A MonitoringDevice absztrakt osztály gyermek osztálya.
 */

public class Camera extends MonitoringDevice {
    
    public static int Price = 200;
    
    public Camera(Position position){
        super(position);
        this.radiusOfVisibility = 120;
    };
}
