package com.emft.safari.view;

import com.emft.safari.model.buildables.*;
import com.emft.safari.model.people.*;
import com.emft.safari.model.animals.*;
import com.emft.safari.model.equipment.Airship;
import com.emft.safari.model.equipment.Camera;
import com.emft.safari.model.equipment.Drone;
import com.emft.safari.model.equipment.Jeep;
import com.emft.safari.model.equipment.MobileDevice;
import com.emft.safari.model.equipment.Station;
import com.emft.safari.model.plants.*;
import com.emft.safari.model.utilities.Age;
import static com.emft.safari.model.utilities.GameLevel.EASY;
import static com.emft.safari.model.utilities.GameLevel.HARD;
import static com.emft.safari.model.utilities.GameLevel.MEDIUM;
import com.emft.safari.model.utilities.GameSpeed;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.utilities.Time;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import java.util.Random;

import java.util.stream.Collectors;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;


public class ContentPanel extends JPanel {
    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 1200;  
    
    private SafariGUI safariGUI;
    private Director director;
    private SafariGameEngine gameEngine;
    private Time time;

    private Graphics2D g2d; 
    private Image backgroundImage;
    
    private final List<Animal> animals;
    private final List<Plant> plants;
    private final List<Lake> lakes;
    private final List<Vet> vets;
    private final List<Ranger> rangers;
    private final List<Poacher> poachers;
    private final List<Jeep> jeepCollection;
    private final List<Camera> cameraCollection;
    private final List<Drone> droneCollection;
    private final List<Airship> airshipCollection;
    
    private final List<Animal> newAnimals;
    private final List<Plant> newPlants;
    private final List<Lake> newLakes;
    private final List<Vet> newVets;
    private final List<Ranger> newRangers;
    private final List<Poacher> newPoachers;
    
    private final List<Animal> removableAnimals;
    private final List<Plant> removablePlants;
    private final List<Lake> removableLakes;
    private final List<Vet> removableVets;
    private final List<Ranger> removableRangers;
    private final List<Poacher> removablePoachers;
  
    public boolean actionBuyRoute = false;
    public boolean actionBuyCamera=false;
    public boolean actionRemoveRoute = false;

    private MessageBox messageBox;
    private ArrayList<MessageBox> messageBoxes;
    private MessageBox endGameMessage;
    
    private final Route route;
    private List<Route> routes;  
    private ArrayList<Position> routeSection;
    public boolean deleted;    
    private Position pathPosition;
    private final int ROUTE_COST = 2;
    private Image jeepIconF;
    private Image jeepIconB;
    private Image jeepIconF0;
    private Image jeepIconB0;
    private Image cameraIcon; 
    private Image stationIcon;
    private Image droneIcon;
    private Image airshipIcon;
    private MessageBox endGamaMessage;
    private ArrayList<MessageBox> messBoxes;

    private List<Station> stationCollection;
    private MiniMap miniMap;

    private float brightness = 1.0f;

    public Position buyingDeviceCenterCircle=null;
    public Position buyingDevicePointCircle=null;
    
    private JProgressBar progressBar; 
    
    public ContentPanel(Director director, SafariGameEngine gameEngine,SafariGUI safariGUI){
        super();
        this.stationCollection=new ArrayList<>();
        this.airshipCollection=new ArrayList<>();
        this.droneCollection=new ArrayList<>();
        this.safariGUI = safariGUI;
        this.gameEngine = gameEngine;
        this.director = director;
        this.cameraCollection=new ArrayList<>();
        this.jeepCollection = new ArrayList<>();
        this.messageBoxes = new ArrayList<>();   
        this.routes = new ArrayList<>();
        this.routeSection = new ArrayList<>();
        this.routes.add(new Route(new Position(20,30), this));
        this.pathPosition = new Position(routes.get(0).getPoints().get(0).getX(),
                                         routes.get(0).getPoints().get(0).getY());
        try{
           jeepIconF = new ImageIcon(getClass().getResource("/pictures/jeep_f.png")).getImage();
           jeepIconB = new ImageIcon(getClass().getResource("/pictures/jeep_b.png")).getImage();
           jeepIconF0 = new ImageIcon(getClass().getResource("/pictures/jeep_f0.png")).getImage();
           jeepIconB0 = new ImageIcon(getClass().getResource("/pictures/jeep_b0.png")).getImage();
           cameraIcon=new ImageIcon(getClass().getResource("/pictures/camera.png")).getImage();
           stationIcon=new ImageIcon(getClass().getResource("/pictures/station1.png")).getImage();
           droneIcon=new ImageIcon(getClass().getResource("/pictures/drone.png")).getImage();
           airshipIcon=new ImageIcon(getClass().getResource("/pictures/airship.png")).getImage();
           
        }
        catch (Exception e){
          //  System.out.println("Image can't be loaded" + e.getMessage());
        }
        
        pathPosition = routes.get(0).getPoints().get(0);
        this.route = this.routes.get(0);
        
                
        // Alap objektumok listáinak inicializálása (állatok, növények, tavak):
        this.animals = new ArrayList<>();
        this.newAnimals = new ArrayList<>();
        this.removableAnimals = new ArrayList<>();      

        this.plants = new ArrayList<>();
        this.newPlants = new ArrayList<>();
        this.removablePlants = new ArrayList<>();

        this.lakes = new ArrayList<>();
        this.newLakes = new ArrayList<>();
        this.removableLakes = new ArrayList<>();
        
        
        // Emberek listáinak inicializálása (orvos, vadőr, orvvadász):
        this.vets = new ArrayList<>();
        this.newVets = new ArrayList<>();
        this.removableVets = new ArrayList<>();
        
        this.rangers = new ArrayList<>();
        this.newRangers = new ArrayList<>();
        this.removableRangers= new ArrayList<>();
        
        this.poachers = new ArrayList<>();
        this.newPoachers = new ArrayList<>();
        this.removablePoachers = new ArrayList<>();
        
        
        this.time = new Time(gameEngine.getGameSpeed());
        
        ///ProgressBar
        this.progressBar=new JProgressBar(1,0,100);
        progressBar.setIndeterminate(false);
        progressBar.setBounds(30, 120, 15, 200);
        progressBar.setValue(0);
        this.add(progressBar, BorderLayout.SOUTH);
        progressBar.setForeground(new Color(124, 252, 0)); 
        
        
        // Kezdeti objektumok lehelyezése (fontos a sorrend):
        initLakes();
        initPlants();
        initAnimals();

        setEndMessageBox();
        
    }
    public void setProgressBar(){
        int minMonth = switch(gameEngine.getGameLevel()) {
                case EASY -> 3;
                case MEDIUM -> 6;
                case HARD -> 12;
            };
        int minhour=minMonth*30*24;
        this.progressBar.setValue((int)(100*(time.getTimeInHours()*1.0/minhour)));
    }

    /**
     * A játék végén megjelenő üzenet kezeléséért felelős.
     */
    public final void setEndMessageBox() {
        endGameMessage = new MessageBox(new Point(300,200));
        endGameMessage.setEnabledNoButton(false);
        endGameMessage.remove(endGameMessage.getNoButton());
        this.add(endGameMessage);
        
        endGameMessage.getYesButton().addActionListener(e ->{
            this.safariGUI.switchToOtherPanel("homePage");
        });
    }
    
    
    public void  messageBoxObserv(){
        messageBoxes.forEach(MessageBox::checkByTime);
    }
    
    public void moveJeeps(){
        jeepCollection.forEach(Jeep::step);
    }
    
    public List<Lake> getLakes() {
        return lakes;
    }
    
    public List<Plant> getPlants() {
        return plants;
    }
    
    public List<Animal> getAnimals() {
        return animals;
    }
    
    public Route getRoute() {
        return route;
    }
    
    public List<Poacher> getPoachers(){
        return poachers;
    }
    
    public List<Ranger> getRangers(){
        return rangers;
    }
    
    public List<Vet> getVets(){
        return vets;
    }
    
    public List<Jeep> getJeeps(){
        return jeepCollection;
    }  
    
    /**
     * Az összes növényevő állat lekérdezéért felelős.
     * @return növényevők listája
     */
    public List<Herbivore> getHerbivores() {
        List<Herbivore> herbivores = new ArrayList<>();
        
        for (Animal animal : this.animals) {
            if (animal instanceof Herbivore herbivore) {
                herbivores.add(herbivore);
            }
        }
        
        return herbivores;
    }
    
    
    /**
     * Az összes húsevő állat lekérdezéért felelős.
     * @return húsevők listája
     */
    public List<Carnivore> getCarnivores() {
        List<Carnivore> carnivores = new ArrayList<>();
        
        for (Animal animal : animals) {
            if (animal instanceof Carnivore carnivore) {
                carnivores.add(carnivore);
            }
        }
        
        return carnivores;
    }


    /**
     * Az összes állat állapotának változtatásáért felelős (mozgás, idősödés, szaporodás, megjelenés, eltűnés).
     */
    public void updateAnimals() {  
        for (Animal animal : animals) {   
            if (!gameEngine.isPaused()) {
                animal.move();
                animal.tryToReproduce();
                animal.changeByTime();
            }
            
            if (animal.isDecayed()) {
                removableAnimals.add(animal);
            }
        }
        
        // Állatok csoportosítása:
        groupSameSpecies();
        
        // Új állatok hozzáadása (születés, vásárlás):
        animals.addAll(newAnimals);
        newAnimals.clear();
        
        // Halott/elrohadt állatok törlése:
        animals.removeAll(removableAnimals);
        removableAnimals.clear();
    }
    
    
    /**
     * Az összes növény állapotának változtatásáért felelős (növekedés, megjelenés, eltűnés).
     */
    public void updatePlants() {        
        for (Plant plant : plants) {
            if (!gameEngine.isPaused()) {
                plant.changeByTime();
            }
        }
        
        plants.addAll(newPlants);
        newPlants.clear();
        
        plants.removeAll(removablePlants);
        removablePlants.clear();
    }
    
    
    /**
     * Az összes tó állapotának változtatásáért felelős (megjelenés, eltűnés).
     */
    public void updateLakes() {
        lakes.addAll(newLakes);
        newLakes.clear();
        
        lakes.removeAll(removableLakes);
        removableLakes.clear();
    }
    
    
    /**
     * Az összes orvos állapotának változtatásáért felelős (mozgás, gyógyítás, megjelenés, eltűnés).
     */
    public void updateVets() {
        for (Vet vet : vets) {  
            if (!gameEngine.isPaused()) {
                vet.move();
            }
            
            if (vet.isDoneWorking()) {
                removableVets.add(vet);
            }
        } 
        
        vets.addAll(newVets);
        newVets.clear();
        
        vets.removeAll(removableVets);
        removableVets.clear();
    }
    
    
    /**
     * Az összes vadőr állapotának változtatásáért felelős (mozgás, vadászat, megjelenés, eltűnés).
     */
    public void updateRangers() {        
        for (Ranger ranger : rangers) {
            if (!gameEngine.isPaused()) {
                ranger.move();
            }
            
            if (ranger.isDoneWorking()) {
                removableRangers.add(ranger);
            }
        } 
        
        rangers.addAll(newRangers);
        newRangers.clear();
        
        rangers.removeAll(removableRangers);
        removableRangers.clear();
    }
    
    
    /**
     * Az összes orvvadász állapotának változtatásáért felelős (mozgás, vadászat, megjelenés, eltűnés).
     */
    public void updatePoachers() {       
        for (Poacher poacher : poachers) {   
            if (!gameEngine.isPaused()) {
                poacher.move();
                poacher.decideTargetAnimal();
            }
        } 
        
        if (!gameEngine.isPaused()) {
            spawnPoachers();
        }
        
        poachers.addAll(newPoachers);
        newPoachers.clear();
        
        poachers.removeAll(removablePoachers);
        removablePoachers.clear();
    }
    

    /**
     * A napszak szerinti sötétedés kezeléséért felelős.
     */
    public void updateDayTime() {
        time = gameEngine.getTime();
        int hour = time.getGameHour();
        int day = time.getGameDay();
    
        float targetBrightness = brightness;

        if (time.getGameDay() > 0) {
            if (day == 0 && hour >= 21) {
                targetBrightness = 0.0f;
            }
            else if (hour < 5 || hour >= 21) {
                targetBrightness = 0.1f;
            }
            else if (hour >= 5 && hour < 8) {
                targetBrightness = lerp(0.1f, 1.0f, hour - 5);
            }
            else if (hour >= 8 && hour < 18) {
                targetBrightness = 1.0f;
            }
            else if (hour >= 18 && hour < 21) {
                targetBrightness = lerp(1.0f, 0.1f, hour - 18);

            }
        }
        else {
            if(time.getGameHour() >= 21) {
                targetBrightness = 0.0f;
            }
            else if(time.getGameHour() >= 18  && time.getGameHour() < 21) {
                targetBrightness = lerp(1.0f, 0.1f, hour - 18);       
            }
        } 

        brightness = lerp(brightness, targetBrightness, 0.05f);
    }

    
    /**
     * Lineáris interpoláció függvénye a sötétedés folyamatosságának kezeléséhez.
     * @param a kezdőérték
     * @param b végérték
     * @param t interpolációs faktor
     * @return interpolált érték
     */
    public static float lerp(float a, float b, float t) {
        return a * (1 - t) + b * t;
    }
    
    
    /**
     * Az egyedi kezdőpozíciók generálásáért felelős.
     * A pálya bizonyos margóján belüli, állatoktól növényektől és tavaktól bizonyos távolságra lévő pozíciókat számít ki.
     * @return egyedi kezdőpozíció
     */
    public Position getUniqueInitPosition() {
        Random rand = new Random();
        Position newPos;
        boolean valid;
    
        do {
            // A pálya szélének kihagyása (100-as margó):
            int x = rand.nextInt(WINDOW_WIDTH - 200) + 100;
            int y = rand.nextInt(WINDOW_HEIGHT - 200) + 100;
            newPos = new Position(x, y);
            valid = true;
    
            // Objektumok közötti távolságok ellenőrzése:
            for (Animal animal : newAnimals) {
                if (newPos.distance(animal.getPosition()) < 70) {
                    valid = false;
                    break;
                }
            }
    
            for (Plant plant : newPlants) {
                if (newPos.distance(plant.getPosition()) < 180) {
                    valid = false;
                    break;
                }
            }
    
            for (Lake lake : newLakes) {
                if (newPos.distance(lake.getPosition()) < 170) {
                    valid = false;
                    break;
                }
            }
    
        } while (!valid);
    
        return newPos;
    }
    

    /**
     * Az egyedi pozíciók generálásáért felelős.
     * A pálya bizonyos margóján belüli, állatoktól növényektől és tavaktól bizonyos távolságra lévő pozíciókat számít ki.
     * @return egyedi pozíció
     */
    public Position getUniquePosition() {
        Random rand = new Random();
        Position newPos;
        boolean valid;
    
        do {
            // A pálya szélének kihagyása (100-as margó):
            int x = rand.nextInt(WINDOW_WIDTH - 200) + 100;
            int y = rand.nextInt(WINDOW_HEIGHT - 200) + 100;
            newPos = new Position(x, y);
            valid = true;
    
            // Objektumok közötti távolságok ellenőrzése:
            for (Animal animal : animals) {
                if (newPos.distance(animal.getPosition()) < 70) {
                    valid = false;
                    break;
                }
            }
    
            for (Plant plant : plants) {
                if (newPos.distance(plant.getPosition()) < 180) {
                    valid = false;
                    break;
                }
            }
    
            for (Lake lake : lakes) {
                if (newPos.distance(lake.getPosition()) < 170) {
                    valid = false;
                    break;
                }
            }
    
        } while (!valid);
    
        return newPos;
    }
    
    
    /**
     * Az állatok egyedi célpozícióinak generálásáért felelős (figyelembe veszi az előző célpozíciót is).
     * A pálya bizonyos margóján belüli, állatoktól növényektől és tavaktól bizonyos távolságra lévő pozíciókat számít ki.
     * @param prevTargetPosition előző célpozíció
     * @return egyedi célpozíció
     */
    public Position getUniqueTargetPosition(Position prevTargetPosition) {
        Random rand = new Random();
        Position newPos;
        boolean valid;
    
        do {
            // A pálya szélének kihagyása (50-es margó):
            int x = rand.nextInt(WINDOW_WIDTH - 100) + 50;
            int y = rand.nextInt(WINDOW_HEIGHT - 100) + 50;
            newPos = new Position(x, y);
            valid = true;
            
            if (newPos.distance(prevTargetPosition) <= 600) {
                valid = false;
            }
    
            for (Plant plant : plants) {
                if (newPos.distance(plant.getPosition()) < 180) {
                    valid = false;
                    break;
                }
            }
    
            for (Lake lake : lakes) {
                if (newPos.distance(lake.getPosition()) < 170) {
                    valid = false;
                    break;
                }
            }    
        } while (!valid);
    
        return newPos;
    }
    

    /**
     * Az állatok hozzáadásáért felelős.
     * @param type állat típusa
     * @param age állat éltekora
     * @param position állat pozíciója
     */
    public void addAnimal(String type, Age age, Position position) {  
        switch (type) {
            case "Lion" -> newAnimals.add(new Lion(position, age, this));
            case "Hyena" -> newAnimals.add(new Hyena(position, age, this));
            case "Elephant" -> newAnimals.add(new Elephant(position, age, this));
            case "Giraffe" -> newAnimals.add(new Giraffe(position, age, this));
            default -> throw new IllegalArgumentException("Adding - Unknown animal type: " + type);
       }
    }
    
    
    /**
     * Az állatok törléséért felelős eladás esetén.
     * @param position kattintás pozíciója
     * @param type állat típusa
     */
    public void removeAnimal(Position position, String type) {
        // A kattintáshoz közeli adott típusú állatok kigyűjtése:
        List<Animal> animalsNearCursor = new ArrayList<>();
        
        for (Animal animal : animals) {
            if (type.equals(animal.getClass().getSimpleName()) && position.distance(animal.getPosition()) < 170 && !animal.isDead()) {
                animalsNearCursor.add(animal);
            }
        }
        
        if (animalsNearCursor.isEmpty()) {
            //System.out.println("Selling animal isn't possible! No animal near the cursor (from given type)!");
            return;
        }

        
        // A kattintáshoz legközelebbi adott típusú állat megkeresése:
        Animal nearestAnimal = animalsNearCursor.get(0);

        for (Animal otherAnimal : animalsNearCursor) {
            if (position.distance(nearestAnimal.getPosition()) > position.distance(otherAnimal.getPosition())) {
                nearestAnimal = otherAnimal;
            }
        }

        
        // Ha az állat nem beteg, akkor el lehet adni pénzért:
        if (!nearestAnimal.isSick()) {
            switch (type) {
                case "Lion" -> director.earnMoney(30);
                case "Hyena" -> director.earnMoney(20);
                case "Elephant" -> director.earnMoney(40);
                case "Giraffe" -> director.earnMoney(30);
                default -> throw new AssertionError("Removing - Unknown animal type: " + type);
            }

            removableAnimals.add(nearestAnimal);
        }
        else {
         //   System.out.println("Sick animal cant be sold");
        }
    }
    
    
    /**
     * A növények hozzáadásáért felelős.
     * @param type növény típusa
     * @param position növény pozíciója
     */
    public void addPlant(String type, Position position) {
         switch (type) {
            case "Acacia" -> newPlants.add(new Acacia(position, this));
            case "BaobabTree" -> newPlants.add(new BaobabTree(position, this));
            case "ElephantGrass" -> newPlants.add(new ElephantGrass(position, this));
            default -> throw new IllegalArgumentException("Adding - Unknown plant type: " + type);
        }
    }
    
    
    /**
     * A növények törléséért felelős eladás esetén.
     * @param position kattintás pozíciója
     * @param type növény típusa
     */
    public void removePlant(Position position, String type) {  
        // A kattintáshoz közeli adott típusú növények kigyűjtése:
        List<Plant> plantsNearCursor = new ArrayList<>();
        
        for (Plant plant : plants) {
            if (type.equals(plant.getClass().getSimpleName()) && position.distance(plant.getPosition()) < 170 && !plant.isBeingEaten()) {
                plantsNearCursor.add(plant);
            }
        }
        
        if (plantsNearCursor.isEmpty()) {
           // System.out.println("Selling plant isn't possible! No plant near the cursor (from given type)!");
            return;
        }

        
        // A kattintáshoz legközelebbi adott típusú növény megkeresése:
        Plant nearestPlant = plantsNearCursor.get(0);

        for (Plant otherPlant : plantsNearCursor) {
            if (position.distance(nearestPlant.getPosition()) > position.distance(otherPlant.getPosition())) {
                nearestPlant = otherPlant;
            }
        }

        
        // Növény eladása pénzért:
        switch (type) {
            case "Acacia" -> director.earnMoney(20);
            case "BaobabTree" -> director.earnMoney(20);
            case "ElephantGrass" -> director.earnMoney(5);
            default -> throw new IllegalArgumentException("Removing - Unknown plant type: " + type);
        }

        removablePlants.add(nearestPlant);
    }

    
    /**
     * A tavak hozzáadásáért felelős.
     * @param position tó pozíciója
     */
    public void addLake(Position position) {
        newLakes.add(new Lake(position, this));
    }
    
    
    /**
     * A tavak törléséért felelős eladás esetén.
     * @param position kattintás pozíciója
     */
    public void removeLake(Position position) {   
        // A kattintáshoz közeli tavak kigyűjtése:
        List<Lake> lakesNearCursor = new ArrayList<>();
        
        for (Lake lake : lakes) {
            if (position.distance(lake.getPosition()) < 170 && !lake.isBeingDrunk()) {
                lakesNearCursor.add(lake);
            }
        }
        
        if (lakesNearCursor.isEmpty()) {
           // System.out.println("Selling lake isn't possible! No lake near the cursor!");
            return;
        }
        
        
        // A kattintáshoz legközelebbi tó megkeresése:
        Lake nearestLake = lakesNearCursor.get(0);

        for (Lake otherLake : lakesNearCursor) {
            if (position.distance(nearestLake.getPosition()) > position.distance(otherLake.getPosition())) {
                nearestLake = otherLake;
            }
        }

        
        // Tó eladása pénzért:
        removableLakes.add(nearestLake);
        director.earnMoney(50);
}
    
    
    /**
     * Az orvosok hozzáadásáért felelős.
     * @param position orvos pozíciója
     */
    public void addVet(Position position) {
        newVets.add(new Vet(position, this, getTimeValue()));
    }
    
    
    /**
     * Az orvosok törléséért felelős.
     * @param vet törölni kívánt orvos
     */
    public void removeVet(Vet vet) {
        removableVets.add(vet);
    }
    
    
    /**
     * A vadőrök hozzáadásáért felelős.
     * @param position vadőr pozíciója
     */
    public void addRanger(Position position) {
        newRangers.add(new Ranger(position, this, getTimeValue()));
    }
    
    
    /**
     * A vadőrök törléséért felelős.
     * @param ranger törölni kívánt vadőr
     */
    public void removeRanger(Ranger ranger) {
        removableRangers.add(ranger);
    }
    
    
    /**
     * Az orvvadászok hozzáadásáért felelős.
     * @param position orvvadász pozíciója
     */
    public void addPoacher(Position position) {
        newPoachers.add(new Poacher(position, this));
    }
    
    
    /**
     * Az orvvadászok törléséért felelős.
     * @param poacher törölni kívánt orvvadász
     */
    public void removePoacher(Poacher poacher) {
        removablePoachers.add(poacher);
    }
    
    /**
     * Az orvvadászok lehelyezéséért felelős (az állatok mennyiségétől függően).
     */
    private void spawnPoachers() {
        if (poachers.size() < animals.size() / 10) {
            Random rand = new Random();
            int spawnChance = rand.nextInt(1000);
            
            switch (gameEngine.getGameLevel()) {
                case EASY -> {
                    if (spawnChance < 20) {
                        Position pos = getUniquePosition();
                        addPoacher(pos);
                       // System.out.println("Adding poacher");
                    }
                }
                case MEDIUM -> {
                    if (spawnChance < 75) {
                        Position pos = getUniquePosition();
                        addPoacher(pos);
                       // System.out.println("Adding poacher");
                    }
                }
                case HARD -> {
                    if (spawnChance < 200) {
                        Position pos = getUniquePosition();
                        addPoacher(pos);
                    //    System.out.println("Adding poacher");
                    }
                }
            }
        }
    }   
    
    /**
     * A kezdő állatok lehelyezéséért felelős.
     */
    private void initAnimals() {
        List<Age> ages = Arrays.asList(Age.YOUNG, Age.MIDDLE_AGED);
        Random rand = new Random();
        
        // Fiatalon és középkorúan kezdenek az állatok:
        for (int i = 0; i < 2; i++) {
            int randomIndex = rand.nextInt(ages.size());
            addAnimal("Lion", ages.get(randomIndex), getUniqueInitPosition());
            addAnimal("Hyena", ages.get(randomIndex), getUniqueInitPosition());
        }
        
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(ages.size());
            addAnimal("Elephant", ages.get(randomIndex), getUniqueInitPosition());
            addAnimal("Giraffe", ages.get(randomIndex), getUniqueInitPosition());
        }
    }
    
    
    /**
     * A kezdő növények lehelyezéséért felelős.
     */
    private void initPlants() {        
        for(int i = 0; i < 2; i++){
            addPlant("Acacia", getUniqueInitPosition());
            addPlant("BaobabTree", getUniqueInitPosition());
            addPlant("ElephantGrass", getUniqueInitPosition());
        }
    }
    
    
    /**
     * A kezdő tavak lehelyezéséért felelős.
     */
    private void initLakes() {        
        for(int i = 0; i < 6; i++){
            addLake(getUniqueInitPosition());
        }
    }
    
    
    /**
     * Az egyező fajba tartozó állatok csoportosításáért felelős.
     */
    private void groupSameSpecies() {        
        for (Animal animal : animals) {
            for (Animal other : animals) {
                if (Animal.isGroupable(animal, other)) {
                    animal.setTargetPosition(other.getTargetPosition());
                }
            }
        }
    }
    
    
    /**
     * A vadőr ragadozóvadászatának aktiválásáért felelős.
     * @param position kattintás pozíciója
     * @param animalType állat típusa
     */
    public void startPredatorHunt(Position position, String animalType) {
        if (rangers.isEmpty()) {
          //  System.out.println("Hunting predators isn't possible! No rangers on the field!");
            return;
        }

        if (getCarnivores().isEmpty()) {
           // System.out.println("Hunting predators isn't possible! No carnivores on the field!");
            return;
        }
        
        
        // A kattintáshoz közeli adott típusú ragadozók kigyűjtése:
        List<Carnivore> carnivoresNearCursor = new ArrayList<>();
        
        for (Carnivore carnivore : getCarnivores()) {
            if (carnivore.getClass().getSimpleName().equals(animalType) && !carnivore.isDead() &&
                position.distance(carnivore.getPosition()) < 150) {
                carnivoresNearCursor.add(carnivore);
            }
        }
        
        if (carnivoresNearCursor.isEmpty()) {
        //    System.out.println("Hunting predator isn't possible! No predator near the cursor (from given type)!");
            return;
        }
        

        // A szabad (épp nem vadászó) és elfoglalt (épp vadászó) vadőrök kigyűjtése:
        List<Ranger> freeRangers = new ArrayList<>();
        List<Ranger> busyRangers = new ArrayList<>();

        for (Ranger ranger : rangers) {
            if (ranger.getTargetPredator() == null) {
                freeRangers.add(ranger);

            }
            else {
                busyRangers.add(ranger);
            }
        }
        
        if (freeRangers.isEmpty()) {
           // System.out.println("Hunting predators isn't possible! No free rangers on the field!");
            return;
        }
        
        
        // A kattintáshoz legközelebbi adott típusú ragadozó megkeresése:
        Carnivore nearestCarnivore = carnivoresNearCursor.get(0);

        for (Carnivore otherCarnivore : carnivoresNearCursor) {
            if (position.distance(nearestCarnivore.getPosition()) > position.distance(otherCarnivore.getPosition())) {
                nearestCarnivore = otherCarnivore;
            }
        }
        

        // Az adott ragadozóhoz legközelebbi szabad vadőr megkeresése:
        Ranger nearestFreeRanger = freeRangers.get(0);

        for (Ranger otherFreeRanger : freeRangers) {
            if (nearestFreeRanger.getPosition().distance(nearestCarnivore.getPosition()) > otherFreeRanger.getPosition().distance(nearestCarnivore.getPosition())) {
                nearestFreeRanger = otherFreeRanger;
            }
        }
            
        
        // Elfoglalt vadőrök prédáinak ellenőrzése (két vadőr ne vadászhasson ugyanarra):
        for (Ranger busyRanger : busyRangers) {
            if (busyRanger.getTargetPredator() == nearestCarnivore) {
            //    System.out.println("Hunting predator isn't possible! Another ranger is hunting it!");
                return;
            }
        }
        
        
        // Ragadozó vadászatának indítása:
        nearestFreeRanger.setTargetPredator(nearestCarnivore);
        nearestCarnivore.setMarked();
      //  System.out.println("Ranger hunting: " + animalType);
    }
    
    
    /**
     * Az állatok bechippeléséért felelős.
     * @param position kattintás pozíciója
     * @param animalType állat típusa
     */
    public void chipAnimal(Position position, String animalType) {
        
        // A kattintáshoz közeli adott típusú állatok kigyűjtése:
        List<Animal> animalsNearCursor = new ArrayList<>();
        
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equals(animalType) && position.distance(animal.getPosition()) < 170 &&
                !animal.isDead() && !animal.isChipped()) {
                animalsNearCursor.add(animal);
            }
        }
        
        if (animalsNearCursor.isEmpty()) {
          //  System.out.println("Chipping animal isn't possible! No chippable animals near the cursor (from given type)!");
            return;
        }
        
        
        // A kattintáshoz legközelebbi adott típusú állat megkeresése:
        Animal nearestAnimal = animalsNearCursor.get(0);

        for (Animal otherAnimal : animalsNearCursor) {
            if (position.distance(nearestAnimal.getPosition()) > position.distance(otherAnimal.getPosition())) {
                nearestAnimal = otherAnimal;
            }
        }

        
        // Chip megvásárlása:
        nearestAnimal.buyChip();
        director.spendMoney(10);
    }
    
    
    // Kirajzolások:
    @Override 
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        this.g2d = (Graphics2D) graphics;

        drawBackground();
        drawLakes(g2d);
        drawRoute(g2d);
        drawAnimals(g2d);
        drawVets(g2d);
        drawRangers(g2d);
        drawPoachers(g2d);
        drawPlants(g2d);
        drawProgressBars(g2d);
        drawJeep(g2d);
        drawMonitoringDevice(g2d);


        BufferedImage darknessLayer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gDark = darknessLayer.createGraphics();

        float alpha = Math.max(0.0f, Math.min(1.0f, 1.0f - brightness));
        gDark.setComposite(AlphaComposite.SrcOver.derive(alpha));
        gDark.setColor(Color.BLACK);
        gDark.fillRect(0, 0, getWidth(), getHeight());

        gDark.setComposite(AlphaComposite.Clear);

        for (Animal animal : animals) {
            if (animal.isChipped()){
                int radius = 100;
                int circleX = animal.getPosition().getX();
                int circleY = animal.getPosition().getY();

                gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
            }
        }

        for (Lake lake : lakes){
            int radius = 200;
            int circleX = lake.getPosition().getX();
            int circleY = lake.getPosition().getY();

            gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
        }
         for(Camera camera : cameraCollection){
            int radius = camera.getRadiusOfVisibility();
            int circleX = camera.getPosition().getX();
            int circleY = camera.getPosition().getY();
            gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
        }
        
        for(Drone drone : droneCollection){
            if(drone.getIsFlying()){
                int radius = drone.getRadiusOfVisibility();
                int circleX = drone.getPosition().getX();
                int circleY = drone.getPosition().getY();
                gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
            }
        }
         for(Airship airship : airshipCollection){
            if(airship.getIsFlying()){
                int radius = airship.getRadiusOfVisibility();
                int circleX = airship.getPosition().getX();
                int circleY = airship.getPosition().getY();
                gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
            }
         }
        for (Plant plant : plants){
            int radius = 100;
            int circleX = plant.getPosition().getX();
            int circleY = plant.getPosition().getY();

            gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
        }
       
        for (Ranger ranger : rangers){
            int radius = 150;
            int circleX = ranger.getPosition().getX();
            int circleY = ranger.getPosition().getY();
            gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
        }
        
        routes = this.getRoutes();
        for(Route route : routes){
            for(Position point : route.getPoints()){
                int radius = 100;
                int circleX = point.getX();
                int circleY = point.getY();

                gDark.fillOval(circleX - radius / 2, circleY - radius / 2, radius, radius);
            }        
        }
        
        
        gDark.dispose();

        g2d.drawImage(darknessLayer, 0, 0, null);
    }

       
    /**
     * A háttér kirajzolásáért felelős.
     */
    private void drawBackground() {
        this.setBackground(new Color(202,149,14));

    }
  
        
    /**
     * Az állatok kirajzolásáért felelős.
     * A halott állatok előbb rajzolódnak ki, mint az élők.
     */
    private void drawAnimals(Graphics2D g2d) {
        // Halott állatok kirajzolása:
        for (Animal animal : animals) {
            if (animal.isDead()) {
                drawSingleAnimal(g2d, animal);
            }
        }
    
        // Élő állatok kirajzolása:
        for (Animal animal : animals) {
            if (!animal.isDead()) {
                drawSingleAnimal(g2d, animal);
                
                // Státuszikonok kirajzolása (csak élő állatoknál):
                if (!animal.isDrinking() && !animal.isEating()) {
                    drawStatusIcons(g2d, animal);
                }
            }
        }
    }


    /**
     * Egyetlen állat kirajzolásáért felelős (iránytől függően tükrözve).
     * A beteg állatok zöldes árnyalatúak, a chippeltek mögött van egy sárga telített kör,
     * az orvvadász által üldözöttek körül pedig egy vörös telítetlen kör.
     */
    private void drawSingleAnimal(Graphics2D g2d, Animal animal) {
        int x = animal.getPosition().getX();
        int y = animal.getPosition().getY();
        int imageSize = animal.getImageSize();
    
        if (animal.getSpeedX() < 0 || animal.getSpeedX() == 0 && animal.getPrevSpeedX() < 0) {
            Graphics2D g2dMirrored = (Graphics2D) g2d.create();
    
            g2dMirrored.translate(x, y);
            g2dMirrored.scale(-1, 1);
            g2dMirrored.drawImage(animal.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
    
            if (animal.isSick()) {
                BufferedImage greenOverlay = createOverlay(animal.getImage(), imageSize, imageSize, new Color(100, 200, 74, 255));
                g2dMirrored.drawImage(greenOverlay, -imageSize / 2, -imageSize / 2, null);
            }
    
            g2dMirrored.dispose();
        }
        else {
            g2d.drawImage(animal.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
    
            if (animal.isSick()) {
                BufferedImage greenOverlay = createOverlay(animal.getImage(), imageSize, imageSize, new Color(100, 200, 74, 255));
                g2d.drawImage(greenOverlay, x - imageSize / 2, y - imageSize / 2, null);
            }
        }
    
        // Chippelt állat jelölése:
        if (animal.isChipped()) {
            g2d.setColor(new Color(255, 255, 0, 40));
            g2d.fillOval(x - imageSize / 2, y - imageSize / 2, imageSize, imageSize);
        }
        
        // Vadőr által üldözött célállat jelölése:
        if (animal instanceof Carnivore carnivore) {
            if (carnivore.isMarked()) {
                g2d.setColor(new Color(255, 0, 0, 60));
                Stroke originalStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(4));
                g2d.drawOval(x - imageSize / 2, y - imageSize / 2, imageSize, imageSize);
                g2d.setStroke(originalStroke);
            }
        }
    }


    /**
     * A képek átszínezéséért felelős. Ez az állapotjelzéshez kell (állat - betegség, növény - ehetetlenség).
     */
    private BufferedImage createOverlay(Image originalImage, int width, int height, Color color) {
        BufferedImage overlay = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = overlay.createGraphics();
    
        g.drawImage(originalImage, 0, 0, width, height, null);
    
        g.setComposite(AlphaComposite.SrcAtop.derive(0.4f));
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.dispose();
        
        return overlay;
    }
    
    
    /**
     * Az állatok feletti státusz ikonok (szomjúság, éhség, fáradtság, alvás) kirajzolásáért felelős.
     */
    private void drawStatusIcons(Graphics2D g2d, Animal animal) {
        int x = animal.getPosition().getX();
        int y = animal.getPosition().getY();
        int imageSize = animal.getImageSize();        
        
        if (!animal.isResting()) {
            g2d.drawImage(animal.getStatusIcon(), x - 10, y - imageSize / 2 - 20, 20, 20, null);
        }
        else {
            g2d.drawImage(animal.getStatusIcon(), x - 10, y - imageSize / 2 - 30, 20, 20, null);
        }
    }
    
    
    /**
     * Az ivás, evés és pihenés folyamatának jelzéséért (töltési sávokkal) felelős.
     */
    private void drawProgressBars(Graphics2D g2d) {
        for (Animal animal : animals) {
            if (!animal.isDead()) {
                int x = animal.getPosition().getX();
                int y = animal.getPosition().getY();
                int imageSize = animal.getImageSize();
        
                if (animal.isDrinking() || animal.isEating() || animal.isResting()) {
                    int barWidth = imageSize;
                    int barHeight = 5;
                    int barX = x - barWidth / 2;
                    int barY = y - imageSize / 2 - 10;

                    g2d.setColor(new Color(150, 150, 150));
                    g2d.fillRect(barX, barY, barWidth, barHeight);

                    // Progress sávok:
                    if (animal.isDrinking()) {
                        g2d.setColor(new Color(0, 0, 255));
                       
                    }
                    else if (animal.isEating()) {
                        if (animal instanceof Herbivore) {
                            g2d.setColor(new Color(66, 140, 120));
                        }
                        else {
                            g2d.setColor(new Color(123, 2, 26));
                        }
                    }
                    else if (animal.isResting()) {
                        g2d.setColor(new Color(255, 255, 0));
                    }
                    
                    int progressWidth = (int) (barWidth * animal.getActivityProgress());
                    g2d.fillRect(barX, barY, progressWidth, barHeight);
                }
            }
        }
    }
       
    
    /**
     * A növények kirajzolásáért felelős.
     * Az ehetetlen növények szürkésen jelennek meg.
     */
    private void drawPlants(Graphics2D g2d) {
        for (Plant plant : plants) {
            int x = plant.getPosition().getX();
            int y = plant.getPosition().getY();
            int imageSize = plant.getImageSize();
            
            g2d.drawImage(plant.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);

            if(!plant.isEatable()){
                BufferedImage greyOverlay = createOverlay(plant.getImage(), imageSize, imageSize, new Color(176, 176, 176, 255));
                g2d.drawImage(greyOverlay, -imageSize / 2, -imageSize / 2, null);
            }
        }
    }
    
    
    /**
     * A tavak kirajzolásáért felelős.
     */
    private void drawLakes(Graphics2D g2d) {
        for (Lake lake : lakes) {
            int x = lake.getPosition().getX();
            int y = lake.getPosition().getY();
            int imageWidth = lake.getImageWidth();
            int imageHeight = lake.getImageHeight();
            
            g2d.drawImage(lake.getImage(), x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight, null);
        }
    }
    

    /**
     * A dzsippek kirajzolásáért felelős (iránytől függően tükrözve).
     */
    private void drawJeep(Graphics2D g2d){
     
        g2d.setColor(new Color	(112, 82, 21));
      
        for (Jeep jeep : jeepCollection){
            if (jeep.getUnderWay()) {
                double angle=Math.toDegrees(Math.atan2(jeep.getDirection().getY(), jeep.getDirection().getX()));
                angle=(360-angle)%360;
              //  System.out.println("angle: "+angle);
                if(!jeep.getOrientation()) {
                   if (angle<60)
                        g2d.drawImage(jeepIconB, jeep.getPosition().getX() - 15 + jeep.getDirection().getX(), 
                                  jeep.getPosition().getY() - 15 + jeep.getDirection().getY(), 30, 30, null);
                   else
                       g2d.drawImage(jeepIconB0, jeep.getPosition().getX() - 15 + jeep.getDirection().getX(), 
                                  jeep.getPosition().getY() - 15 + jeep.getDirection().getY(), 30, 30, null);
                  
                }
                else {
                    if (angle>180 && angle<220)
                        g2d.drawImage(jeepIconF0, jeep.getPosition().getX() - 15 + jeep.getDirection().getX(), 
                                  jeep.getPosition().getY() - 15 + jeep.getDirection().getY(), 30, 30, null);
                    else
                        g2d.drawImage(jeepIconF, jeep.getPosition().getX() - 15 + jeep.getDirection().getX(), 
                                  jeep.getPosition().getY() - 15 + jeep.getDirection().getY(), 30, 30, null);
                        
                  
                }  
           }

        }
    }
    
    
    /**
     * Az orvosok kirajzolásáért felelős (iránytől függően tükrözve).
     */
    private void drawVets(Graphics2D g2d) {
        for (Vet vet : vets) {
            int x = vet.getPosition().getX();
            int y = vet.getPosition().getY();
            int imageSize = vet.getImageSize();
            
            if (vet.getSpeedX() < 0) {
                Graphics2D g2dMirrored = (Graphics2D) g2d.create();
                
                g2dMirrored.translate(x, y);
                g2dMirrored.scale(-1, 1);
                
                g2dMirrored.drawImage(vet.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                g2dMirrored.dispose();
            }
            else {
                g2d.drawImage(vet.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
            }
        }
    }
    
    
    /**
     * A vadőrök kirajzolásáért felelős (iránytől függően tükrözve).
     */
    private void drawRangers(Graphics2D g2d) {
         for (Ranger ranger : rangers) {
            int x = ranger.getPosition().getX();
            int y = ranger.getPosition().getY();
            int imageSize = ranger.getImageSize();
            
            if (ranger.getSpeedX() < 0) {
                Graphics2D g2dMirrored = (Graphics2D) g2d.create();
                
                g2dMirrored.translate(x, y);
                g2dMirrored.scale(-1, 1);
                
                g2dMirrored.drawImage(ranger.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                g2dMirrored.dispose();
            }
            else {
                g2d.drawImage(ranger.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
            }
        }
    }
    
    
    /**
     * Az orvvadászok kirajzolásáért felelős (iránytől függően tükrözve).
     */
    private void drawPoachers(Graphics2D g2d) {
        for (Poacher poacher : poachers) {
            // vadorok
            for (Ranger ranger : rangers) {
                if (ranger.getPosition().distance(poacher.getPosition()) < 250) {
                    int x = poacher.getPosition().getX();
                    int y = poacher.getPosition().getY();
                    int imageSize = poacher.getImageSize();

                    if (poacher.getSpeedX() < 0) {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(x, y);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(poacher.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                        g2dMirrored.dispose();
                    }
                    else {
                        g2d.drawImage(poacher.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
                    }
                }
            }

            // dronok
            for(Drone drone : droneCollection) {
                if (drone.getPosition().distance(poacher.getPosition()) < drone.getRadiusOfVisibility()) {
                    int x = poacher.getPosition().getX();
                    int y = poacher.getPosition().getY();
                    int imageSize = poacher.getImageSize();

                    if (poacher.getSpeedX() < 0) {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(x, y);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(poacher.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                        g2dMirrored.dispose();
                    }
                    else {
                        g2d.drawImage(poacher.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
                    }
                }
            }
            
            // kamerak
            for(Camera camera : cameraCollection) {
                if (camera.getPosition().distance(poacher.getPosition()) < camera.getRadiusOfVisibility()) {
                    int x = poacher.getPosition().getX();
                    int y = poacher.getPosition().getY();
                    int imageSize = poacher.getImageSize();

                    if (poacher.getSpeedX() < 0) {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(x, y);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(poacher.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                        g2dMirrored.dispose();
                    }
                    else {
                        g2d.drawImage(poacher.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
                    }
                }
            }
            
            // leghajok
            for(Airship airship : airshipCollection) {
                if (airship.getPosition().distance(poacher.getPosition()) < airship.getRadiusOfVisibility()) {
                    int x = poacher.getPosition().getX();
                    int y = poacher.getPosition().getY();
                    int imageSize = poacher.getImageSize();

                    if (poacher.getSpeedX() < 0) {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(x, y);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(poacher.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                        g2dMirrored.dispose();
                    }
                    else {
                        g2d.drawImage(poacher.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
                    }
                }
            }
            
            // jeep
            for(Jeep jeep : jeepCollection) {
                if (jeep.getPosition()!=null && jeep.getPosition().distance(poacher.getPosition()) < 250) {
                    int x = poacher.getPosition().getX();
                    int y = poacher.getPosition().getY();
                    int imageSize = poacher.getImageSize();

                    if (poacher.getSpeedX() < 0) {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(x, y);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(poacher.getImage(), -imageSize / 2, -imageSize / 2, imageSize, imageSize, null);
                        g2dMirrored.dispose();
                    }
                    else {
                        g2d.drawImage(poacher.getImage(), x - imageSize / 2, y - imageSize / 2, imageSize, imageSize, null);
                    }
                }
            }
        }  
    }
    
    // Megfigyelő rendszerek kirajozálása.
    public void drawMonitoringDevice(Graphics2D g2d){
        for(Station station:stationCollection){
            g2d.drawImage(stationIcon, station.getPosition().getX()-station.getImageWidth()/2,
                    station.getPosition().getY()-station.getImageHeight()/2, station.getImageWidth(), station.getImageHeight(), null);

        }
            
        for (Camera camera:cameraCollection){
            g2d.drawImage(cameraIcon, camera.getPosition().getX()-20,camera.getPosition().getY()-20, 40, 40, null);          
        }
         g2d.setColor(new Color(13, 254, 2)); // A gomb szine
         g2d.setStroke(new BasicStroke(2));
        for(Station station:stationCollection){
            if (station.getMarked()){
              // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
               g2d.drawOval(station.getPosition().getX()-30, station.getPosition().getY()-30, 100, 100);
               break;
            }
        }
        
        for(Drone drone:droneCollection){
            if(drone.getIsSelected()){
               g2d.drawOval(drone.getPosition().getX()-drone.getSize()/2, drone.getPosition().getY()-drone.getSize()/2,
                drone.getSize(), drone.getSize());
               break;
            }
        }
        for(Airship airship:airshipCollection){
            if(airship.getIsSelected()){
               g2d.drawOval(airship.getPosition().getX()-airship.getSize()/2, airship.getPosition().getY()-airship.getSize()/2,
                   airship.getSize(), airship.getSize());
                   break;
            }
        }
        
        if(buyingDeviceCenterCircle!=null){
            g2d.setColor(Color.BLUE); // A gomb szine
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            g2d.fillOval(buyingDeviceCenterCircle.getX(), buyingDeviceCenterCircle.getY(), 5, 5);
        }
        if(buyingDevicePointCircle!=null){
            g2d.setColor(Color.BLUE); // A gomb szine
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            g2d.fillOval(buyingDevicePointCircle.getX(), buyingDevicePointCircle.getY(), 10, 10);
            int r=(int)buyingDeviceCenterCircle.distance(buyingDevicePointCircle);
            g2d.drawOval(buyingDeviceCenterCircle.getX()-r,buyingDeviceCenterCircle.getY()-r,2*r,2*r);
        }
        
        
         for (Drone drone:droneCollection){
             if (drone.getIsFlying())
                g2d.drawImage(droneIcon, drone.getPosition().getX()-25,drone.getPosition().getY()-25, 
                            drone.getSize(), drone.getSize(), null);          
        }
         for (Airship airship:airshipCollection){
             if(airship.getIsFlying())
                g2d.drawImage(airshipIcon, airship.getPosition().getX()-30,airship.getPosition().getY()-30, 
                        airship.getSize(), airship.getSize(), null);          
        }
    
    }
    
    // Útvonal kirajzolása
    private void drawRoute(Graphics2D g2d){

            //aktuális utvonal kirajzolasa
            g2d.setColor(new Color (112, 82, 21));
            g2d.fillRect(0,0,100,100);
            g2d.fillRect(WINDOW_WIDTH-100,WINDOW_HEIGHT-80,100,80);
            g2d.setColor(Color.green);
            g2d.fillRect(0,0,8,25); 
            g2d.setColor(Color.red);
            g2d.fillRect(WINDOW_WIDTH-8, WINDOW_HEIGHT-25,8,25); 

            //Az utvonal kirajzolasa
            g2d.setColor(new Color	(112, 82, 21));

           for (Route route : this.routes){

               for (int i = 1; i < route.getPoints().size(); i++) {
                   Position p1 = route.getPoints().get(i - 1);
                   Position p2 = route.getPoints().get(i);
                   g2d.setStroke(new BasicStroke(50));
                   g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY()); // Vonalként összeköti a pontokat
               }
           }
            //az ideglesen utvonal kirajzolasa
            g2d.setColor(new Color(244,104,24));
           //az ideglenes utvonal kirajzolasa
           for (int i = 1; i < this.routeSection.size(); i++) {
               Position p1 = routeSection.get(i - 1);
               Position p2 = routeSection.get(i);
                   g2d.setStroke(new BasicStroke(40));
                   g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY()); // Vonalként összeköti a pontokat
           }

               // az utvonalveget jelző kör.
            if (this.actionBuyRoute){
               g2d.setColor(Color.YELLOW); // A gomb szine
               g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
               g2d.fillOval(this.pathPosition.getX()-25, this.pathPosition.getY()-25, 50, 50);
            }
            if(this.actionRemoveRoute){
               for (Route route :routes){
                   if (route.getSelected()){
                      g2d.setColor(Color.RED);
                      ArrayList<Position> pos=route.getPoints();
                       for (int i = 1; i < pos.size(); i++) {
                          Position p1 = pos.get(i - 1);
                          Position p2 = pos.get(i);
                              g2d.setStroke(new BasicStroke(25));
                              g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY()); // Vonalként összeköti a pontokat
                      }
                       break;
                   }
               }

           }
           if (this.actionBuyRoute){
                 g2d.setColor(Color.RED);
                 g2d.setStroke(new BasicStroke(2)); 
                for (Plant plant: plants){  
                    g2d.drawOval(plant.getPosition().getX()-(plant.getImageSize()/4), plant.getPosition().getY(),
                            plant.getImageSize()/2, plant.getImageSize()/2);
                }
                for (Lake lake: lakes){  
                    g2d.drawOval(lake.getPosition().getX()-lake.getImageWidth()/2, lake.getPosition().getY()-lake.getImageHeight()/2-20,
                            lake.getImageWidth(), lake.getImageWidth());
                }
                
                for(Station station:stationCollection){
                    g2d.drawOval(station.getPosition().getX()-(station.getImageWidth()/2), 
                            station.getPosition().getY()-station.getImageHeight()/4, station.getImageWidth()+20, station.getImageWidth()+20);
                    
                }

            }
       }


    
    public boolean onExitGate(Position pos){
        return pos.getX()>this.WINDOW_WIDTH-100 &&pos.getY()>this.WINDOW_HEIGHT-80;
    }
    
    public void setPathPosition(Position pos){
        this.pathPosition=pos;
    }
    
    public void addRouteSection(Position pos){
        this.routeSection.add(pos);
    }
    
    public ArrayList<Position> getRouteSection(){
        return this.routeSection;
    }
    
    public void clearRouteSection(){
        this.routeSection.clear();
    }
    
    public List<Route> getRoutes(){
        return routes;
    }
    
    public void flyMobileDevice(){
        for(Drone drone:droneCollection){
            drone.fly();
        }
         for(Airship airship:airshipCollection){
            airship.fly();
        }
    
    }
    
    
    public Map<String, Object> onRoute(Position pos){
        Map<String, Object> result = new HashMap<>();
        for(Route route : routes){
            if((boolean)route.onRoute(pos).get("exist")){
                result.put("exist", true);
                result.put("route", route);
                result.put("position", (Position) route.onRoute(pos).get("position"));
                return result;
                }
            
        }
        result.put("exist", false);
        return result;
    }
    
    
    public boolean onIcon(Position position) {
        return abs(this.pathPosition.getX() - position.getX()) < 20 && 
               abs(this.pathPosition.getY() - position.getY()) < 20;
    
    }
    
    
    public void buyRoute() {
        deleted = false;
        Map<String, Object> actData; 
        actData = onRoute(getRouteSection().get(0));
        Position pos = (Position)actData.get("position");
        Route route = (Route)actData.get("route");
        
        if ((route.getPoints().size() - route.getPoints().indexOf(pos)) > 10) {
            Route rr = new Route(new ArrayList<>(route.getPoints().subList(0, route.getPoints().indexOf(pos) + 1)),this);
            rr.addRoute(routeSection);
            routes.add(rr);
            
            if (onExitGate(rr.getEndRoute())){
                rr.setComplete();
                setPathPosition();
            }
        }
        else {
            route.getPoints().subList(route.getPoints().size() -
                                      abs(route.getPoints().size() -
                                      route.getPoints().indexOf(pos)),
                                      route.getPoints().size()).clear();
            route.addRoute(routeSection);
            
            if (onExitGate(route.getEndRoute())) {
                route.setComplete();
                setPathPosition();
            }
        }
    }
     
     
    public void setPathPosition() {
        for (Route r :routes) {
            if (!r.getComplete()) {
                pathPosition = r.getEndRoute();
                return;
            }
        }
        
        pathPosition = new Position(40, 50);
    }
   
    
    public void removeRoute(Route route){
        routes.remove(route);
        
        if (routes.isEmpty()) {
            routes.add(new Route(new Position(20, 30),this));
        }
        
        setPathPosition();
        deleted = true;
    }
    
    public void buyJeep() {
        jeepCollection.add(new Jeep());
        director.spendMoney(Jeep.PRICE);
    }

    
    public void jeepTour() {
        Random random = new Random();
        List<Route> routeComplete = routes.stream().filter(rr -> rr.getComplete() && rr.getFree()).collect(Collectors.toList());
        List<Jeep> jeepFree = jeepCollection.stream().filter(jeep -> !(jeep.getUnderWay())).collect(Collectors.toList());
        
        int multiplier = (SafariGameEngine.GAME_SPEED == 3) ? 5 : 1;
        
        while(!routeComplete.isEmpty() && !jeepFree.isEmpty() && gameEngine.getTouristNumber() >= multiplier * 4){
            int randIndex = random.nextInt(routeComplete.size());
            jeepFree.get(0).move(routeComplete.get(randIndex));
            routeComplete.remove(randIndex);
            jeepFree.remove(0);
            gameEngine.changeTouristNumber((-4) * multiplier);
            director.spendMoney(-400 * multiplier);
        }
    } 
    
    
    public boolean routeComplete(){
        List<Route> routeComplete = routes.stream().filter(rr->(rr.getComplete())).collect(Collectors.toList());
        return !routeComplete.isEmpty();
    }
    
    
    private boolean inCircle(Position pos, int r, Position other){
        return pos.distance(other) < (r + 20);
    }
    
    
    public boolean isSubject(Position pos) {
        
        for (Lake lake: this.lakes) {
            if (inCircle(new Position(lake.getPosition().getX(), lake.getPosition().getY() + 10),
                                      lake.getImageWidth() / 2 + 10, pos)) {
                return true;
            }
        } 
        
        for(Plant plant: this.plants){
            if (inCircle(new Position(plant.getPosition().getX(), plant.getPosition().getY() - plant.getImageSize() / 4 + plant.getImageSize() / 2),
                                      plant.getImageSize()/4 +10, pos)) {
                return true;
            }
        }
        
        return false;
    }
    public Station thereIsStation(Position pos){
        for (Station station:this.stationCollection){
            if (inCircle(new Position(station.getPosition().getX()+station.getImageWidth()/2,
                    station.getPosition().getY()+station.getImageHeight()/2),
            station.getImageWidth()/2+30, pos)){
                return station;
        }
        }    
        return null;
    }
    

    public MobileDevice thereIsMobileDevice(Position pos){
        for (Drone drone:this.droneCollection){
            if ((inCircle(new Position(drone.getPosition().getX()+drone.getSize()/2,
                    drone.getPosition().getY()+drone.getSize()/2),
            drone.getSize()/2+10, pos)) && drone.getIsFlying()){
                return drone;
        }
        }
        for (Airship airship:this.airshipCollection){
            if ((inCircle(new Position(airship.getPosition().getX()+airship.getSize()/2,
                    airship.getPosition().getY()+airship.getSize()/2),
            airship.getSize()/2+10, pos))&& airship.getIsFlying()){
                return airship;
        }
        }    
        return null;
    }
  
    
    public MessageBox getEndMessageBox(){
        return endGameMessage;
    }

    public void addMessBox(MessageBox messageBox){
        messageBoxes.add(messageBox);
    }
        
    public GameSpeed getGameSpeed() {
        return gameEngine.getGameSpeed();
    }
    
    public int getTimeValue() {
        return gameEngine.getTime().getTimeInHours();
    }
    
    public SafariGameEngine getGameEngine() {
        return gameEngine;
    }
    
    public Director getDirector() {
        return director;
    }

    public void buyCamera(Position pos){
        director.spendMoney(Camera.Price);
        this.cameraCollection.add(new Camera(pos));
    }
     public void buyStation(Position pos){
        director.spendMoney(Station.PRICE);
        this.stationCollection.add(new Station(pos));
    }
    
    public void buyDrone(Position center, Station station, Position start){
        director.spendMoney(Drone.PRICE);
        this.droneCollection.add(new Drone(center,station,start));
    }
    public void buyAirship(Position center, Station station, Position start){
        director.spendMoney(Airship.PRICE);
        this.airshipCollection.add(new Airship(center,station,start));
    }
     
    public boolean haveStation(){
        return !this.stationCollection.isEmpty();
    }

    public int getJeepCount(){
        return jeepCollection.size();
    }
    
    public Position getPathPosition(){
        return pathPosition;
    }

    public int getRouteCost(){
        return ROUTE_COST;
    }
    
    public boolean getDeleted(){
        return deleted;
    } 
    public boolean isThereAnimal(Position pos){
        for (Animal animal:this.animals){
            if (inCircle(new Position(animal.getPosition().getX()+animal.getImageSize()/2,
                    animal.getPosition().getY()+animal.getImageSize()/2),
            animal.getImageSize()/2+10, new Position (pos.getX()+20, pos.getY()+20))){
                return true;
            }
        }
        return false;
    
    }
    public boolean isTherePeople(Position pos){
    for (Vet vet:this.vets){
            if (inCircle(new Position(vet.getPosition().getX()+vet.getImageSize()/2,
                    vet.getPosition().getY()+vet.getImageSize()/2),
            vet.getImageSize()/2+10, new Position (pos.getX()+20, pos.getY()+20))){
                return true;
            }
        }
     for (Poacher poacher:this.poachers){
            if (inCircle(new Position(poacher.getPosition().getX()+poacher.getImageSize()/2,
                    poacher.getPosition().getY()+poacher.getImageSize()/2),
            poacher.getImageSize()/2+10, new Position (pos.getX()+20, pos.getY()+20))){
                return true;
            }
        }
        return false;
    }
}
