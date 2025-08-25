package com.emft.safari.model.people;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.animals.Animal;
import static com.emft.safari.model.utilities.GameSpeed.DAY;
import static com.emft.safari.model.utilities.GameSpeed.HOUR;
import static com.emft.safari.model.utilities.GameSpeed.WEEK;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import java.util.Random;

public class Poacher extends Person {   
    private Animal targetAnimal;
    private boolean isHunting;
        
    public Poacher(Position pos, ContentPanel contentPanel) {
        super(pos, contentPanel);
        this.targetAnimal = null;
        this.isHunting = false;
    }
    
    
    @Override
    public void move() {        
        if (isHunting) {
            findTargetAnimal();
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
     * A célállat kiválasztásáért felelős.
     */
    public void decideTargetAnimal() {
        if (targetAnimal == null) {
            Random rand = new Random();
            
            if (rand.nextInt(1000) < 30) {
                for (Animal animal : contentPanel.getAnimals()) {
                    if (rand.nextInt(1000) < 400) {
                        targetAnimal = animal;
                        isHunting = true;
                    }
                }
            }
        }
    }
    
    
    /**
     * A célállat levadászásáért felelős.
     */
    public void findTargetAnimal() {
        if (!targetAnimal.isDead() && targetAnimal != null) {
            if (pos.distance(targetAnimal.getPosition()) > 100) {
                moveTowards(targetAnimal.getPosition());
            }
            else {
                targetAnimal.die();
           //     System.out.println("Poacher hunted: " + targetAnimal);
                targetAnimal = null;
                isHunting = false;
            }
        }
    }

    
    /**
     * Az orvvadász halálának kezeléséért felelős.
     */
    public void die() {
        contentPanel.removePoacher(this);
    }
    
    public Animal getTargetAnimal(){
        return targetAnimal;
    }

    @Override
    public Image getImage() {
        return imageCache.get(getClass().getSimpleName());
    }
}
