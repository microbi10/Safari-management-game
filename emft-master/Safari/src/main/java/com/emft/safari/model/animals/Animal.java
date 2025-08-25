package com.emft.safari.model.animals;

import com.emft.safari.model.buildables.Lake;
import com.emft.safari.model.plants.Plant;
import com.emft.safari.model.utilities.State;
import com.emft.safari.model.utilities.Age;
import static com.emft.safari.model.utilities.GameSpeed.DAY;
import static com.emft.safari.model.utilities.GameSpeed.HOUR;
import static com.emft.safari.model.utilities.GameSpeed.WEEK;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;


public abstract class Animal {
    
    protected static final int MIDDLE_AGE_THRESHOLD = 2500;
    protected static final int OLD_AGE_THRESHOLD = 5000;
    protected int hungerThreshold;
    protected int thirstThreshold;
    protected int sicknessThreshold;
    
    protected int actualLifeTime;
    protected int maxLifeTime;
    protected Age age;
    
    protected Position pos;
    protected Position targetPos;
    protected double speedX;
    protected double speedY;
    protected double prevSpeedX;
    
    protected final ContentPanel contentPanel;
    protected List<Position> waterSources;

    protected int hunger; 
    protected int thirst;
    protected int sickness;

    protected boolean isTired;
    protected boolean isSick;
    protected boolean isDecayed;
    protected boolean isChipped;
    
    protected State state;

    private Timer timer;
    private Timer restTimer;
    private Timer decayTimer;
    private double activityProgress;
    
    protected int childCount;
    
    protected int imageSize;
    public static final Map<String, Image> imageCache = new HashMap<>();

    
    // Statikus inicializáló blokk, ami előre betölt minden képet:
    static {
        for (String type : new String[]{"Lion", "Hyena", "Elephant", "Giraffe"}) {
            for (Age age : Age.values()) {
                loadAnimalImage(type, age, null);         // Alapértelmezett
                loadAnimalImage(type, age, "resting");    // Pihenés
            }

            loadAnimalImage(type, Age.OLD, "dead");       // Halott (idős kortól)
        }
        
        // Egyéb képek egyenkénti betöltése:
        loadImage("corpse", "/pictures/corpse.png");
        loadImage("energy", "/pictures/energy.png");
        loadImage("zzz", "/pictures/zzz.png");
        loadImage("water_drop", "/pictures/water_drop.png");
        loadImage("leaf", "/pictures/leaf.png");
        loadImage("meat", "/pictures/meat.png");
    }
    
    
    public Animal(Position pos, Age age, ContentPanel contentPanel){
        this.actualLifeTime = 0;
        this.maxLifeTime = 7000;
        this.pos = pos;
        this.contentPanel = contentPanel;
        this.targetPos = contentPanel.getUniquePosition();
        setRandomSpeed();
        this.hunger = 0;
        this.thirst = 0;
        this.sickness = 0;
        this.sicknessThreshold = 800;
        this.age = age;
        this.state = State.MOVING;
        this.waterSources = new ArrayList<>();
        this.isTired = false;
        this.isSick = false;
        this.isDecayed = false;
        this.isChipped = false;
        this.activityProgress = 0;
        this.childCount = 0;
        setImageSizeByAge();
        setActualLifeTimeByAge();
    };
    
    
    public Position getPosition() {
        return pos;
    }
    
    public Position getTargetPosition() {
        return targetPos;
    }
    
    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }
    
    public double getPrevSpeedX() {
        return prevSpeedX;
    }
    
    public Age getAge() {
        return age;
    }
    
    public int getHunger() {
        return hunger;
    }
    
    public int getThirst() {
        return thirst;
    }
    
    public int getSickness() {
        return sickness;
    }
    
    public int getActualLifeTime() {
        return actualLifeTime;
    }
    
    public double getActivityProgress() {
        return activityProgress;
    }
    
    public int getImageSize() {
        return imageSize;
    }
    
    public int getChildCount() {
        return childCount;
    }
    
    public List<Position> getWaterSources() {
        return waterSources;
    }
    
    public int getMaxLifeTime() {
        return maxLifeTime;
    }
    
    public boolean isDead() {
        return state == State.DEAD;
    }
    
    /**
     * Ellenőrzi, hogy az állat idősödésbe halt-e bele.
     * @return idősödésbe halt-e bele
     */
    public boolean isDeadFromAge() {
        return isDead() && maxLifeTime <= actualLifeTime;
    }
    
    /**
     * Ellenőrzi, hogy az állat éhségbe vagy szomjúságba halt-e bele.
     * @return éhségbe vagy szomjúságba halt-e bele
     */
    public boolean isDeadFromStatusEffect() {
        return isDead() && (hunger > hungerThreshold || thirst > thirstThreshold);
    }
    
    public abstract boolean isThirsty();
    
    public abstract boolean isHungry();
    
    public boolean isDrinking() {
        return state == State.DRINKING;
    }
    
    public boolean isEating() {
        return state == State.EATING;
    }
    
    public boolean isResting() {
        return state == State.RESTING;
    }
    
    public boolean isMoving() {
        return state == State.MOVING;
    }
    
    public boolean isTired() {
        return isTired;
    }
    
    public boolean isSick() {
        return isSick;
    }
    
    public boolean isDecayed() {
        return isDecayed;
    }
    
    public boolean isChipped() {
        return isChipped;
    }
    
    public void buyChip() {
        isChipped = true;
    }
    
    
    /**
     * Ellenőrzi, hogy csoportosítható-e két állat.
     * @param a1 egyik állat
     * @param a2 másik állat
     * @return csoportosulhat-e a kát állat
     */
    public static boolean isGroupable(Animal a1, Animal a2) {
        return a1 != a2 && a1.getClass() == a2.getClass() &&
               a1.getPosition().distance(a2.getPosition()) < 120 &&
               a1.isMoving() && a2.isMoving() && !a1.isTired() && !a2.isTired() &&
               !a1.isThirsty() && !a2.isThirsty() && !a1.isHungry() && !a2.isHungry();
    }

    
    /**
     * Ellenőrzi, hogy párzásképes-e egy állat.
     * @return párzásképes-e az állat
     */
    public boolean canMate() {
        return age != Age.YOUNG && isMoving() && !isHungry() && !isThirsty() && !isTired() && childCount == 0;
    }
    
    
    /**
     * A konstruktorban az állat bármilyen kezdőéletkort kaphat, a képének méretét ennek megfelelően állítjuk be.
     */
    protected final void setImageSizeByAge() {
        switch (age) {
            case YOUNG -> imageSize = 40;
            case MIDDLE_AGED -> imageSize = 50;
            case OLD -> imageSize = 60;
        }
    }
    
    
    /**
     * A konstruktorban az állat bármilyen kezdőéletkort kaphat, az életkor pontjainak aktuális értékét ennek megfelelően állítjuk be.
     */
    protected final void setActualLifeTimeByAge() {
        switch (age) {
            case YOUNG -> actualLifeTime = 0;
            case MIDDLE_AGED -> actualLifeTime = MIDDLE_AGE_THRESHOLD;
            case OLD -> actualLifeTime = OLD_AGE_THRESHOLD;
        }
    }
    
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
    
    public void setPosition(Position pos) {
        this.pos = pos;
    }
    
    public void setTargetPosition(Position targetPos) {
        this.targetPos = targetPos;
    }
    
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
    
    
    /**
     * Az állatok előző sebesség adattagjának frissítéséért felelős.
     * Ez ahhoz szükséges, hogy eldöntse álló helyzetben, hogy melyik irányba nézzen.
     */ 
    protected void updatePrevSpeedX () {
        if (speedX != 0) {
            prevSpeedX = speedX;
        }
    }
    
    
    /**
     * Az állatok sebességének véletlenszerű beállításáért felelős.
     */
    public final void setRandomSpeed() {
        Random rand = new Random();
        int[] possibleValues = {-2, -1, 1, 2};
        speedX = possibleValues[rand.nextInt(possibleValues.length)];
        speedY = possibleValues[rand.nextInt(possibleValues.length)];
        updatePrevSpeedX();
    }
    
    
    /** 
     * Az állatok célpozíciójának véletlenszerű beállításáért felelős.
     */
    public void setRandomTargetPos() {
        targetPos = contentPanel.getUniqueTargetPosition(targetPos);
    }

        
    /** 
     * Az állatok mozgásáért felelős.
     * Az eközben bekövetkező lehetséges eseményeket is kezeli.
     */
    public abstract void move();
    
    
    /**
     * Egy adott pozíció megközelítéséért felelős.
     * @param targetPos a célpozíció, mely felé az állat tart
     */
    protected void moveTowards(Position targetPos) {        
        double deltaX = targetPos.getX() - pos.getX();
        double deltaY = targetPos.getY() - pos.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        if (distance == 0) {
            return;
        }
        
        double speedMultiplier = Math.min(3, distance / 3);

        speedX = (deltaX / distance) * speedMultiplier;
        speedY = (deltaY / distance) * speedMultiplier;
        updatePrevSpeedX();
    }
    
    
    /** 
     * A tavakba ütközés és visszapattanás/kikerülés kezeléséért felelős.
     */
    protected void handleLakeCollision () {
        if (!isThirsty() && !isDrinking()) {
            for (Lake lake : contentPanel.getLakes()) {
                double deltaX = pos.getX() - lake.getPosition().getX();
                double deltaY = pos.getY() - lake.getPosition().getY();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance < 70) {
                    double avoidX = pos.getX() + (deltaX / distance) * 200;
                    double avoidY = pos.getY() + (deltaY / distance) * 200;

                    setTargetPosition(new Position((int)avoidX, (int)avoidY));

                    double speedMultiplier = 5;
                    speedX = (deltaX / distance) * speedMultiplier;
                    speedY = (deltaY / distance) * speedMultiplier;
                   
                }
            }
        }
    }
    
    
    /** 
     * A növényekbe ütközés és visszapattanás/kikerülés kezeléséért felelős.
     */
    protected void handlePlantCollision () {
        if ((this instanceof Herbivore && !isHungry() || this instanceof Carnivore) && !isEating()) {
            for (Plant plant : contentPanel.getPlants()) {
                double deltaX = pos.getX() - plant.getPosition().getX();
                double deltaY = pos.getY() - plant.getPosition().getY();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance < 50) {
                    double avoidX = pos.getX() + (deltaX / distance) * 200;
                    double avoidY = pos.getY() + (deltaY / distance) * 200;

                    setTargetPosition(new Position((int)avoidX, (int)avoidY));

                    double speedMultiplier = 5;
                    speedX = (deltaX / distance) * speedMultiplier;
                    speedY = (deltaY / distance) * speedMultiplier;
                   
                }
            }
        }
    }
    
    
    /** 
     * A más típusú állatokba ütközés és visszapattanás/kikerülés kezeléséért felelős.
     */
    protected void handleAnimalCollision () {
        for (Animal other : contentPanel.getAnimals()) {
            if (this != other && this.getClass() != other.getClass() && !isEating() && !other.isEating() && !isDrinking() && !other.isDrinking()) {
                double deltaX = pos.getX() - other.getPosition().getX();
                double deltaY = pos.getY() - other.getPosition().getY();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance < 35) {
                    double avoidX = pos.getX() + (deltaX / distance) * 100;
                    double avoidY = pos.getY() + (deltaY / distance) * 100;

                    setTargetPosition(new Position((int)avoidX, (int)avoidY));

                    double speedMultiplier = 5;
                    speedX = (deltaX / distance) * speedMultiplier;
                    speedY = (deltaY / distance) * speedMultiplier;

                }
            }
        }
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
     * Ellenőrzi, hogy falba ütközött-e az állat.
     * @return falba ütközött-e
     */
    protected boolean isCollidingWithWall() {
        return pos.getX() - imageSize / 2 <= 0 || pos.getX() + imageSize / 2 >= ContentPanel.WINDOW_WIDTH ||
               pos.getY() - imageSize / 2 <= 0 || pos.getY() + imageSize / 2 >= ContentPanel.WINDOW_HEIGHT;
    }
    
    
    /**
     * Az állatok szaporodásának megpróbálásáért felelős (alacsony eséllyel).
     */
    public void tryToReproduce() {
        for (Animal other : contentPanel.getAnimals()) {
            if (isGroupable(this, other) && canMate() && other.canMate()) {                
                double avgX = (pos.getX() + other.getPosition().getX()) / 2;
                double avgY = (pos.getY() + other.getPosition().getY()) / 2;
                
                Random rand = new Random();
                double reproductionChance = rand.nextInt(100);

                if (reproductionChance < 1) {
                    contentPanel.addAnimal(this.getClass().getSimpleName(), Age.YOUNG, new Position((int)avgX, (int)avgY));
                    childCount++;
                    other.setChildCount(other.getChildCount() + 1);
                }
            }
        }
    }   

    
    /**
     * Elmenti a közeli tavak pozícióját a listájába.
     */
    protected void checkNearbyWaterSources() {
        for (Lake lake : contentPanel.getLakes()) {
            if (!waterSources.contains(lake.getPosition()) && pos.distance(lake.getPosition()) < 220) {
                waterSources.add(lake.getPosition());
            }
        }
    }
    
    
    /**
     * Elmenti a közeli élelemforrásokat a listájába.
     */
    protected abstract void checkNearbyFoodSources();
    

    /**
     * Megkeresi a legközelebbi tavat, odamegy hozzá, és iszik belőle.
     */
    protected void findNearestWaterSource() {   
        if (!waterSources.isEmpty()) {
            Position nearestLakePos = waterSources.get(0);
            
            for (Position otherLakePos : waterSources) {
                if (pos.distance(nearestLakePos) > pos.distance(otherLakePos)) {
                    nearestLakePos = otherLakePos;
                }
            }

            if (isMoving() && pos.distance(nearestLakePos) > 30) {
                setTargetPosition(nearestLakePos);
            }
            else {
                startActivity(State.DRINKING);
            }
        }
    }
    
    
    /**
     * Megkeresi a legközelebbi élelemforrást, odamegy hozzá, és eszik belőle.
     */
    protected abstract void findNearestFoodSource();
   
    
    /**
     * Az állapotok idő szerinti változásainak kezeléséért felelős.
     * Ide tartozik az idősödés, halál, éhség, szomjúság, betegség.
     */
    public void changeByTime() {
        if (isDead()) {
            return;
        }
        
        // Szomjúság, éhség, életkor, betegség növelése:
        if (isMoving()) {
            increaseThirst();
            increaseHunger();
            increaseAge();
            increaseSickness();
        }
        
        // Lebetegedés kezelése:
        if (!isSick) {
            Random rand = new Random();
            int sicknessChance = rand.nextInt(3);

            if (sickness >= 600 && sicknessChance < 1) {
                isSick = true;
            }
            else if (sickness >= 600 && sicknessChance >= 1) {
                sickness = 0;
            }
        }
        
        
        // Idősödési fázisok:
        
        // Fiatal --> Középkorú:
        if (age == Age.YOUNG && actualLifeTime >= MIDDLE_AGE_THRESHOLD) {
            age = Age.MIDDLE_AGED;
            setImageSizeByAge();
        }

        // Középkorú --> Idős:
        if (age == Age.MIDDLE_AGED && actualLifeTime >= OLD_AGE_THRESHOLD) {
            age = Age.OLD;
            setImageSizeByAge();
            childCount = 0;
        } 
    
        
        // Halál (idős kortól/éhségtől/szomjúságtól):
        if (actualLifeTime >= maxLifeTime ||
            hunger > hungerThreshold || thirst > thirstThreshold) {
            die();
        }
        
        
        // Betegség miatti halál vagy meggyógyulás:
        if (!isDead() && isSick) {
            Random rand = new Random();
            int sicknessDeathChance = rand.nextInt(4); 

            if (sickness > sicknessThreshold && sicknessDeathChance < 1) {
                die();
            }
            
            if (sickness > sicknessThreshold && sicknessDeathChance >= 1) {
                isSick = false;
                sickness = 0;
            }
        }
    }
    
    
    /**
     * Növeli az éhség értékét.
     * A növekedés mértékét befolyásolja az állat kora és a játék sebessége.
     */
    protected void increaseHunger() {
        Random rand = new Random();
        int hungerBoost = rand.nextInt(3) + 1;
        
        switch(contentPanel.getGameSpeed()) {
            case HOUR -> hungerBoost = rand.nextInt(3) + 1;
            case DAY -> hungerBoost = rand.nextInt(3) + 4;
            case WEEK -> hungerBoost = rand.nextInt(3) + 7;
        }
        
        hunger += hungerBoost;

        if (age == Age.OLD) {
            hunger += hungerBoost + 1;
        }
    };
    
    
    /**
     * Növeli a szomjúság értékét.
     * A növekedés mértékét befolyásolja a játék sebessége.
     */
    protected void increaseThirst(){
        Random rand = new Random();
        int thirstBoost = rand.nextInt(3);
        
        switch(contentPanel.getGameSpeed()) {
            case HOUR -> thirstBoost = rand.nextInt(3);
            case DAY -> thirstBoost = rand.nextInt(3) + 2;
            case WEEK -> thirstBoost = rand.nextInt(3) + 5;
        }
        
        thirst += thirstBoost;
    };
    
    
    /**
     * Növeli az életkor értékét.
     * A növekedés mértékét befolyásolja a játék sebessége.
     */
    protected void increaseAge() {
        Random rand = new Random();
        int ageBoost = rand.nextInt(6);
        
        switch(contentPanel.getGameSpeed()) {
            case HOUR -> ageBoost = rand.nextInt(6);
            case DAY -> ageBoost = rand.nextInt(6) + 2;
            case WEEK -> ageBoost = rand.nextInt(6) + 4;
        }

        actualLifeTime += ageBoost;
    }
    
    
    /**
     * Növeli a betegség értékét.
     * A növekedés mértékét befolyásolja a játék sebessége.
     */
    protected void increaseSickness() {
        Random rand = new Random();
        int sicknessBoost = rand.nextInt(3);
        
        switch(contentPanel.getGameSpeed()) {
            case HOUR -> sicknessBoost = rand.nextInt(3);
            case DAY -> sicknessBoost = rand.nextInt(3) + 2;
            case WEEK -> sicknessBoost = rand.nextInt(3) + 4;
        }
        
        sickness += sicknessBoost;
    }


    /**
     * Az állatok állapotának/eseményének indításáért felelős.
     * @param newState új állapot
     */
    protected void startActivity(State newState) {
        if (!isMoving()) {
            return;
        }
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        switch (newState) {
            case DRINKING -> thirst = 0;
            case EATING   -> hunger = 0;
            default -> {}
        }
        
        // Új állapot beállítása, állat leállítása:
        state = newState;
        speedX = 0;
        speedY = 0;
        
        // Folyamat elkezdése:
        timer = new Timer(30, e -> updateProgress(newState));
        timer.start();
    }

    
    /**
     * Az állatok állapotának/eseményének frissítéséért felelős (az állat feletti csíkhoz).
     * @param newState új állapot
     */
    protected void updateProgress(State newState) {
        if (!contentPanel.getGameEngine().isPaused()) {
            // Állat által végzett folyamat sebességének beállítása játékbeli idő gyorsasága alapján:
            double activitySpeed = 1.0 / 100;

            switch(contentPanel.getGameSpeed()) {
                case HOUR -> activitySpeed = 1.0 / 100;
                case DAY -> activitySpeed = 1.0 / 70;
                case WEEK -> activitySpeed = 1.0 / 50;
            }

            activityProgress += activitySpeed;
        }
        
        if (activityProgress >= 1.0) {
            stopActivity(newState);
        }
    }

    
     /**
     * Az állatok állapotának/eseményének befejezéséért felelős.
     * @param newState új állapot
     */
    protected void stopActivity(State newState) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        if (isDead()) {
            return;
        }
        
        state = State.MOVING;
        activityProgress = 0;

        if (newState == State.EATING) {
            isTired = true;
            
            // Állat étkezés utáni elálmodásának sebessége a játékbeli idő gyorsasága alapján:
            int exhaustionSpeed = 5000;

            switch(contentPanel.getGameSpeed()) {
                case HOUR -> exhaustionSpeed = 5000;
                case DAY -> exhaustionSpeed = 3000;
                case WEEK -> exhaustionSpeed = 1000;
            }
            
            restTimer = new Timer(exhaustionSpeed, e -> {
                if (!isDead()) {
                    isTired = false;
                    
                    if (this instanceof Herbivore herbivore) {
                        if (herbivore.isFleeing()) {
                            return;
                        }
                    }
                    
                    startActivity(State.RESTING);
                }
            });
            restTimer.setRepeats(false);
            restTimer.start();
        }

        setRandomSpeed();
    }


    /**
     * Az állat halálának kezeléséért felelős.
     */
    public void die() {
        // Állat megölése, leállítása (még nem tűnik el, dög lesz belőle)
        state = State.DEAD;
        speedX = 0;
        speedY = 0;
        
        // Állat elrohadásának gyorsasága a játékbeli idő gyorsasága alapján:
        int decaySpeed = 7000;

        switch(contentPanel.getGameSpeed()) {
            case HOUR -> decaySpeed = 7000;
            case DAY -> decaySpeed = 4000;
            case WEEK -> decaySpeed = 2000;
        }
        
        decayTimer = new Timer(decaySpeed, e -> decay());
        decayTimer.setRepeats(false);
        decayTimer.start();
    }
    
    
    /**
     * Az állatok tetemének eltűnéséért/elrohadásáért felelős.
     */
    protected void decay() {
        if (!isBeingEaten()) {
            isDecayed = true;
        }
        else {
            
            // Állat elrohadásának (miután ettek belőle) gyorsasága a játékbeli idő gyorsasága alapján:
            int tryDecayingAgainSpeed = 4000;

            switch(contentPanel.getGameSpeed()) {
                case HOUR -> tryDecayingAgainSpeed = 4000;
                case DAY -> tryDecayingAgainSpeed = 2000;
                case WEEK -> tryDecayingAgainSpeed = 1000;
            }
            
            decayTimer = new Timer(tryDecayingAgainSpeed, e -> decay());
            decayTimer.setRepeats(false);
            decayTimer.start();
        }
    }
    
    
    /**
     * Eldönti, hogy egy adott állat tetemét eszi-e egy ragadozó éppen.
     * @return eszik-e épp a tetemet
     */
    public boolean isBeingEaten() {
        if (!isDead()) {
            return false;
        }
        
        for (Carnivore carnivore : contentPanel.getCarnivores()) {
            if (!carnivore.isDead() && carnivore.isEating() && pos.distance(carnivore.getPosition()) < 40) {
                return true;
            }
        }
       
       return false;
    }
    
    
    /**
     * Az állatok gyógyításáért felelős.
     */
    public void getHealed() {
        isSick = false;
        sickness = 0;
    }
    
    
    /**
     * Az állatok aktuális állapota alapján visszaadja a megfelelő képet, amivel reprezentálni lehet őket a játéktáblán.
     * @return az aktuálisan kirajzolandó kép
     */
    public Image getImage() {
        if (isDeadFromAge() || isDeadFromStatusEffect()) {
            return imageCache.get(getClass().getSimpleName() + "_" + age + "_dead");
        }
        else if (isDead()) {
            return imageCache.get("corpse");
        }
        else if (isResting()) {
            return imageCache.get(getClass().getSimpleName() + "_" + age + "_resting");
        }
        
        return imageCache.get(getClass().getSimpleName() + "_" + age);
    }
    
    
    /**
     * Az állatok aktuális állapota alapján visszaadja a megfelelő ikont a fejük fölé.
     * @return az aktuálisan kirajzolandó ikon
     */
    public Image getStatusIcon() {
        if (isTired) {
            return imageCache.get("energy");
        }
        else if (isResting()) {
            return imageCache.get("zzz");
        }
        else if (isThirsty()) {
            return imageCache.get("water_drop");
        }
        else if (isHungry() && !isDrinking()) {
           if (this instanceof Herbivore ) {
               return imageCache.get("leaf");
           }
           else if (this instanceof Carnivore) {
               return imageCache.get("meat");
           }
        }
        
        return null;
    }
    
    
    /**
     * Betölti az állatok különböző állapotaihoz tartozó képeket a statikus tárolóba (Map).
     * Kezeli a képek betöltése során előkerülő hibákat.
     * @param type állat típusa
     * @param age állat életkora
     * @param state állat állapota
     */
    private static void loadAnimalImage(String type, Age age, String state) {
        try {
            String path;
        
            if (state != null) {
                path = String.format("/pictures/%s_%s_%s.png", type.toLowerCase(), age.toString().toLowerCase(), state);
            }
            else {
                path = String.format("/pictures/%s_%s.png", type.toLowerCase(), age.toString().toLowerCase());
            }

            URL imageUrl = Animal.class.getResource(path);

            if (imageUrl == null) {
                throw new IllegalArgumentException("Image not found: " + path);
            }

            String key = type + "_" + age + (state != null ? "_" + state : "");

            imageCache.put(key, new ImageIcon(imageUrl).getImage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("itt a hiba az allatok osztalyban");
            System.err.println("Image can't be loaded: " + type + " " + age + (state != null ? " " + state : ""));
        }
    }
    

    /**
     * Betölti az állatokhoz tartozó egyéb képeket a statikus tárolóba (Map).
     * Kezeli a képek betöltése során előkerülő hibákat.
     * @param key kulcs
     * @param path kép elérési útvonala
     */
    private static void loadImage(String key, String path) {
        try {
            URL iconUrl = Animal.class.getResource(path);
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



    // Csak teszteléshez (package-private setterek):
    void setMoving() {
        state = State.MOVING;
    }
    
    void setEating() {
        state = State.EATING;
    }
    
    void setDrinking() {
        state = State.DRINKING;
    }
    
    void setResting() {
        state = State.RESTING;
    }
    
    void setTired() {
        isTired = true;
    }
    
    void setSick() {
        isSick = true;
    }

    void setHunger(int hunger) {
        this.hunger = hunger;
    }

    void setThirst(int thirst) {
        this.thirst = thirst;
    }
    
    void setWaterSources(List<Position> waterSources) {
        this.waterSources = waterSources;
    }
    
    void setActualLifeTime(int actualLifeTime) {
        this.actualLifeTime = actualLifeTime;
    }
}
