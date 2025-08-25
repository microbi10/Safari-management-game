package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;

/**
 * A megfigyelő eszközök töltőállomása
 * Getterek es settereken kívül nincs speciális metódusa.
 **/
public class Station {
    public static int PRICE=200;
    private final Position position;
  
    private final int IconWIDTH=60;
    private final int IconHEIGHT=100;
    
    private boolean marked;
    
    public Station(Position pos){
        this.position=pos;
        this.marked=false;
    
    }
    
    public Position getPosition(){
        return this.position;
    }
    
    public int getImageWidth(){
        return this.IconWIDTH;
    } 
    public int getImageHeight(){
        return this.IconHEIGHT;
    }
    public void setMark(boolean bol){
        this.marked=bol;
    }
    public boolean getMarked(){
        return this.marked;
    }
    
}
