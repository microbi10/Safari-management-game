package com.emft.safari.model.plants;

import com.emft.safari.model.animals.Herbivore;
import com.emft.safari.model.utilities.Size;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;


public abstract class Plant {
    
    protected Position pos;
    protected int actualLifeTime;
    protected final ContentPanel contentPanel;
    protected float sizePoint;
    protected int valueMultiplier;
    protected Size size;
    protected boolean isEatable;
    protected boolean isAlive;
    protected int imageSize;
    private static final Map<String, Image> imageCache = new HashMap<>();
    
    static {
        for (String type : new String[]{"BaobabTree", "Acacia", "ElephantGrass"}) {
            for (Size size : Size.values()) {
                loadImage(type, size);
            }
        }
    }
    
    
    public Plant(Position pos, ContentPanel contentPanel){
        this.actualLifeTime = 0;
        this.pos = pos;
        this.sizePoint = 1;
        this.size = Size.SMALL;
        this.imageSize = 80;
        this.contentPanel = contentPanel;
        this.isAlive = true;
    }
    
    
    public int getImageSize() {
        return imageSize;
    }
    
    public Position getPosition() {
        return pos;
    }
    
    public boolean isEatable() {
        return isEatable;
    }
    
    
    /**
     * A növények növekedésének szabályozásáért felelős.
     */
    protected abstract void grow();
    
    
    /**
     * A növények elfogyasztásának kezeléséért felelős.
     */
    public void decreaseSize(){
        sizePoint -= 20;
        
        if (sizePoint < 1) {
            sizePoint = 1;
        }
    }
    
    
    /**
     * Betölti a növényekhez tartozó képeket a statikus tárolóba (Map).
     * Kezeli a képek betöltése során előkerülő hibákat.
     * @param type növény típusa
     * @param size növény mérete
     */
    public static void loadImage(String type, Size size) {
        String key = type + "_" + size;

        if (!imageCache.containsKey(key)) {
            String imagePath = "/pictures/" + type.toLowerCase() + "_" + size.toString().toLowerCase() + ".png";

            URL imgUrl = Plant.class.getResource(imagePath);

            if (imgUrl == null) {
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }

            Image img = new ImageIcon(imgUrl).getImage();
            imageCache.put(key, img);
        }
    }
    
    
    /**
     * A növények aktuális állapota alapján visszaadja a megfelelő képet, amivel reprezentálni lehet őket a játéktáblán.
     * @return az aktuálisan kirajzolandó kép
     */
    public Image getImage() {
        return imageCache.get(getClass().getSimpleName() + "_" + size);
    }

    
    /**
     * A növények idő szerinti növekedésének kezeléséért felelős.
     */
    public void changeByTime() {
        if (!isBeingEaten()) {
            actualLifeTime++;
        
            if ((actualLifeTime >= 800 && actualLifeTime < 1300) || (sizePoint > 10 && sizePoint < 65)) {
                size = Size.MEDIUM;
                imageSize = 90;
                sizePoint = Math.min(sizePoint + 0.1f, 100f);
                isEatable = true;

                //System.out.println("Medium+");
                //System.out.println(sizePoint);

            }
            else if ((actualLifeTime >= 1300) || (sizePoint >= 65 && sizePoint <= 100)) {
                size = Size.LARGE;
                imageSize = 100;
                sizePoint = Math.min(sizePoint + 0.1f, 100f);
                isEatable = true;
             //  System.out.println("Large+");

                //System.out.println(sizePoint);
            }
            else {
                size = Size.SMALL;
                imageSize = 80;
                sizePoint = Math.min(sizePoint + 0.1f, 100f);
                
                if (sizePoint < 1) {
                    sizePoint = 1;
                }
                
                isEatable = false;

            //    System.out.println("Small+");
              //  System.out.println(sizePoint);

            }
        }
        else {
            sizePoint -= 0.5f;
            
            if (sizePoint < 1) {
                sizePoint = 1;
            }
        }
    }

    
    /**
     * Ellenőrzi, hogy épp eszi-e a növényt egy/több növényevő állat.
     * @return eszik-e a növényt
     */
    public boolean isBeingEaten() {
        for (Herbivore herbivore : contentPanel.getHerbivores()) {
            if (!herbivore.isDead() && herbivore.isEating() && pos.distance(herbivore.getPosition()) < 40) {
                return true;
            }
        }
        
        return false;
    }
}