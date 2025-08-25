package com.emft.safari.model.utilities;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int distance (Position other) {
        double d = sqrt(pow(x - other.getX(), 2) + pow(y - other.getY(), 2));
        return (int)d;
    }
    
    public Position differ(Position other) {
        return new Position(x - other.getX(), y - other.getY());
    }
    
    public Position average(Position oth1, Position oth2) {
        return new Position((x + oth1.getX() + oth2.getX()) / 3, 
                            (y + oth1.getY() + oth2.getY()) / 3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    
    @Override
    public String toString(){
        return "("+ x + ", " + y + ")";
    }
}