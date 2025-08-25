package com.emft.safari.model.people;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;


public abstract class Person {
    
    protected Position pos;
    protected ContentPanel contentPanel;
    protected double speedX;
    protected double speedY;
    protected int imageSize;
    protected static final Map<String, Image> imageCache = new HashMap<>();
    
    static {
        // Orvos képei:
        loadImage("Vet", "/pictures/vet.png");
        loadImage("Vet_healing", "/pictures/vet_healing.png");
        
        // Vadőr képei:
        loadImage("Ranger", "/pictures/ranger.png");
        loadImage("Ranger_tracking", "/pictures/ranger_tracking.png");
        loadImage("Ranger_shooting", "/pictures/ranger_shooting.png");
        
        // Orvvadász képei:
        loadImage("Poacher", "/pictures/poacher.png");
    }    
    
    
    public Person(Position pos, ContentPanel contentPanel){
        this.pos = pos;
        this.contentPanel = contentPanel;
        setRandomSpeed();
        this.imageSize = 50;
    }
    
    public Position getPosition() {
        return pos;
    }
    
    public double getSpeedX() {
        return speedX;
    }
    
    public int getImageSize() {
        return imageSize;
    }
    
    
    /**
     * Betölti az emberekhez tartozó képeket a statikus tárolóba (Map).
     * Kezeli a képek betöltése során előkerülő hibákat.
     */ 
    private static void loadImage(String key, String path) {
        try {
            URL iconUrl = Person.class.getResource(path);
            
            if (iconUrl != null) {
                imageCache.put(key, new ImageIcon(iconUrl).getImage());
            }
            else {
                System.err.println("Image not found: " + path);
            }
        }
        catch (Exception e) {
            System.err.println("Image can't be loaded: " + key);
        }
    }
    
    
    /** 
     * Az emberek mozgásáért felelős.
     * Az eközben bekövetkező lehetséges eseményeket is kezeli.
     */
    protected abstract void move();        
    
    
    /**
     * Egy adott pozíció megközelítéséért felelős.
     * @param targetPos a célpozíció, mely felé az ember tart
     */
    protected void moveTowards(Position targetPos) {        
        double deltaX = targetPos.getX() - pos.getX();
        double deltaY = targetPos.getY() - pos.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double speedMultiplier = 5;

        speedX = (deltaX / distance) * speedMultiplier;
        speedY = (deltaY / distance) * speedMultiplier;
        
        handleWallCollision();
    }
    
    
    /**
     * A falba ütközés és visszapattanás kezeléséért felelős.
     */
    protected void handleWallCollision () {
        pos.setX(Math.max(imageSize / 2, Math.min(pos.getX(), ContentPanel.WINDOW_WIDTH - imageSize / 2)));
        pos.setY(Math.max(imageSize / 2, Math.min(pos.getY(), ContentPanel.WINDOW_HEIGHT - imageSize / 2)));

        if (pos.getX() - imageSize / 2 <= 0 || pos.getX() + imageSize / 2 >= ContentPanel.WINDOW_WIDTH) {
            speedX = -speedX;
        }
        if (pos.getY() - imageSize / 2 <= 0 || pos.getY() + imageSize / 2 >= ContentPanel.WINDOW_HEIGHT) {
            speedY = -speedY;
        }
    }
    
    
    /**
     * Az emberek sebességének véletlenszerű beállításáért felelős.
     */
    private void setRandomSpeed() {
        Random rand = new Random();
        int[] possibleValues = {-3, 3};
        speedX = possibleValues[rand.nextInt(possibleValues.length)];
        speedY = possibleValues[rand.nextInt(possibleValues.length)];
    }
    
    
    /**
     * Az emberek aktuális állapota alapján visszaadja a megfelelő képet, amivel reprezentálni lehet őket a játéktáblán.
     * @return az aktuálisan kirajzolandó kép
     */
    public abstract Image getImage();
}
