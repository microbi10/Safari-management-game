package com.emft.safari.model.people;

import com.emft.safari.model.animals.Animal;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


public class Vet extends Personnel {
    
    private final List<Animal> sickAnimals;
    
    public Vet(Position pos, ContentPanel contentPanel, int employmentStartDate) {
        super(pos, contentPanel, employmentStartDate);
        this.sickAnimals = new ArrayList<>();
    }
    
    
    @Override
    public void move() {   
        checkEmploymentStatus();
        
        // Közeli beteg állatok ellenőrzése:
        checkNearbySickAnimals();
        findNearestSickAnimal();
        
        // Ütközések kezelése:
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
     * Elmenti a közeli beteg állatokat a listájába.
     */
    private void checkNearbySickAnimals() {
        for (Animal animal : contentPanel.getAnimals()) {
            if (!sickAnimals.contains(animal) && pos.distance(animal.getPosition()) < 350 && animal.isSick() && !animal.isDead()) {
                sickAnimals.add(animal);
            }
        }
    }
    
    
    /** 
     * Megkeresi a legközelebbi beteg állatot, odamegy hozzá, és meggyógyítja.
     */
    protected void findNearestSickAnimal() {   
        if (!sickAnimals.isEmpty()) {
            Animal nearestSickAnimal = sickAnimals.get(0);
            
            for (Animal otherSickAnimal : sickAnimals) {
                if (pos.distance(nearestSickAnimal.getPosition()) > pos.distance(otherSickAnimal.getPosition())) {
                    nearestSickAnimal = otherSickAnimal;
                }
            }

            if (pos.distance(nearestSickAnimal.getPosition()) >= 40) {
                moveTowards(nearestSickAnimal.getPosition());
            }
            else {
                heal(nearestSickAnimal);
            }
        }
    }
     
   
    /**
     * Meggyógyítja az adott állatot.
     * @param animal meggyógyítani kívánt állat
     */
    private void heal(Animal animal) {
        animal.getHealed();
        sickAnimals.remove(animal);
    }
    
    
    @Override
    public Image getImage() {
        for (Animal animal : sickAnimals) {
            if (pos.distance(animal.getPosition()) < 350) {
                return imageCache.get(getClass().getSimpleName() + "_healing");
            }
        }
        
        return imageCache.get(getClass().getSimpleName());
    }
}
