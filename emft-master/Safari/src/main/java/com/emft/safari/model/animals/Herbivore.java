package com.emft.safari.model.animals;

import com.emft.safari.model.plants.Plant;
import com.emft.safari.model.utilities.Age;
import static com.emft.safari.model.utilities.GameSpeed.DAY;
import static com.emft.safari.model.utilities.GameSpeed.HOUR;
import static com.emft.safari.model.utilities.GameSpeed.WEEK;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.utilities.State;
import com.emft.safari.view.ContentPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Herbivore extends Animal{
    
    protected List<Plant> plants;
    protected List<Carnivore> predators;
    protected boolean isFleeing;
    
    public Herbivore(Position pos, Age age, ContentPanel contentPanel){
        super(pos, age, contentPanel);
        this.plants = new ArrayList<>();
        this.predators = new ArrayList<>();
        this.hungerThreshold = 3000;
        this.thirstThreshold = 2500;
        this.isFleeing = false;
    }
    
    protected boolean isFleeing() {
        return isFleeing;
    }
    
    public List<Plant> getPlants() {
        return plants;
    }
    
    public List<Carnivore> getPredators() {
        return predators;
    }
    
    @Override
    public boolean isThirsty() {
        return thirst >= 700;
    }
    
    @Override
    public boolean isHungry() {
        return hunger >= 1000;
    }
    
    
    @Override
    public void move() {
        if (!isMoving()) {
            return;
        }
        
        // Közeli tavak, növények és ragaozók ellenőrzése, mentése:
        checkNearbyWaterSources();
        checkNearbyFoodSources();
        checkNearbyPredators();
        
        // Menekülés a legközelebbi éhes ragadozótól, ha kell:
        findNearestPredator();
        
        // Tavak és növények keresése:
        if (isThirsty() && !isTired && !isFleeing) {
            findNearestWaterSource();
        }
        else if (isHungry() && !isTired && !isFleeing) {
            findNearestFoodSource();
        }
        
        // Mozgás irányának és sebességének beállítása:
        if (pos.distance(targetPos) > 40 && !isFleeing) {            
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
     *  Elmenti a közeli növényeket a listájába.
     */ 
    @Override
    protected void checkNearbyFoodSources() {
        for (Plant plant : contentPanel.getPlants()) {
            if (!plants.contains(plant) && pos.distance(plant.getPosition()) < 170) {
                plants.add(plant);
            }
        }
    }

    
    /**
     * Megkeresi a legközelebbi növényt, odamegy hozzá, és elkezdi enni.
     */
    @Override
    protected void findNearestFoodSource() {
        if (!plants.isEmpty()) {
            Plant nearestPlant = plants.get(0);
            
            for (Plant otherPlant : plants) {
                if (pos.distance(nearestPlant.getPosition()) > pos.distance(otherPlant.getPosition())) {
                    nearestPlant = otherPlant;
                }
            }

            if (isMoving() && pos.distance(nearestPlant.getPosition()) > 30) {
                setTargetPosition(nearestPlant.getPosition());
            }
            else {
                startActivity(State.EATING);
                nearestPlant.decreaseSize();
            }
        }
    }
    
    
    /**
     * Ellenőrzi, hogy vannak-e ragadozók a közelben.
     */
    protected void checkNearbyPredators() {
        predators.clear();

        // Közeli ragadozók kigyűjtése:
        for (Carnivore carnivore : contentPanel.getCarnivores()) {
            if (carnivore.isHungry() && !carnivore.isThirsty()&& pos.distance(carnivore.getPosition()) < 120) {
                predators.add(carnivore);
            }
        }        
    }
    
    
    /**
     * Megkeresi a legközelebbi éhes ragadozó, és elindítja a menekülést, ha kell.
     * @return legközelebbi ragadozó
     */
    protected Carnivore findNearestPredator() {
        if (!predators.isEmpty()) {
            Carnivore nearestPredator = predators.get(0);
            
            for (Carnivore otherPredator : predators) {
                if (pos.distance(nearestPredator.getPosition()) > pos.distance(otherPredator.getPosition())) {
                    nearestPredator = otherPredator;
                }
            }

            // Menekülés indítása:
            isFleeing = true;    
            fleeFrom(nearestPredator.getPosition());
            return nearestPredator;
        }
        else {
            isFleeing = false;
        }
        
        return null;
    }
    
    

    /**
     * A közeli éhes ragadozóktól való menekülésért felelős.
     * @param predatorPos az üldöző ragadozó pozíciója
     */ 
    protected void fleeFrom(Position predatorPos) {
        double deltaX = pos.getX() - predatorPos.getX();
        double deltaY = pos.getY() - predatorPos.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        double fleeX = pos.getX() + deltaX;
        double fleeY = pos.getY() + deltaY;
    
        setTargetPosition(new Position((int)fleeX, (int)fleeY));
    
        Random rand = new Random();
        double speedMultiplier = rand.nextInt(3) + 2;
        speedX = (deltaX / distance) * speedMultiplier;
        speedY = (deltaY / distance) * speedMultiplier;
        updatePrevSpeedX();
        
        if (isCollidingWithWall()) {
            setRandomTargetPos();
        }
    }
    
    
    // Csak teszteléshez (package-private setterek):
    void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
    
    void setPredators(List<Carnivore> predators) {
        this.predators = predators;
    }
}