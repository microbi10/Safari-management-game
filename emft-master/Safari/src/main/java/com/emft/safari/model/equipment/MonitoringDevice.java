package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;

/**
 * A megfigyelő rendszerek absztrakt főosztálya. Ebből származtatjuk a Camera osztályt és közvetve a 
 * Drone es Airship osztályokat is.
 */
public abstract class MonitoringDevice {
    
    protected int sizeICon=50;
    protected int radiusOfVisibility;
    protected Position position;
    
    public MonitoringDevice(Position position){
        this.radiusOfVisibility=20;
        this.position=position;
    }
    
    public Position getPosition(){
        return position;
    }
    
    public int getRadiusOfVisibility(){
        return radiusOfVisibility;
    }
    public int getSize(){
        return sizeICon;
    }
    
}
