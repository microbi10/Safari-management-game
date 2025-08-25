package com.emft.safari.model.animals;

import static com.emft.safari.model.animals.Animal.MIDDLE_AGE_THRESHOLD;
import static com.emft.safari.model.animals.Animal.OLD_AGE_THRESHOLD;
import com.emft.safari.model.buildables.Lake;
import com.emft.safari.model.plants.Acacia;
import com.emft.safari.model.plants.BaobabTree;
import com.emft.safari.model.plants.ElephantGrass;
import com.emft.safari.model.plants.Plant;
import com.emft.safari.model.utilities.Age;
import com.emft.safari.model.utilities.GameSpeed;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.utilities.State;
import com.emft.safari.view.ContentPanel;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GiraffeTest {
    
    List<Herbivore> herbivores;
    List<Carnivore> carnivores;
    List<Plant> plants;

    @BeforeEach
    public void setup() {
        herbivores = new ArrayList<>();
        carnivores = new ArrayList<>();
        plants = new ArrayList<>();
    }
        
    private Animal createAnimal(String animalType, Age age, GameSpeed gameSpeed, Position pos) {        
        ContentPanel contentPanel = EasyMock.createMock(ContentPanel.class);
        
        // Alapértelmezett pozíció beállítása:
        if (pos == null) {            
            switch (animalType) {
                case "Giraffe" -> {
                    pos = new Position(100, 100);
                }
                case "Elephant" -> {
                    pos = new Position(120, 120);
                }
                case "Lion" -> {
                    pos = new Position(100, 120);
                }
                case "Hyena" -> {
                    pos = new Position(120, 100);
                }
                default -> throw new IllegalArgumentException("Unknown animal type: " + animalType);
            }
        }
        
        
        // ContentPanel további viselkedésének megadása:
        EasyMock.expect(contentPanel.getGameEngine()).andReturn(null).anyTimes();
        EasyMock.expect(contentPanel.getUniquePosition()).andReturn(pos).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(gameSpeed).anyTimes();  
        
        
        // Tavak hozzáadása:        
        
        List<Lake> lakes = getLakes();
        EasyMock.expect(contentPanel.getLakes()).andReturn(lakes).anyTimes();
        
        
        // Növények hozzáadása:
        
        ContentPanel dummyPanelPlant1 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant1.getUniquePosition()).andReturn(new Position(120, 120)).anyTimes();
        EasyMock.replay(dummyPanelPlant1);
        Acacia acacia1 = new Acacia(new Position(120, 120), dummyPanelPlant1);
        plants.add(acacia1);   
        
        ContentPanel dummyPanelPlant2 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant2.getUniquePosition()).andReturn(new Position(180, 180)).anyTimes();
        EasyMock.replay(dummyPanelPlant2);
        Acacia acacia2 = new Acacia(new Position(180, 180), dummyPanelPlant2);
        plants.add(acacia2);
        
        ContentPanel dummyPanelPlant3 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant3.getUniquePosition()).andReturn(new Position(300, 300)).anyTimes();
        EasyMock.replay(dummyPanelPlant3);
        BaobabTree baobabTree1 = new BaobabTree(new Position(300, 300), dummyPanelPlant3);
        plants.add(baobabTree1);
        
        ContentPanel dummyPanelPlant4 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant4.getUniquePosition()).andReturn(new Position(360, 360)).anyTimes();
        EasyMock.replay(dummyPanelPlant4);
        BaobabTree baobabTree2 = new BaobabTree(new Position(360, 360), dummyPanelPlant4);
        plants.add(baobabTree2);
        
        ContentPanel dummyPanelPlant5 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant5.getUniquePosition()).andReturn(new Position(700, 700)).anyTimes();
        EasyMock.replay(dummyPanelPlant5);
        ElephantGrass elephantGrass1 = new ElephantGrass(new Position(700, 700), dummyPanelPlant5);
        plants.add(elephantGrass1);
        
        ContentPanel dummyPanelPlant6 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelPlant6.getUniquePosition()).andReturn(new Position(800, 800)).anyTimes();
        EasyMock.replay(dummyPanelPlant6);
        ElephantGrass elephantGrass2 = new ElephantGrass(new Position(800, 800), dummyPanelPlant6);
        plants.add(elephantGrass2);
        
        EasyMock.expect(contentPanel.getPlants()).andReturn(plants).anyTimes();
        
        // Környező ragadozók hozzáadása:                
        
        // Éhes oroszlán:
        ContentPanel dummyPanelCarnivore1 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore1.getUniquePosition()).andReturn(new Position(120, 120)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore1);
        Lion lion1 = new Lion(new Position(120, 120), Age.MIDDLE_AGED, dummyPanelCarnivore1);
        lion1.setHunger(1200);
        carnivores.add(lion1);
        
        // Éhes hiéna:
        ContentPanel dummyPanelCarnivore2 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore2.getUniquePosition()).andReturn(new Position(230, 230)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore2);
        Hyena hyena1 = new Hyena(new Position(230, 230), Age.MIDDLE_AGED, dummyPanelCarnivore2);
        hyena1.setHunger(1200);
        carnivores.add(hyena1);
        
        // Éhes és szomjas oroszlán:
        ContentPanel dummyPanelCarnivore3 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore3.getUniquePosition()).andReturn(new Position(200, 200)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore3);
        Lion lion2 = new Lion(new Position(200, 200), Age.MIDDLE_AGED, dummyPanelCarnivore3);
        lion2.setHunger(1200);
        lion2.setThirst(1000);
        carnivores.add(lion2);
        
        // Éhes és szomjas hiéna:
        ContentPanel dummyPanelCarnivore4 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore4.getUniquePosition()).andReturn(new Position(300, 300)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore4);
        Hyena hyena2 = new Hyena(new Position(300, 300), Age.MIDDLE_AGED, dummyPanelCarnivore4);
        hyena2.setHunger(1200);
        hyena2.setThirst(1000);
        carnivores.add(hyena2);
        
        // Étkező oroszlán:
        ContentPanel dummyPanelCarnivore5 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore5.getUniquePosition()).andReturn(new Position(400, 400)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore5);
        Lion lion3 = new Lion(new Position(400, 400), Age.MIDDLE_AGED, dummyPanelCarnivore5);
        lion3.setEating();
        carnivores.add(lion3);
        
        // Étkező hiéna:
        ContentPanel dummyPanelCarnivore6 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore6.getUniquePosition()).andReturn(new Position(600, 600)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore6);
        Hyena hyena3 = new Hyena(new Position(600, 600), Age.MIDDLE_AGED, dummyPanelCarnivore6);
        hyena3.setEating();
        carnivores.add(hyena3);        
        
        // Sima oroszlán:
        ContentPanel dummyPanelCarnivore7 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore7.getUniquePosition()).andReturn(new Position(800, 800)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore7);
        Lion lion4 = new Lion(new Position(800, 800), Age.MIDDLE_AGED, dummyPanelCarnivore7);
        carnivores.add(lion4);
        
        // Sima hiéna:
        ContentPanel dummyPanelCarnivore8 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore8.getUniquePosition()).andReturn(new Position(900, 900)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore8);
        Hyena hyena4 = new Hyena(new Position(900, 900), Age.MIDDLE_AGED, dummyPanelCarnivore8);
        carnivores.add(hyena4);        
        
        EasyMock.expect(contentPanel.getCarnivores()).andReturn(carnivores).anyTimes();        

        EasyMock.replay(contentPanel);

            
        // Állat létrehozása:
        switch (animalType) {
            case "Elephant" -> {
                return new Elephant(pos, age, contentPanel);
            }
            case "Giraffe" -> {
                return new Giraffe(pos, age, contentPanel);
            }
            case "Lion" -> {
                return new Lion(pos, age, contentPanel);
            }
            case "Hyena" -> {
                return new Hyena(pos, age, contentPanel);
            }
            default -> throw new IllegalArgumentException("Unknown animal type: " + animalType);
        }
    }
    
    
    private Lake createLake(Position pos) {
        ContentPanel contentPanel = EasyMock.createMock(ContentPanel.class);
        return new Lake(pos, contentPanel);
    }
    
    
    private List<Lake> getLakes() {
        Lake lake1 = createLake(new Position(150, 150));
        Lake lake2 = createLake(new Position(180, 180));
        Lake lake3 = createLake(new Position(300, 300));
        Lake lake4 = createLake(new Position(500, 500));
        
        List<Lake> lakes = new ArrayList<>();
        lakes.add(lake1);
        lakes.add(lake2);
        lakes.add(lake3);
        lakes.add(lake4);
        
        return lakes;
    }    
    
    
    @Test
    public void testIncreaseHungerByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);

            giraffe.increaseHunger();
            int newHunger = giraffe.getHunger();
            
            assertTrue(newHunger >= 1 && newHunger <= 3, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    @Test
    public void testIncreaseHungerByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.DAY, null);

            giraffe.increaseHunger();
            int newHunger = giraffe.getHunger();

            assertTrue(newHunger >= 4 && newHunger <= 6, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseHungerByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.WEEK, null);

            giraffe.increaseHunger();
            int newHunger = giraffe.getHunger();

            assertTrue(newHunger >= 7 && newHunger <= 9, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);

            giraffe.increaseThirst();
            int newThirst = giraffe.getThirst();

            assertTrue(newThirst >= 0 && newThirst <= 2, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.DAY, null);

            giraffe.increaseThirst();
            int newThirst = giraffe.getThirst();

            assertTrue(newThirst >= 2 && newThirst <= 4, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.WEEK, null);

            giraffe.increaseThirst();
            int newThirst = giraffe.getThirst();

            assertTrue(newThirst >= 5 && newThirst <= 7, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);

            giraffe.increaseAge();
            int newAge = giraffe.getActualLifeTime();

            assertTrue(newAge >= 0 && newAge <= 5, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.DAY, null);

            giraffe.increaseAge();
            int newAge = giraffe.getActualLifeTime();

            assertTrue(newAge >= 2 && newAge <= 7, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.WEEK, null);

            giraffe.increaseAge();
            int newAge = giraffe.getActualLifeTime();

            assertTrue(newAge >= 4 && newAge <= 9, "Age didn't increase accordingly. New value: " + newAge);
        }
    }

    
    @Test
    public void testIncreaseSicknessByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);

            giraffe.increaseSickness();
            int newSickness = giraffe.getSickness();

            assertTrue(newSickness >= 0 && newSickness <= 2, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
    
    
    @Test
    public void testIncreaseSicknessByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.DAY, null);

            giraffe.increaseSickness();
            int newSickness = giraffe.getSickness();

            assertTrue(newSickness >= 2 && newSickness <= 4, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
 
    
    @Test
    public void testIncreaseSicknessByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.WEEK, null);

            giraffe.increaseSickness();
            int newSickness = giraffe.getSickness();

            assertTrue(newSickness >= 4 && newSickness <= 6, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }

    
    @Test
    public void testDeath() {                
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.die();
        
        assertTrue(giraffe.isDead(), "Didn't die succesfully.");
        assertEquals(0, giraffe.getSpeedX(), "speedX didn't reset when dying.");
        assertEquals(0, giraffe.getSpeedY(), "speedY didn't reset when dying.");
    }
    
    
    @Test
    public void testGetHealed() {                
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.isSick = true;
        giraffe.getHealed();
        
        assertTrue(!giraffe.isSick() && giraffe.getSickness() == 0, "Didn't heal succesfully.");
    }


    @Test
    public void testImageSizeByAge() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(giraffe.getImageSize() == 40, "Image size for YOUNG should be 40.");

        giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(giraffe.getImageSize() == 50, "Image size for MIDDLE_AGED should be 50.");

        giraffe = createAnimal("Giraffe", Age.OLD, GameSpeed.HOUR, null);
        assertTrue(giraffe.getImageSize() == 60, "Image size for OLD should be 60.");
    }


    @Test
    public void testSetActualLifeTimeByAge() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.setActualLifeTimeByAge();
        assertEquals(0, giraffe.getActualLifeTime(), "Actual life time should be 0 for YOUNG age.");

        giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setActualLifeTimeByAge();
        assertEquals(Giraffe.MIDDLE_AGE_THRESHOLD, giraffe.getActualLifeTime(), "Actual life time should match MIDDLE_AGE_THRESHOLD for MIDDLE_AGED.");

        giraffe = createAnimal("Giraffe", Age.OLD, GameSpeed.HOUR, null);
        giraffe.setActualLifeTimeByAge();
        assertEquals(Giraffe.OLD_AGE_THRESHOLD, giraffe.getActualLifeTime(), "Actual life time should match OLD_AGE_THRESHOLD for OLD age.");
    }


    @Test
    public void testCanMate() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(giraffe.canMate(), "Giraffe should be able to mate.");
    }


    @Test
    public void testCannotMateDueToAge() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(!giraffe.canMate(), "Young giraffe should not be able to mate.");
    }

    @Test
    public void testCannotMateDueToHunger() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setHunger(1200);
        assertTrue(!giraffe.canMate(), "Hungry giraffe should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToThirst() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setThirst(1000);
        assertTrue(!giraffe.canMate(), "Thirsty giraffe should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToTiredness() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setTired();
        assertTrue(!giraffe.canMate(), "Tired giraffe should not be able to mate.");
    }

    
    @Test
    public void testCannotMateDueToNotMoving() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setEating();
        assertTrue(!giraffe.canMate(), "Eating giraffe should not be able to mate.");
        giraffe.setDrinking();
        assertTrue(!giraffe.canMate(), "Drinking giraffe should not be able to mate.");
        giraffe.setResting();
        assertTrue(!giraffe.canMate(), "Resting giraffe should not be able to mate.");
    }
    

    @Test
    public void testCannotMateDueToDeath() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.die();
        assertTrue(!giraffe.canMate(), "Dead giraffe should not be able to mate.");
    }


    @Test
    public void testIsGroupable() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));

        assertTrue(Animal.isGroupable(giraffe1, giraffe2), "Two giraffes should be groupable.");
    }


    @Test
    public void testNotIsGroupableDueToDifferentSpecies() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        assertFalse(Animal.isGroupable(giraffe, elephant), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(giraffe, lion), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(giraffe, hyena), "Different species shouldn't be groupable.");
    }

    
    @Test
    public void testNotIsGroupableDueToDistance() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(300, 300));

        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Giraffes too far apart shouldn't be groupable.");
    }
    
    
    @Test
    public void testNotIsGroupableDueToHunger() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        giraffe1.setHunger(1000);
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "A hungry giraffe shouldn't be groupable with a well-fed giraffe.");     

        giraffe2.setHunger(1000);
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Hungry giraffes shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToThirst() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        giraffe1.setThirst(700);
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "A thirsty giraffe shouldn't be groupable with a well-hydrated giraffe.");     

        giraffe2.setThirst(700);
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Thirsty giraffes shouldn't be groupable.");        
    }

    
    @Test
    public void testNotIsGroupableDueToTiredness() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        giraffe1.setTired();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "A tired giraffe shouldn't be groupable with an energetic giraffe.");     

        giraffe2.setTired();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Tired giraffes shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToNotMoving() {
        Animal giraffe1 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal giraffe2 = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        giraffe1.setEating();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "An eating giraffe shouldn't be groupable with a well-fed giraffe.");     

        giraffe2.setEating();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Tired giraffes shouldn't be groupable."); 
        

        giraffe1.setDrinking();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "A drinking giraffe shouldn't be groupable with a well-hydrated giraffe.");

        giraffe2.setDrinking();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Drinking giraffes shouldn't be groupable.");


        giraffe1.setResting();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "A resting giraffe shouldn't be groupable with a moving giraffe.");

        giraffe2.setResting();
        assertFalse(Animal.isGroupable(giraffe1, giraffe2), "Resting giraffes shouldn't be groupable.");
    }
    
    
    @Test
    public void testSetRandomSpeed() {
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        List<Integer> possibleValues = new ArrayList<>(List.of(-2, -1, 1, 2));
        
        for (int i = 0; i < 100; i++) {
            giraffe.setRandomSpeed();
            int speedX = (int)giraffe.getSpeedX();
            int speedY = (int)giraffe.getSpeedY();

            assertTrue(possibleValues.contains(speedX), "speedX should be one of the allowed values.");
            assertTrue(possibleValues.contains(speedY), "speedY should be one of the allowed values.");
        }
    }
    
    
    @Test
    public void testMoveTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        giraffe.moveTowards(targetPos);
        
        assertEquals(3.0, giraffe.getSpeedX(), 0.001);
        assertEquals(0.0, giraffe.getSpeedY(), 0.001);
        
        // Balra irány:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(80, 100);
        giraffe.moveTowards(targetPos);
        
        assertEquals(-3.0, giraffe.getSpeedX(), 0.001);
        assertEquals(0.0, giraffe.getSpeedY(), 0.001);
        
        // Felfelé irány:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(100, 120);
        giraffe.moveTowards(targetPos);
        
        assertEquals(0.0, giraffe.getSpeedX(), 0.001);
        assertEquals(3.0, giraffe.getSpeedY(), 0.001);

        // Lefelé irány:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(100, 80);
        giraffe.moveTowards(targetPos);
        
        assertEquals(0.0, giraffe.getSpeedX(), 0.001);
        assertEquals(-3.0, giraffe.getSpeedY(), 0.001);

        // Átlós mozgás:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(120, 120);
        giraffe.moveTowards(targetPos);
        double expected = 3 / Math.sqrt(2);
        assertEquals(expected, giraffe.getSpeedX(), 0.001);
        assertEquals(expected, giraffe.getSpeedY(), 0.001);
        
        // Rövid távolság:
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(101, 100);
        giraffe.moveTowards(targetPos);
        assertTrue(Math.abs(giraffe.getSpeedX()) < 3.0);
        assertEquals(0.0, giraffe.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        giraffe.setSpeedX(2);
        giraffe.setSpeedY(2);
        targetPos = new Position(100, 100);
        giraffe.moveTowards(targetPos);
        assertEquals(2.0, giraffe.getSpeedX(), 0.001);
        assertEquals(2.0, giraffe.getSpeedY(), 0.001);
    }


    @Test
    public void testHandleWallCollision() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);

        // Bal falhoz ütközés:
        giraffe.setPosition(new Position(1, 200));
        giraffe.setSpeedX(-3);
        giraffe.setSpeedY(0);
        giraffe.handleWallCollision();
        assertEquals(25, giraffe.getPosition().getX());
        assertEquals(3, giraffe.getSpeedX(), 0.001);

        // Jobb falhoz ütközés:
        giraffe.setPosition(new Position(ContentPanel.WINDOW_WIDTH, 200));
        giraffe.setSpeedX(3);
        giraffe.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_WIDTH - 25, giraffe.getPosition().getX());
        assertEquals(-3, giraffe.getSpeedX(), 0.001);

        // Felső falhoz ütközés:
        giraffe.setPosition(new Position(300, 0));
        giraffe.setSpeedX(0);
        giraffe.setSpeedY(3);
        giraffe.handleWallCollision();
        assertEquals(25, giraffe.getPosition().getY());
        assertEquals(-3, giraffe.getSpeedY(), 0.001);

        // Alsó falhoz ütközés:
        giraffe.setPosition(new Position(300, ContentPanel.WINDOW_HEIGHT));
        giraffe.setSpeedX(0);
        giraffe.setSpeedY(-3);
        giraffe.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_HEIGHT - 25, giraffe.getPosition().getY());
        assertEquals(3, giraffe.getSpeedY(), 0.001);
    }
    
    
    @Test
    public void testCheckNearbyWaterSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Tavak pozíciói:
        // (150, 150) <-- lakePositions.get(0)
        // (180, 180) <-- lakePositions.get(1)
        // (300, 300) <-- lakePositions.get(2)
        // (500, 500) <-- lakePositions.get(3)
        
        
        // Minden tó pozíciójának kinyerése:
        List<Position> lakePositions = new ArrayList<>();
        
        for (Lake lake : getLakes()) {
            lakePositions.add(lake.getPosition());
        }
        
        
        // Közeli tavak mentése:
        giraffe.checkNearbyWaterSources();
        List<Position> sources = giraffe.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");
        
        // Duplikációk elkerülése:
        giraffe.checkNearbyWaterSources();
        sources = giraffe.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");

        // Mentett tavak listájának tartalomellenőrzése:
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertFalse(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));

        // Újabb tó hozzáadása:
        giraffe.setPosition(new Position(200, 200));
        giraffe.checkNearbyWaterSources();
        sources = giraffe.getWaterSources();
        
        assertEquals(3, sources.size());
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertTrue(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));
    }
    
    
    @Test
    public void testFindNearestWaterSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Tavak pozíciói:
        // (150, 150) <-- lakePositions.get(0)
        // (180, 180) <-- lakePositions.get(1)
        // (300, 300) <-- lakePositions.get(2)
        // (500, 500) <-- lakePositions.get(3)
        
        
        // Minden tó pozíciójának kinyerése:
        List<Position> lakePositions = new ArrayList<>();
        
        for (Lake lake : getLakes()) {
            lakePositions.add(lake.getPosition());
        }
        
        giraffe.setWaterSources(lakePositions);
        
        // Legközelebbi tó kiválasztása:
        
        giraffe.findNearestWaterSource();
        Position expectedTarget = lakePositions.get(0); // (150, 150)
        assertEquals(expectedTarget, giraffe.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(giraffe.isDrinking(), "Giraffe shouldn't be drinking while being far from the target lake.");
        
        giraffe.setPosition(new Position(210, 210));
        giraffe.findNearestWaterSource();
        expectedTarget = lakePositions.get(1); // (180, 180)
        assertEquals(expectedTarget, giraffe.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(giraffe.isDrinking(), "Giraffe shouldn't be drinking while being far from the target lake.");
        
        giraffe.setPosition(new Position(250, 250));
        giraffe.findNearestWaterSource();
        expectedTarget = lakePositions.get(2); // (300, 300)
        assertEquals(expectedTarget, giraffe.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(giraffe.isDrinking(), "Giraffe shouldn't be drinking while being far from the target lake.");
        
        // Ivás megkezdése:
        giraffe.setPosition(new Position(130, 130));
        giraffe.findNearestWaterSource();
        assertTrue(giraffe.isDrinking(), "Giraffe should be drinking while being near the target lake.");
    }
    
    
    @Test
    public void testIsBeingEaten() {
        // Ragadozók pozíciói:
        // (400, 400) <-- Étkező oroszlán
        // (600, 600) <-- Étkező hiéna
        
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        giraffe.setPosition(new Position(380, 380));
        assertFalse(giraffe.isBeingEaten(), "Alive giraffe near eating carnivore shouldn't be considered as being eaten.");
        
        giraffe.setPosition(new Position(0, 0));
        assertFalse(giraffe.isBeingEaten(), "Alive giraffe far from eating carnivore shouldn't be considered as being eaten.");

        giraffe.setPosition(new Position(380, 380));
        giraffe.die();
        assertTrue(giraffe.isBeingEaten(), "Dead giraffe near eating carnivore should be considered as being eaten.");
        
        giraffe.setPosition(new Position(0, 0));
        assertFalse(giraffe.isBeingEaten(), "Dead giraffe far from eating carnivore shouldn't be considered as being eaten.");
    }

    
    @Test
    public void testChangeByTimeAgeChanging() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.changeByTime();
        assertEquals(Age.YOUNG, giraffe.getAge());
        assertEquals(40, giraffe.getImageSize());

        giraffe.setActualLifeTime(MIDDLE_AGE_THRESHOLD);
        giraffe.changeByTime();
        assertEquals(Age.MIDDLE_AGED, giraffe.getAge());
        assertEquals(50, giraffe.getImageSize());
        
        giraffe.setActualLifeTime(OLD_AGE_THRESHOLD);
        giraffe.changeByTime();
        assertEquals(Age.OLD, giraffe.getAge());
        assertEquals(60, giraffe.getImageSize());       
        assertEquals(0, giraffe.getChildCount());
    }
    
    
    @Test
    public void testChangeByTimeDeath() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.setActualLifeTime(giraffe.getMaxLifeTime());
        giraffe.changeByTime();
        assertTrue(giraffe.isDead());
    }
    
    
    @Test
    public void testStartActivityEating() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.EATING);
        assertEquals(0, giraffe.getHunger(), "Hunger didn't reset while eating.");
        assertEquals(0, giraffe.getSpeedX(), 0.001, "speedX didn't reset while eating.");
        assertEquals(0, giraffe.getSpeedY(), 0.001, "speedY didn't reset while eating.");
        assertTrue(giraffe.isEating(), "Didn't start eating.");
    }
    
    
    @Test
    public void testStartActivityDrinking() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.DRINKING);
        assertEquals(0, giraffe.getThirst(), "Thirst didn't reset while drinking.");
        assertEquals(0, giraffe.getSpeedX(), 0.001, "speedX didn't reset while drinking.");
        assertEquals(0, giraffe.getSpeedY(), 0.001, "speedY didn't reset while drinking.");
        assertTrue(giraffe.isDrinking(), "Didn't start drinking.");
    }
    
    
    @Test
    public void testStartActivityResting() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.RESTING);
        assertEquals(0, giraffe.getSpeedX(), 0.001, "speedX didn't reset while resting.");
        assertEquals(0, giraffe.getSpeedY(), 0.001, "speedY didn't reset while resting.");
        assertTrue(giraffe.isResting(), "Didn't start resting.");
    }
    
    
    @Test
    public void testStopActivityEating() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.EATING);
        giraffe.stopActivity(State.EATING);
        
        assertTrue(giraffe.isMoving(), "Didn't start moving after stopping eating activity.");
        assertTrue(giraffe.getSpeedX() != 0, "speedX stayed 0 even after stopping eating activity.");
        assertTrue(giraffe.getSpeedY() != 0, "speedY stayed 0 even after stopping eating activity.");
    }
    
    
    @Test
    public void testStopActivityDrinking() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.DRINKING);
        giraffe.stopActivity(State.DRINKING);
        
        assertTrue(giraffe.isMoving(), "Didn't start moving after stopping drinking activity.");
        assertTrue(giraffe.getSpeedX() != 0, "speedX stayed 0 even after stopping drinking activity.");
        assertTrue(giraffe.getSpeedY() != 0, "speedY stayed 0 even after stopping drinking activity.");
    }
    
    
    @Test
    public void testStopActivityResting() {
        Animal giraffe = createAnimal("Giraffe", Age.YOUNG, GameSpeed.HOUR, null);
        giraffe.startActivity(State.RESTING);
        giraffe.stopActivity(State.RESTING);
        
        assertTrue(giraffe.isMoving(), "Didn't start moving after stopping resting activity.");
        assertTrue(giraffe.getSpeedX() != 0, "speedX stayed 0 even after stopping resting activity.");
        assertTrue(giraffe.getSpeedY() != 0, "speedY stayed 0 even after stopping resting activity.");
    }
    
    
    @Test
    public void testCheckNearbyFoodSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Giraffe giraffe = (Giraffe)createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növények pozíciói:
        // (120, 120) <-- plants.get(0)
        // (180, 180) <-- plants.get(1)
        // (300, 300) <-- plants.get(2)
        // (360, 360) <-- plants.get(3)
        // (700, 700) <-- plants.get(4)
        // (800, 800) <-- plants.get(5)               
        
        // Közeli növények mentése:
        giraffe.checkNearbyFoodSources();
        List<Plant> foodSources = giraffe.getPlants();
        assertEquals(2, foodSources.size());
        
        // Duplikációk elkerülése:
        giraffe.checkNearbyFoodSources();
        foodSources = giraffe.getPlants();
        assertEquals(2, foodSources.size());

        // Mentett növények listájának tartalomellenőrzése:
        assertTrue(foodSources.contains(plants.get(0)));
        assertTrue(foodSources.contains(plants.get(1)));
        assertFalse(foodSources.contains(plants.get(2)));
        assertFalse(foodSources.contains(plants.get(3)));
        assertFalse(foodSources.contains(plants.get(4)));
        assertFalse(foodSources.contains(plants.get(5)));

        // Újabb növény hozzáadása:
        giraffe.setPosition(new Position(320, 320));
        giraffe.checkNearbyFoodSources();
        foodSources = giraffe.getPlants();
        
        assertEquals(4, foodSources.size());
        assertTrue(foodSources.contains(plants.get(0)));
        assertTrue(foodSources.contains(plants.get(1)));
        assertTrue(foodSources.contains(plants.get(2)));
        assertTrue(foodSources.contains(plants.get(3)));
        assertFalse(foodSources.contains(plants.get(4)));
        assertFalse(foodSources.contains(plants.get(5)));      
    }
    
    
    @Test
    public void testFindNearestFoodSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Giraffe giraffe = (Giraffe)createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növények pozíciói:
        // (120, 120) <-- plants.get(0)
        // (180, 180) <-- plants.get(1)
        // (300, 300) <-- plants.get(2)
        // (360, 360) <-- plants.get(3)
        // (700, 700) <-- plants.get(4)
        // (800, 800) <-- plants.get(5)
                
        // Legközelebbi növény kiválasztása:
        giraffe.setPlants(plants);
        giraffe.setPosition(new Position(80, 80));
        giraffe.findNearestFoodSource();
        Plant expectedTarget = plants.get(0); // (120, 120)
        assertEquals(expectedTarget.getPosition(), giraffe.getTargetPosition(), "The nearest plant wasn't set as target position.");
        assertFalse(giraffe.isEating(), "Giraffe shouldn't be eating while being far from the target plant.");
        
        giraffe.setPosition(new Position(340, 330));
        giraffe.setPlants(plants);
        giraffe.findNearestFoodSource();
        expectedTarget = plants.get(3); // (360, 360)
        assertEquals(expectedTarget.getPosition(), giraffe.getTargetPosition(), "The nearest plant wasn't set as target position.");
        assertFalse(giraffe.isEating(), "Giraffe shouldn't be eating while being far from the target plant.");
        
        // Evés megkezdése:
        giraffe.setPosition(new Position(130, 130));
        giraffe.setPlants(plants);
        giraffe.findNearestFoodSource();
        assertTrue(giraffe.isEating(), "Giraffe should be eating while being near the target plant.");
}
    
    
    @Test
    public void testCheckNearbyPredators() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Giraffe giraffe = (Giraffe)createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Ragadozók pozíciói:
        // (120, 120) <-- carnivores.get(0) <-- Éhes oroszlán
        // (230, 230) <-- carnivores.get(1) <-- Éhes hiéna
        // (200, 200) <-- carnivores.get(2) <-- Éhes és szomjas oroszlán
        // (300, 300) <-- carnivores.get(3) <-- Éhes és szomjas hiéna
        // (400, 400) <-- carnivores.get(4) <-- Étkező oroszlán
        // (600, 600) <-- carnivores.get(5) <-- Étkező hiéna
        // (800, 800) <-- carnivores.get(6) <-- Sima oroszlán
        // (900, 900) <-- carnivores.get(7) <-- Sima hiéna
        
        // Közeli ragadozók mentése:
        giraffe.setPosition(new Position(150, 150));
        giraffe.checkNearbyPredators();
        List<Carnivore> predators = giraffe.getPredators();
        assertEquals(2, predators.size());
        
        // Duplikációk elkerülése / elemek törlése:
        giraffe.checkNearbyFoodSources();
        predators = giraffe.getPredators();
        assertEquals(2, predators.size());

        // Mentett ragadozók listájának tartalomellenőrzése:
        assertTrue(predators.contains(carnivores.get(0)), "Giraffe should consider a hungry lion as a threat.");
        assertTrue(predators.contains(carnivores.get(1)), "Giraffe should consider a hungry hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(2)), "Giraffe shouldn't consider a hungry and thirsty lion as a threat.");
        assertFalse(predators.contains(carnivores.get(3)), "Giraffe shouldn't consider a hungry and thirsty hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(4)), "Giraffe shouldn't consider a well-fed lion as a threat.");
        assertFalse(predators.contains(carnivores.get(5)), "Giraffe shouldn't consider a well-fed hyena as a threat.");

        giraffe.setPosition(new Position(320, 320));
        giraffe.checkNearbyPredators();
        predators = giraffe.getPredators();
                
        assertFalse(predators.contains(carnivores.get(0)), "Giraffe shouldn't consider a hungry lion as a threat if it's too far.");
        assertFalse(predators.contains(carnivores.get(1)), "Giraffe shouldn't consider a hungry hyena as a threat if it's too far.");
        assertFalse(predators.contains(carnivores.get(2)), "Giraffe shouldn't consider a hungry and thirsty lion as a threat.");
        assertFalse(predators.contains(carnivores.get(3)), "Giraffe shouldn't consider a hungry and thirsty hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(4)), "Giraffe shouldn't consider a well-fed lion as a threat.");
        assertFalse(predators.contains(carnivores.get(5)), "Giraffe shouldn't consider a well-fed hyena as a threat.");    
    }
    
    
    @Test
    public void testFleeFromNearestPredator() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Giraffe giraffe = (Giraffe)createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Ragadozók pozíciói:
        // (120, 120) <-- carnivores.get(0) <-- Éhes oroszlán
        // (230, 230) <-- carnivores.get(1) <-- Éhes hiéna
        // (200, 200) <-- carnivores.get(2) <-- Éhes és szomjas oroszlán
        // (300, 300) <-- carnivores.get(3) <-- Éhes és szomjas hiéna
        // (400, 400) <-- carnivores.get(4) <-- Étkező oroszlán
        // (600, 600) <-- carnivores.get(5) <-- Étkező hiéna
        // (800, 800) <-- carnivores.get(6) <-- Sima oroszlán
        // (900, 900) <-- carnivores.get(7) <-- Sima hiéna
        
        assertFalse(giraffe.isFleeing(), "Giraffe shouldn't be fleeing while being far from predators.");
        
        giraffe.setPredators(carnivores);
        giraffe.setPosition(new Position(80, 80));
        Carnivore expectedPredator = carnivores.get(0);
        assertEquals(expectedPredator, giraffe.findNearestPredator(), "The nearest predator wasn't found.");
        assertTrue(giraffe.isFleeing(), "Giraffe should be fleeing while being near a hungry predator.");
        
        giraffe.setPredators(carnivores);
        giraffe.setPosition(new Position(220, 220));
        expectedPredator = carnivores.get(1);
        assertEquals(expectedPredator, giraffe.findNearestPredator(), "The nearest predator wasn't found.");
        assertTrue(giraffe.isFleeing(), "Giraffe should be fleeing while being near a hungry predator.");
    }
}