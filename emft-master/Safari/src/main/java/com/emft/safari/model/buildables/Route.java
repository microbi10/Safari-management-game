package com.emft.safari.model.buildables;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Route {
    
    private boolean complete;
    private Position start;
   
    private ArrayList<Position> listPoints;
    private boolean selected;
    
    private ArrayList<Position> forwDirection;
    private ArrayList<Position> backwDirection;
    private final ContentPanel contentPanel;
    
    private boolean free;
    
 
    public Route(Position start, ContentPanel contentPanel){
        this.contentPanel=contentPanel;
        this.free = true;
        this.forwDirection = new ArrayList<>();
        this.backwDirection = new ArrayList<>();
        this.start = start;
        this.complete = false;
        this.listPoints = new ArrayList<>();
        this.listPoints.add(start);
        selected = false;
    }
    
    public Route(ArrayList<Position> pos, ContentPanel contentPanel){
        this.contentPanel=contentPanel;
        this.free = true;
        this.forwDirection = new ArrayList<>();
        this.backwDirection = new ArrayList<>();
        this.start = pos.getFirst();
        this.complete = false;
        this.listPoints = new ArrayList<>();
        
        for (Position pp :pos){
            this.listPoints.add(new Position(pp.getX(), pp.getY()));
        }
        
        selected = false;
    }
    
    public void setFree(boolean b){
        this.free = b;
    }
    
    public boolean getFree(){
        return free;
    }
    
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    public boolean getSelected(){
        return selected;
    }
    
    public ArrayList<Position> getPoints(){
        return listPoints;
    }
    
    
    
    public void addRoute(ArrayList<Position> section){
       listPoints.addAll(section);
       section.clear();
      
    }
    
    public Position getEndRoute(){
        return listPoints.get(listPoints.size() - 1);
    }
    
    public Map<String, Object> onRoute(Position position) {
        Map<String, Object> result= new HashMap<>();
        for (Position point : listPoints){
            if (point.distance(position) < 20){
                result.put("exist", true);
                result.put("position", point );
                return result;
            }                
        }
        result.put("exist",false);
        return result;
    }
    
    public void setComplete(){
        cleanRoute();
        this.complete = true;
    }
    
    public boolean getComplete(){
        return complete;
    }
    
    private void cleanRoute(){
        for (int i = 1; i < listPoints.size() - 1; i++){
            if (listPoints.get(i - 1).distance(listPoints.get(i + 1))<5) {
               listPoints.remove(i);
            }
        }   
        
        ArrayList<Position> newPos = new ArrayList<>();
        
        for (int i = 1; i < listPoints.size(); i++) {
            double j = 0.4;
            Position fromPos = listPoints.get(i - 1);
            Position toPos = listPoints.get(i);
            
            while(j <= 1){
                double x = (float) fromPos.getX() + j * (float)(toPos.getX() - fromPos.getX());
                double y = (float) fromPos.getY() + j * (float)(toPos.getY() - fromPos.getY());
            
                newPos.add(new Position((int) Math.round(x), (int) Math.round(y)));
                j += 0.9;
            }  
        }
        
        listPoints.clear();
        listPoints.addAll(0, newPos);
        newPos.clear();
        
        
        for (int i = 1; i < listPoints.size(); i++){
            Position dVector = listPoints.get(i).differ(listPoints.get(i - 1));
            
            forwDirection.add(new Position ((int)((float)10*dVector.getY()*(-1) / sqrt(pow(dVector.getX(),2)+pow(dVector.getY(),2))), 
                    (int)( (float)10*dVector.getX() / (sqrt(pow(dVector.getX(),2)+pow(dVector.getY(),2))))));
           
            Position d1Vector = listPoints.get(i - 1).differ(listPoints.get(i));
            backwDirection.add(new Position ((int)((float)10*d1Vector.getY() *(-1)/ sqrt(pow(d1Vector.getX(),2)+pow(d1Vector.getY(),2))), 
                    (int)( (float)10*d1Vector.getX() / (sqrt(pow(d1Vector.getX(),2)+pow(d1Vector.getY(),2))))));
        }
        
        listPoints.removeLast();
        newPos.add(forwDirection.get(0));
        
        for (int i = 1; i < forwDirection.size() - 1; i++) {
            newPos.add(forwDirection.get(i - 1).average(forwDirection.get(i), forwDirection.get(i + 1)));
        }
        
        newPos.add(forwDirection.getLast());
        forwDirection.clear();
        forwDirection.addAll(0, newPos);
        newPos.clear();
        newPos.add(backwDirection.get(0));
        
        for (int i = 1; i < backwDirection.size() - 1; i++) {
            newPos.add(backwDirection.get(i - 1).average(backwDirection.get(i), backwDirection.get(i + 1)));
        }
        
        newPos.add(backwDirection.getLast());
        backwDirection.clear();
        backwDirection.addAll(0, newPos);
        newPos.clear();
        
        /*for (int i=1; i<forwDirection.size();i++){
            double dot = forwDirection.get(i-1).getX() * forwDirection.get(i).getX() + forwDirection.get(i-1).getY()* forwDirection.get(i).getY();
                if (dot < 0) {
                    // Irányuk ellentétes → fordítsd meg
                    forwDirection.get(i).setX((-1)*forwDirection.get(i).getX());
                    forwDirection.get(i).setY((-1)*forwDirection.get(i).getY());
                }
        }*/
    }
    
    public ArrayList<Position> getForwDirection(){
        return this.forwDirection;
    }
    
    public ArrayList<Position> getBackwDirection(){
        return this.backwDirection;
    }
    
    public boolean isThereAnimal(Position pos){
       return  this.contentPanel.isThereAnimal(pos);
    }
    public boolean isTherePeople(Position pos){
        return this.contentPanel.isTherePeople(pos);
    }
    
    //Ezt a metodust csak a teszteleshez hasznaljuk, a mock-olás elősegítese miatt.
    public void setlistPoints(ArrayList<Position> list){
        this.listPoints=list;
    }
}
