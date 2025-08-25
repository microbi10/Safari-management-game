package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.buildables.Route;
import com.emft.safari.view.SafariGameEngine;

/**
 * A turisták szállítását végzi a Safariban. 
 */
public class Jeep {
    
    public static final int seatingCapacity=4;
    public static final int PRICE=100;
    private Position position;
    private Route route;
    private boolean underway;
    private boolean forward;
    private int indexStep;
    private double interpol;
    private int size;
    private Position direction;
    private Position fromPos;
    private Position toPos;
   
    
    public Jeep() {
        this.underway = false;
        this.route = null;
        this.indexStep = 0;
        this.position = null;
        
    }
    
// Az objektum mozgásának inicializását végzi el.
    public void move(Route route) {
        route.setFree(false);
        this.route = route;
        this.position = new Position(route.getPoints().get(0).getX(), route.getPoints().get(0).getY());
        this.forward = true;
        this.underway = true;
        this.direction = route.getForwDirection().get(0);
        this.size = route.getPoints().size();
        this.interpol = 0.1;
        this.fromPos = route.getPoints().getFirst();
        this.toPos = route.getPoints().get(1);
    }
   
    // Az objektum konkrét helyzetének megváltoztatását végzi, gyakorlatilag a mozgást.
    public void step(){
        if(route!=null && !route.isThereAnimal(this.getPosition()) && !route.isTherePeople(this.getPosition())){
            if (underway) { 
                double speed = (2 * ((float)SafariGameEngine.getGameSpeedInt())) / 9;

                if (interpol < 1) {
                    interpol += speed;
                }
                else {
                    interpol = 0.1;

                    if (forward) {
                        indexStep += 1;

                        if ((indexStep + 1) >= (size - 1)) {
                            forward = false;
                            route.setFree(true);
                        }
                    }
                    else {
                        indexStep -= 1;

                        if (indexStep < 1) {
                            underway = false;
                        }
                    }

                    if (forward) {
                        direction = route.getForwDirection().get(indexStep); 
                        fromPos = route.getPoints().get(indexStep);
                        toPos = route.getPoints().get(indexStep + 1);
                    }
                    else {
                        direction = route.getBackwDirection().get(indexStep + 1);
                        fromPos = route.getPoints().get(indexStep + 1);
                        toPos = route.getPoints().get(indexStep);
                    }
                }

                double x =  fromPos.getX() + interpol * (float) (toPos.getX() - fromPos.getX());
                double y =  fromPos.getY() + interpol * (float)(toPos.getY() - fromPos.getY());

                position.setX((int)x);
                position.setY((int)y);
            }
            else {
                underway = false;
            }
        }
    }
  

    
    public Position getDirection(){
        return direction;
    }

    public boolean getUnderWay(){
        return underway;
    
    }
    public Position getPosition(){
        return position;
    }
    public boolean getOrientation(){
        return forward;
    }
    public Route getRoute(){
        return route;
    }
}
