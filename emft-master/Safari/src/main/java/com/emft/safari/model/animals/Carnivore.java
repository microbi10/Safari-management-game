package com.emft.safari.model.animals;

import com.emft.safari.model.utilities.Age;
import static com.emft.safari.model.utilities.GameSpeed.DAY;
import static com.emft.safari.model.utilities.GameSpeed.HOUR;
import static com.emft.safari.model.utilities.GameSpeed.WEEK;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.utilities.State;
import com.emft.safari.view.ContentPanel;
import java.util.ArrayList;
import java.util.List;


public abstract class Carnivore extends Animal {
    
    protected List<Herbivore> preys;
    protected boolean isMarked;
    protected boolean isHunting;

    public Carnivore(Position pos, Age age, ContentPanel contentPanel) {
        super(pos, age, contentPanel);
        this.preys = new ArrayList<>();
        this.hungerThreshold = 2000;
        this.thirstThreshold = 1800;
        this.isMarked = false;
        this.isHunting = false;
    }
    
    public boolean isMarked() {
        return isMarked;
    }
    
    public boolean isHunting() {
        return isHunting;
    }
    
    @Override
    public boolean isThirsty() {
        return thirst >= 1000;
    }
    
    @Override
    public boolean isHungry() {
        return hunger >= 1200;
    }
    
    public List<Herbivore> getPreys() {
        return preys;
    }
    
    
    @Override
    public void move() {
        if (!isMoving()) {
            return;
        }
        
        // Közeli új víz- és élelemforrások ellenőrzése, mentése:
        checkNearbyWaterSources();
        checkNearbyFoodSources();
        
        // Tavak és prédák keresése:
        if (isThirsty() && !isTired) {
            findNearestWaterSource();
        }
        else if (isHungry() && !isTired) {
            findNearestFoodSource();
        }
        
        // Mozgás irányának és sebességének beállítása:
        if (isHunting()) {
            runTowards(targetPos);
        }
        else if (pos.distance(targetPos) > 40) {            
            moveTowards(targetPos);
        }
        else {
            setRandomTargetPos();
        }
        
        // Ütközések kezelése:
        handleWallCollision();
        handleLakeCollision();
        handlePlantCollision();
        handleAnimalCollision();
        
        // Sebesség állítása a játéksebesség alapján:
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
     * Elmenti a közeli prédákat a listájába.
     */
    @Override
    protected void checkNearbyFoodSources() {
        preys.clear();
        
        for (Herbivore herbivore : contentPanel.getHerbivores()) {
            if (!preys.contains(herbivore) && pos.distance(herbivore.getPosition()) < 170) {
                preys.add(herbivore);
            }
        }
    }

    
    /**
     * Megkeresi a legközelebbi prédát, odafut hozzá, és levadássza.
     */ 
    @Override
    protected void findNearestFoodSource() {
        if (!preys.isEmpty()) {
            Animal nearestPrey = preys.get(0);
            
            for (Animal prey : preys) {
                if (pos.distance(nearestPrey.getPosition()) > pos.distance(prey.getPosition())) {
                    nearestPrey = prey;
                }
            }

            if (state == State.MOVING && pos.distance(nearestPrey.getPosition()) >= 40) {
                setTargetPosition(nearestPrey.getPosition());
                isHunting = true;
            }
            else {
                nearestPrey.die();
                isHunting = false;
                startActivity(State.EATING);
                
                // Ha a növényevő beteg volt, akkor a fetőzött tetem a húsevőt is beteggé teszi.
                if (nearestPrey.isSick()) {
                    if (!isSick) {
                       sickness = 600;
                    }
                    
                    isSick = true;
                }
            }
        }
    }
    
    
    /**
     * Egy adott pozíció futás általi megközelítéséért felelős.
     * @param target a célpozíció, mely felé a ragadozó fut
     */ 
    protected void runTowards(Position target) {
        double deltaX = target.getX() - pos.getX();
        double deltaY = target.getY() - pos.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        if (distance == 0) {
            return;
        }
        
        double speedMultiplier = Math.max(4, 5 - (distance / 40));

        speedX = (deltaX / distance) * speedMultiplier;
        speedY = (deltaY / distance) * speedMultiplier;
        updatePrevSpeedX();
    }
    
    
    /**
     * Megjelöltté változtatja a ragadozót (egy vadőr vadászik rá).
     */
    public void setMarked() {
        isMarked = true;
    }
    
    
    /**
     * Megszünteti a ragadozó megjelöltségét (nem vadászik rá a vadőr).
     */
    public void setUnmarked() {
        isMarked = false;
    }
    
    
    
    // Csak teszteléshez (package-private setterek):
    void setPreys(List<Herbivore> preys) {
        this.preys = preys;
    }
}
