package com.emft.safari.model.buildables;

import com.emft.safari.model.animals.Animal;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Lake {
    protected Position pos;
    protected static final Image image;
    protected static final int IMAGE_WIDTH = 150;
    protected static final int IMAGE_HEIGHT = 90;
    protected final ContentPanel contentPanel;
    
    static {
        image = new ImageIcon(Lake.class.getResource("/pictures/lake.png")).getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
    }
    
    public Lake(Position pos, ContentPanel contentPanel) {
        this.pos = pos;
        this.contentPanel = contentPanel;
    }
    
    
    public Image getImage() {
        return image;
    }
    
    public int getImageWidth() {
        return IMAGE_WIDTH;
    }
    
     public int getImageHeight() {
        return IMAGE_HEIGHT;
    }
     
    public Position getPosition() {
        return pos;
    }
    
    /**
     * Ellenőrzi, hogy épp iszik-e egy állat a tóból.
     * @return isznak-e a tóból
     */
    public boolean isBeingDrunk() {
        for (Animal animal : contentPanel.getAnimals()) {
            if (!animal.isDead() && animal.isDrinking() && pos.distance(animal.getPosition()) < 40) {
                return true;
            }
        }
        
        return false;
    }
}
