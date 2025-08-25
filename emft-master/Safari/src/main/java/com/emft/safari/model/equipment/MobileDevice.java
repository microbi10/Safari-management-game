package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.SafariGameEngine;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A mozgó megfigyelő osztályok absztrakt szülőosztálya.
 * Ebből az osztályból származtatjuk a Camera és a Drone osztályokat.
 */

public abstract class MobileDevice extends MonitoringDevice {
    
    protected float maxFlightTime=100;
    protected float actFlightTime=0;
    protected float maxRefuelingTime=50;
    protected float actRefuelingTime=0;
    protected Station station;
    protected float angle;
    protected Position centerCircle; 
    protected boolean isSelected;
   
    protected Position startPoint;
    protected float radiusCircle;
   
    protected boolean isLinearMovement;
    protected boolean toStation;
    protected boolean isFlying;
    protected List<Point> lineraMovePoints;
    protected int actualIndex;
 
    public MobileDevice(Position center, Station station, Position start){
        
        super(new Position(station.getPosition().getX(), station.getPosition().getY()));
        this.lineraMovePoints = new ArrayList<>();
        this.station=station;
        this.startPoint=start;
        this.centerCircle=center;
        this.radiusCircle=center.distance(start);
        this.isSelected=false;
        this.generateMovePoint();
        this.initStartFly();       
    }
    
    // Egy egyenes vonalon elhelyezkedő pontokat határoz meg, mely mentén mozog az objektum az állomása és a
    // kijelölt útvonala között
     protected void generateMovePoint() {
         lineraMovePoints.clear();
        int x1=(int)(this.centerCircle.getX()+this.radiusCircle*Math.cos(0));
        int y1=(int)(this.centerCircle.getY()+this.radiusCircle*Math.sin(0));
        int x0=station.getPosition().getX();
        int y0=station.getPosition().getY();
        int steps = Math.max(Math.abs(x0 -x1 ), Math.abs(y0 - y1));
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            int x = (int) Math.round(x0 + t * (x1 - x0));
            int y = (int) Math.round(y0 + t * (y1 - y0));
            Point p = new Point(x, y);
            // Csak akkor adjuk hozzá, ha még nem szerepel (elhagyja a duplázást, ami pl. kerekítés miatt lehet)
            if (lineraMovePoints.isEmpty() || !lineraMovePoints.get(lineraMovePoints.size() - 1).equals(p)) {
                lineraMovePoints.add(p);
            }
        }
    }
    protected void initStartFly(){
        this.angle=0;
        isLinearMovement=true;
        toStation=false;
        isFlying=true;
        actFlightTime=0;
        actualIndex=0;

    }
     protected void initEndFly(){
        isLinearMovement=true;
        toStation=true;
        actFlightTime=0;
        actualIndex=lineraMovePoints.size()-1;
    }
    

    //az a repül metódus az eszköz mozgását valósítja meg.
    public void fly(){
        if (isFlying){
            if(isLinearMovement&&!toStation){
                if (this.actualIndex>=lineraMovePoints.size()-1){
                    isLinearMovement=false;
                }else{
                    this.position.setPosition(lineraMovePoints.get(this.actualIndex).x,lineraMovePoints.get(this.actualIndex).y);
                    this.actualIndex+=SafariGameEngine.getGameSpeedInt();
                }
                return;
            }
            if(!isLinearMovement){
                this.position.setX((int)(int)(this.centerCircle.getX()+this.radiusCircle*Math.cos(angle)));
                this.position.setY((int)(this.centerCircle.getY()+this.radiusCircle*Math.sin(angle)));
                angle+=0.01*SafariGameEngine.getGameSpeedInt();
                this.actFlightTime+=0.05;
                if((actFlightTime>maxFlightTime) & (Math.abs((angle % (2*Math.PI)))<0.1)){
                    isLinearMovement=true;
                    actFlightTime=0;
                    initEndFly();
                }
                return;
            }
            if (isLinearMovement &&toStation){
                if (this.actualIndex<=0){
                    isFlying=false;
                }else{
                    this.position.setPosition(lineraMovePoints.get(this.actualIndex).x,lineraMovePoints.get(this.actualIndex).y);
                    this.actualIndex-=SafariGameEngine.getGameSpeedInt();
                }
            }
        }else{
            refueling();
        }
    }
     
    
    // az eszkoz utantoltesenek implementacioja.
    protected  void refueling(){
        if(actRefuelingTime<maxRefuelingTime){
            actRefuelingTime+=0.05;
            
        } else {
            initStartFly();
            actRefuelingTime=0;
        }
    }
    
    public boolean getIsFlying(){
        return this.isFlying;
    }
    public void setIsSelected(boolean bol){
        this.isSelected=bol;
    }
    public boolean getIsSelected(){
        return this.isSelected;
    }
    
    //Az adott metódussal van lehetőségünk megváltoztatni az eszköz repülési utvonalát.
    public void changeFlyPath(Position center, Station station, Position start){
        
        this.actRefuelingTime=0;
        this.isFlying=false;
        this.station=station;
        this.startPoint=start;
        this.centerCircle=center;
        this.radiusCircle=center.distance(start);
        this.isSelected=false;
        this.generateMovePoint();
     
    }
    public Station getStation(){
        return this.station;
    }
}
