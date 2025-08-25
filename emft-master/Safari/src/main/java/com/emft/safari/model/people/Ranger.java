package com.emft.safari.model.people;

import com.emft.safari.model.animals.Carnivore;
import com.emft.safari.model.animals.Hyena;
import com.emft.safari.model.animals.Lion;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


public class Ranger extends Personnel {
    private final List<Poacher> poachers;
    public boolean isHunting;
    public Carnivore targetPredator;
    
    public Ranger(Position pos, ContentPanel contentPanel, int employmentStartDate){
        super(pos, contentPanel, employmentStartDate);
        this.poachers = new ArrayList<>();
        this.isHunting = false;
        this.targetPredator = null;
    }
    
    public Carnivore getTargetPredator() {
        return targetPredator;
    }
    
    
    @Override
    public void move() {
        
        if (isHunting) {
            findTargetPredator();
        }
        else {
            checkEmploymentStatus();
            checkNearbyPoachers();
            findNearestPoacher();
        }
        
        handleWallCollision();
        
        double speedMultiplier = 1;
        
        switch(contentPanel.getGameSpeed()) {
            case HOUR -> speedMultiplier = 1;
            case DAY -> speedMultiplier = 7;
            case WEEK -> speedMultiplier = 13;
        }
        
        // Elmozdulás:
        pos.setX((int) (pos.getX() + speedX * speedMultiplier));
        pos.setY((int) (pos.getY() + speedY * speedMultiplier));
    }
    
    
    /**
     *  Elmenti a közeli orvvadászokat a listájába.
     */
    private void checkNearbyPoachers() {
        poachers.clear();
        
        for (Poacher poacher : contentPanel.getPoachers()) {
            if (!poachers.contains(poacher) && pos.distance(poacher.getPosition()) < 250) {
                poachers.add(poacher);
            //    System.out.println("Poacher found: " + poacher);
            }
        }
    }
    
    
    /** 
     * Megkeresi a legközelebbi orvvadászt, elkezdi üldözni, és megöli.
     * @return a legközelebbi orvvadász
     */
    protected Poacher findNearestPoacher() {
        if (!poachers.isEmpty()) {
            Poacher nearestPoacher = poachers.get(0);
            
            for (Poacher otherPoacher : poachers) {
                if (pos.distance(nearestPoacher.getPosition()) > pos.distance(otherPoacher.getPosition())) {
                    nearestPoacher = otherPoacher;
                }
            }

            if (pos.distance(nearestPoacher.getPosition()) > 100) {
                moveTowards(nearestPoacher.getPosition());
            }
            else {
                nearestPoacher.die();
            }
            
            return nearestPoacher;
        }
        
        return null;
    }
    
    
    /** 
     * Megkeresi a felhasználó által kijelölt ragadozót és megöli.
     * A ragadozó típusa alapján a játékos kap bizonyos mennyiségű pénzt is.
     */
    protected void findTargetPredator() {
        if (targetPredator != null && !targetPredator.isDead() && targetPredator != null) {
            if (pos.distance(targetPredator.getPosition()) > 100) {
                moveTowards(targetPredator.getPosition());
            }
            else {
                targetPredator.die();
                isHunting = false;
                
                if (targetPredator instanceof Lion) {
                    contentPanel.getDirector().earnMoney(40);
                }
                else if (targetPredator instanceof Hyena) {
                    contentPanel.getDirector().earnMoney(30);
                }
            }
        }
        
        if (targetPredator != null && targetPredator.isDead()) {
            targetPredator.setUnmarked();
            targetPredator = null;
        }
    }

    
    /**
     * A levadászni kívánt ragadozó beállítása és a vadászati állapot aktiválása.
     * @param carnivore levadászni kívánt ragadozó 
     */
    public void setTargetPredator(Carnivore carnivore) {
        this.targetPredator = carnivore;
        isHunting = true;
    }
    
    
    @Override
    public Image getImage() {
        
        // Orvvadász és ragadozó vadászatának kezelése:
        if (targetPredator != null && isHunting && !targetPredator.isDead()) {
            if (pos.distance(targetPredator.getPosition()) > 140 && pos.distance(targetPredator.getPosition()) < 350) {
                return imageCache.get(getClass().getSimpleName() + "_tracking");
            }
            else if (pos.distance(targetPredator.getPosition()) <= 140) {
                return imageCache.get(getClass().getSimpleName() + "_shooting");
            }
        }
        else if (!isHunting && findNearestPoacher() != null) {
            Poacher poacher = findNearestPoacher();
            
            if (pos.distance(poacher.getPosition()) > 140 && pos.distance(poacher.getPosition()) < 250) {
                return imageCache.get(getClass().getSimpleName() + "_tracking");
            }
            else if (pos.distance(poacher.getPosition()) <= 140) {
                return imageCache.get(getClass().getSimpleName() + "_shooting");
            }
        }
        
        return imageCache.get(getClass().getSimpleName());
    }
}
