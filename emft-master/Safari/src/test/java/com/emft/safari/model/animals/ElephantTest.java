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

public class ElephantTest {
    
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
                case "Elephant" -> {
                    pos = new Position(100, 100);
                }
                case "Giraffe" -> {
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
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);

            elephant.increaseHunger();
            int newHunger = elephant.getHunger();
            
            assertTrue(newHunger >= 1 && newHunger <= 3, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    @Test
    public void testIncreaseHungerByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.DAY, null);

            elephant.increaseHunger();
            int newHunger = elephant.getHunger();

            assertTrue(newHunger >= 4 && newHunger <= 6, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseHungerByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.WEEK, null);

            elephant.increaseHunger();
            int newHunger = elephant.getHunger();

            assertTrue(newHunger >= 7 && newHunger <= 9, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);

            elephant.increaseThirst();
            int newThirst = elephant.getThirst();

            assertTrue(newThirst >= 0 && newThirst <= 2, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.DAY, null);

            elephant.increaseThirst();
            int newThirst = elephant.getThirst();

            assertTrue(newThirst >= 2 && newThirst <= 4, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.WEEK, null);

            elephant.increaseThirst();
            int newThirst = elephant.getThirst();

            assertTrue(newThirst >= 5 && newThirst <= 7, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);

            elephant.increaseAge();
            int newAge = elephant.getActualLifeTime();

            assertTrue(newAge >= 0 && newAge <= 5, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.DAY, null);

            elephant.increaseAge();
            int newAge = elephant.getActualLifeTime();

            assertTrue(newAge >= 2 && newAge <= 7, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.WEEK, null);

            elephant.increaseAge();
            int newAge = elephant.getActualLifeTime();

            assertTrue(newAge >= 4 && newAge <= 9, "Age didn't increase accordingly. New value: " + newAge);
        }
    }

    
    @Test
    public void testIncreaseSicknessByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);

            elephant.increaseSickness();
            int newSickness = elephant.getSickness();

            assertTrue(newSickness >= 0 && newSickness <= 2, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
    
    
    @Test
    public void testIncreaseSicknessByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.DAY, null);

            elephant.increaseSickness();
            int newSickness = elephant.getSickness();

            assertTrue(newSickness >= 2 && newSickness <= 4, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
 
    
    @Test
    public void testIncreaseSicknessByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.WEEK, null);

            elephant.increaseSickness();
            int newSickness = elephant.getSickness();

            assertTrue(newSickness >= 4 && newSickness <= 6, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }

    
    @Test
    public void testDeath() {                
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.die();
        
        assertTrue(elephant.isDead(), "Didn't die succesfully.");
        assertEquals(0, elephant.getSpeedX(), "speedX didn't reset when dying.");
        assertEquals(0, elephant.getSpeedY(), "speedY didn't reset when dying.");
    }
    
    
    @Test
    public void testGetHealed() {                
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.isSick = true;
        elephant.getHealed();
        
        assertTrue(!elephant.isSick() && elephant.getSickness() == 0, "Didn't heal succesfully.");
    }


    @Test
    public void testImageSizeByAge() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(elephant.getImageSize() == 40, "Image size for YOUNG should be 40.");

        elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(elephant.getImageSize() == 50, "Image size for MIDDLE_AGED should be 50.");

        elephant = createAnimal("Elephant", Age.OLD, GameSpeed.HOUR, null);
        assertTrue(elephant.getImageSize() == 60, "Image size for OLD should be 60.");
    }


    @Test
    public void testSetActualLifeTimeByAge() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.setActualLifeTimeByAge();
        assertEquals(0, elephant.getActualLifeTime(), "Actual life time should be 0 for YOUNG age.");

        elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setActualLifeTimeByAge();
        assertEquals(Elephant.MIDDLE_AGE_THRESHOLD, elephant.getActualLifeTime(), "Actual life time should match MIDDLE_AGE_THRESHOLD for MIDDLE_AGED.");

        elephant = createAnimal("Elephant", Age.OLD, GameSpeed.HOUR, null);
        elephant.setActualLifeTimeByAge();
        assertEquals(Elephant.OLD_AGE_THRESHOLD, elephant.getActualLifeTime(), "Actual life time should match OLD_AGE_THRESHOLD for OLD age.");
    }


    @Test
    public void testCanMate() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(elephant.canMate(), "Elephant should be able to mate.");
    }


    @Test
    public void testCannotMateDueToAge() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(!elephant.canMate(), "Young elephant should not be able to mate.");
    }

    @Test
    public void testCannotMateDueToHunger() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setHunger(1200);
        assertTrue(!elephant.canMate(), "Hungry elephant should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToThirst() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setThirst(1000);
        assertTrue(!elephant.canMate(), "Thirsty elephant should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToTiredness() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setTired();
        assertTrue(!elephant.canMate(), "Tired elephant should not be able to mate.");
    }

    
    @Test
    public void testCannotMateDueToNotMoving() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setEating();
        assertTrue(!elephant.canMate(), "Eating elephant should not be able to mate.");
        elephant.setDrinking();
        assertTrue(!elephant.canMate(), "Drinking elephant should not be able to mate.");
        elephant.setResting();
        assertTrue(!elephant.canMate(), "Resting elephant should not be able to mate.");
    }
    

    @Test
    public void testCannotMateDueToDeath() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.die();
        assertTrue(!elephant.canMate(), "Dead elephant should not be able to mate.");
    }


    @Test
    public void testIsGroupable() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));

        assertTrue(Animal.isGroupable(elephant1, elephant2), "Two elephants should be groupable.");
    }


    @Test
    public void testNotIsGroupableDueToDifferentSpecies() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        assertFalse(Animal.isGroupable(elephant, giraffe), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(elephant, lion), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(elephant, hyena), "Different species shouldn't be groupable.");
    }

    
    @Test
    public void testNotIsGroupableDueToDistance() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(300, 300));

        assertFalse(Animal.isGroupable(elephant1, elephant2), "Elephants too far apart shouldn't be groupable.");
    }
    
    
    @Test
    public void testNotIsGroupableDueToHunger() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        elephant1.setHunger(1000);
        assertFalse(Animal.isGroupable(elephant1, elephant2), "A hungry elephant shouldn't be groupable with a well-fed elephant.");     

        elephant2.setHunger(1000);
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Hungry elephant shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToThirst() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        elephant1.setThirst(700);
        assertFalse(Animal.isGroupable(elephant1, elephant2), "A thirsty elephant shouldn't be groupable with a well-hydrated elephant.");     

        elephant2.setThirst(700);
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Thirsty elephants shouldn't be groupable.");        
    }

    
    @Test
    public void testNotIsGroupableDueToTiredness() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        elephant1.setTired();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "A tired elephant shouldn't be groupable with an energetic elephant.");     

        elephant2.setTired();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Tired elephants shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToNotMoving() {
        Animal elephant1 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal elephant2 = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        elephant1.setEating();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "An eating elephant shouldn't be groupable with a well-fed elephant.");     

        elephant2.setEating();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Tired elephants shouldn't be groupable."); 
        

        elephant1.setDrinking();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "A drinking elephant shouldn't be groupable with a well-hydrated elephant.");

        elephant2.setDrinking();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Drinking elephants shouldn't be groupable.");


        elephant1.setResting();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "A resting elephant shouldn't be groupable with a moving elephant.");

        elephant2.setResting();
        assertFalse(Animal.isGroupable(elephant1, elephant2), "Resting elephants shouldn't be groupable.");
    }
    
    
    @Test
    public void testSetRandomSpeed() {
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        List<Integer> possibleValues = new ArrayList<>(List.of(-2, -1, 1, 2));
        
        for (int i = 0; i < 100; i++) {
            elephant.setRandomSpeed();
            int speedX = (int)elephant.getSpeedX();
            int speedY = (int)elephant.getSpeedY();

            assertTrue(possibleValues.contains(speedX), "speedX should be one of the allowed values.");
            assertTrue(possibleValues.contains(speedY), "speedY should be one of the allowed values.");
        }
    }
    
    
    @Test
    public void testMoveTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        elephant.moveTowards(targetPos);
        
        assertEquals(3.0, elephant.getSpeedX(), 0.001);
        assertEquals(0.0, elephant.getSpeedY(), 0.001);
        
        // Balra irány:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(80, 100);
        elephant.moveTowards(targetPos);
        
        assertEquals(-3.0, elephant.getSpeedX(), 0.001);
        assertEquals(0.0, elephant.getSpeedY(), 0.001);
        
        // Felfelé irány:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(100, 120);
        elephant.moveTowards(targetPos);
        
        assertEquals(0.0, elephant.getSpeedX(), 0.001);
        assertEquals(3.0, elephant.getSpeedY(), 0.001);

        // Lefelé irány:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(100, 80);
        elephant.moveTowards(targetPos);
        
        assertEquals(0.0, elephant.getSpeedX(), 0.001);
        assertEquals(-3.0, elephant.getSpeedY(), 0.001);

        // Átlós mozgás:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(120, 120);
        elephant.moveTowards(targetPos);
        double expected = 3 / Math.sqrt(2);
        assertEquals(expected, elephant.getSpeedX(), 0.001);
        assertEquals(expected, elephant.getSpeedY(), 0.001);
        
        // Rövid távolság:
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(101, 100);
        elephant.moveTowards(targetPos);
        assertTrue(Math.abs(elephant.getSpeedX()) < 3.0);
        assertEquals(0.0, elephant.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        elephant.setSpeedX(2);
        elephant.setSpeedY(2);
        targetPos = new Position(100, 100);
        elephant.moveTowards(targetPos);
        assertEquals(2.0, elephant.getSpeedX(), 0.001);
        assertEquals(2.0, elephant.getSpeedY(), 0.001);
    }


    @Test
    public void testHandleWallCollision() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);

        // Bal falhoz ütközés:
        elephant.setPosition(new Position(1, 200));
        elephant.setSpeedX(-3);
        elephant.setSpeedY(0);
        elephant.handleWallCollision();
        assertEquals(25, elephant.getPosition().getX());
        assertEquals(3, elephant.getSpeedX(), 0.001);

        // Jobb falhoz ütközés:
        elephant.setPosition(new Position(ContentPanel.WINDOW_WIDTH, 200));
        elephant.setSpeedX(3);
        elephant.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_WIDTH - 25, elephant.getPosition().getX());
        assertEquals(-3, elephant.getSpeedX(), 0.001);

        // Felső falhoz ütközés:
        elephant.setPosition(new Position(300, 0));
        elephant.setSpeedX(0);
        elephant.setSpeedY(3);
        elephant.handleWallCollision();
        assertEquals(25, elephant.getPosition().getY());
        assertEquals(-3, elephant.getSpeedY(), 0.001);

        // Alsó falhoz ütközés:
        elephant.setPosition(new Position(300, ContentPanel.WINDOW_HEIGHT));
        elephant.setSpeedX(0);
        elephant.setSpeedY(-3);
        elephant.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_HEIGHT - 25, elephant.getPosition().getY());
        assertEquals(3, elephant.getSpeedY(), 0.001);
    }
    
    
    @Test
    public void testCheckNearbyWaterSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        elephant.checkNearbyWaterSources();
        List<Position> sources = elephant.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");
        
        // Duplikációk elkerülése:
        elephant.checkNearbyWaterSources();
        sources = elephant.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");

        // Mentett tavak listájának tartalomellenőrzése:
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertFalse(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));

        // Újabb tó hozzáadása:
        elephant.setPosition(new Position(200, 200));
        elephant.checkNearbyWaterSources();
        sources = elephant.getWaterSources();
        
        assertEquals(3, sources.size());
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertTrue(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));
    }
    
    
    @Test
    public void testFindNearestWaterSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        
        elephant.setWaterSources(lakePositions);
        
        // Legközelebbi tó kiválasztása:
        
        elephant.findNearestWaterSource();
        Position expectedTarget = lakePositions.get(0); // (150, 150)
        assertEquals(expectedTarget, elephant.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(elephant.isDrinking(), "Elephant shouldn't be drinking while being far from the target lake.");
        
        elephant.setPosition(new Position(210, 210));
        elephant.findNearestWaterSource();
        expectedTarget = lakePositions.get(1); // (180, 180)
        assertEquals(expectedTarget, elephant.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(elephant.isDrinking(), "Elephant shouldn't be drinking while being far from the target lake.");
        
        elephant.setPosition(new Position(250, 250));
        elephant.findNearestWaterSource();
        expectedTarget = lakePositions.get(2); // (300, 300)
        assertEquals(expectedTarget, elephant.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(elephant.isDrinking(), "Elephant shouldn't be drinking while being far from the target lake.");
        
        // Ivás megkezdése:
        elephant.setPosition(new Position(130, 130));
        elephant.findNearestWaterSource();
        assertTrue(elephant.isDrinking(), "Elephant should be drinking while being near the target lake.");
    }
    
    
    @Test
    public void testIsBeingEaten() {
        // Ragadozók pozíciói:
        // (400, 400) <-- Étkező oroszlán
        // (600, 600) <-- Étkező hiéna
        
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        elephant.setPosition(new Position(380, 380));
        assertFalse(elephant.isBeingEaten(), "Alive elephant near eating carnivore shouldn't be considered as being eaten.");
        
        elephant.setPosition(new Position(0, 0));
        assertFalse(elephant.isBeingEaten(), "Alive elephant far from eating carnivore shouldn't be considered as being eaten.");

        elephant.setPosition(new Position(380, 380));
        elephant.die();
        assertTrue(elephant.isBeingEaten(), "Dead elephant near eating carnivore should be considered as being eaten.");
        
        elephant.setPosition(new Position(0, 0));
        assertFalse(elephant.isBeingEaten(), "Dead elephant far from eating carnivore shouldn't be considered as being eaten.");
    }

    
    @Test
    public void testChangeByTimeAgeChanging() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.changeByTime();
        assertEquals(Age.YOUNG, elephant.getAge());
        assertEquals(40, elephant.getImageSize());

        elephant.setActualLifeTime(MIDDLE_AGE_THRESHOLD);
        elephant.changeByTime();
        assertEquals(Age.MIDDLE_AGED, elephant.getAge());
        assertEquals(50, elephant.getImageSize());
        
        elephant.setActualLifeTime(OLD_AGE_THRESHOLD);
        elephant.changeByTime();
        assertEquals(Age.OLD, elephant.getAge());
        assertEquals(60, elephant.getImageSize());       
        assertEquals(0, elephant.getChildCount());
    }
    
    
    @Test
    public void testChangeByTimeDeath() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.setActualLifeTime(elephant.getMaxLifeTime());
        elephant.changeByTime();
        assertTrue(elephant.isDead());
    }
    
    
    @Test
    public void testStartActivityEating() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.EATING);
        assertEquals(0, elephant.getHunger(), "Hunger didn't reset while eating.");
        assertEquals(0, elephant.getSpeedX(), 0.001, "speedX didn't reset while eating.");
        assertEquals(0, elephant.getSpeedY(), 0.001, "speedY didn't reset while eating.");
        assertTrue(elephant.isEating(), "Didn't start eating.");
    }
    
    
    @Test
    public void testStartActivityDrinking() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.DRINKING);
        assertEquals(0, elephant.getThirst(), "Thirst didn't reset while drinking.");
        assertEquals(0, elephant.getSpeedX(), 0.001, "speedX didn't reset while drinking.");
        assertEquals(0, elephant.getSpeedY(), 0.001, "speedY didn't reset while drinking.");
        assertTrue(elephant.isDrinking(), "Didn't start drinking.");
    }
    
    
    @Test
    public void testStartActivityResting() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.RESTING);
        assertEquals(0, elephant.getSpeedX(), 0.001, "speedX didn't reset while resting.");
        assertEquals(0, elephant.getSpeedY(), 0.001, "speedY didn't reset while resting.");
        assertTrue(elephant.isResting(), "Didn't start resting.");
    }
    
    
    @Test
    public void testStopActivityEating() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.EATING);
        elephant.stopActivity(State.EATING);
        
        assertTrue(elephant.isMoving(), "Didn't start moving after stopping eating activity.");
        assertTrue(elephant.getSpeedX() != 0, "speedX stayed 0 even after stopping eating activity.");
        assertTrue(elephant.getSpeedY() != 0, "speedY stayed 0 even after stopping eating activity.");
    }
    
    
    @Test
    public void testStopActivityDrinking() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.DRINKING);
        elephant.stopActivity(State.DRINKING);
        
        assertTrue(elephant.isMoving(), "Didn't start moving after stopping drinking activity.");
        assertTrue(elephant.getSpeedX() != 0, "speedX stayed 0 even after stopping drinking activity.");
        assertTrue(elephant.getSpeedY() != 0, "speedY stayed 0 even after stopping drinking activity.");
    }
    
    
    @Test
    public void testStopActivityResting() {
        Animal elephant = createAnimal("Elephant", Age.YOUNG, GameSpeed.HOUR, null);
        elephant.startActivity(State.RESTING);
        elephant.stopActivity(State.RESTING);
        
        assertTrue(elephant.isMoving(), "Didn't start moving after stopping resting activity.");
        assertTrue(elephant.getSpeedX() != 0, "speedX stayed 0 even after stopping resting activity.");
        assertTrue(elephant.getSpeedY() != 0, "speedY stayed 0 even after stopping resting activity.");
    }
    
    
    @Test
    public void testCheckNearbyFoodSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Elephant elephant = (Elephant)createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növények pozíciói:
        // (120, 120) <-- plants.get(0)
        // (180, 180) <-- plants.get(1)
        // (300, 300) <-- plants.get(2)
        // (360, 360) <-- plants.get(3)
        // (700, 700) <-- plants.get(4)
        // (800, 800) <-- plants.get(5)               
        
        // Közeli növények mentése:
        elephant.checkNearbyFoodSources();
        List<Plant> foodSources = elephant.getPlants();
        assertEquals(2, foodSources.size());
        
        // Duplikációk elkerülése:
        elephant.checkNearbyFoodSources();
        foodSources = elephant.getPlants();
        assertEquals(2, foodSources.size());

        // Mentett növények listájának tartalomellenőrzése:
        assertTrue(foodSources.contains(plants.get(0)));
        assertTrue(foodSources.contains(plants.get(1)));
        assertFalse(foodSources.contains(plants.get(2)));
        assertFalse(foodSources.contains(plants.get(3)));
        assertFalse(foodSources.contains(plants.get(4)));
        assertFalse(foodSources.contains(plants.get(5)));

        // Újabb növény hozzáadása:
        elephant.setPosition(new Position(320, 320));
        elephant.checkNearbyFoodSources();
        foodSources = elephant.getPlants();
        
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
        Elephant elephant = (Elephant)createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növények pozíciói:
        // (120, 120) <-- plants.get(0)
        // (180, 180) <-- plants.get(1)
        // (300, 300) <-- plants.get(2)
        // (360, 360) <-- plants.get(3)
        // (700, 700) <-- plants.get(4)
        // (800, 800) <-- plants.get(5)
                
        // Legközelebbi növény kiválasztása:
        elephant.setPlants(plants);
        elephant.setPosition(new Position(80, 80));
        elephant.findNearestFoodSource();
        Plant expectedTarget = plants.get(0); // (120, 120)
        assertEquals(expectedTarget.getPosition(), elephant.getTargetPosition(), "The nearest plant wasn't set as target position.");
        assertFalse(elephant.isEating(), "Elephant shouldn't be eating while being far from the target plant.");
        
        elephant.setPosition(new Position(340, 330));
        elephant.setPlants(plants);
        elephant.findNearestFoodSource();
        expectedTarget = plants.get(3); // (360, 360)
        assertEquals(expectedTarget.getPosition(), elephant.getTargetPosition(), "The nearest plant wasn't set as target position.");
        assertFalse(elephant.isEating(), "Elephant shouldn't be eating while being far from the target plant.");
        
        // Evés megkezdése:
        elephant.setPosition(new Position(130, 130));
        elephant.setPlants(plants);
        elephant.findNearestFoodSource();
        
        assertTrue(elephant.isEating(), "Elephant should be eating while being near the target plant.");
 }
    
    
    @Test
    public void testCheckNearbyPredators() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Elephant elephant = (Elephant)createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        elephant.setPosition(new Position(150, 150));
        elephant.checkNearbyPredators();
        List<Carnivore> predators = elephant.getPredators();
        assertEquals(2, predators.size());
        
        // Duplikációk elkerülése / elemek törlése:
        elephant.checkNearbyFoodSources();
        predators = elephant.getPredators();
        assertEquals(2, predators.size());

        // Mentett ragadozók listájának tartalomellenőrzése:
        assertTrue(predators.contains(carnivores.get(0)), "Elephant should consider a hungry lion as a threat.");
        assertTrue(predators.contains(carnivores.get(1)), "Elephant should consider a hungry hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(2)), "Elephant shouldn't consider a hungry and thirsty lion as a threat.");
        assertFalse(predators.contains(carnivores.get(3)), "Elephant shouldn't consider a hungry and thirsty hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(4)), "Elephant shouldn't consider a well-fed lion as a threat.");
        assertFalse(predators.contains(carnivores.get(5)), "Elephant shouldn't consider a well-fed hyena as a threat.");

        elephant.setPosition(new Position(320, 320));
        elephant.checkNearbyPredators();
        predators = elephant.getPredators();
                
        assertFalse(predators.contains(carnivores.get(0)), "Elephant shouldn't consider a hungry lion as a threat if it's too far.");
        assertFalse(predators.contains(carnivores.get(1)), "Elephant shouldn't consider a hungry hyena as a threat if it's too far.");
        assertFalse(predators.contains(carnivores.get(2)), "Elephant shouldn't consider a hungry and thirsty lion as a threat.");
        assertFalse(predators.contains(carnivores.get(3)), "Elephant shouldn't consider a hungry and thirsty hyena as a threat.");
        assertFalse(predators.contains(carnivores.get(4)), "Elephant shouldn't consider a well-fed lion as a threat.");
        assertFalse(predators.contains(carnivores.get(5)), "Elephant shouldn't consider a well-fed hyena as a threat.");    
    }
    
    
    @Test
    public void testFleeFromNearestPredator() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Elephant elephant = (Elephant)createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Ragadozók pozíciói:
        // (120, 120) <-- carnivores.get(0) <-- Éhes oroszlán
        // (230, 230) <-- carnivores.get(1) <-- Éhes hiéna
        // (200, 200) <-- carnivores.get(2) <-- Éhes és szomjas oroszlán
        // (300, 300) <-- carnivores.get(3) <-- Éhes és szomjas hiéna
        // (400, 400) <-- carnivores.get(4) <-- Étkező oroszlán
        // (600, 600) <-- carnivores.get(5) <-- Étkező hiéna
        // (800, 800) <-- carnivores.get(6) <-- Sima oroszlán
        // (900, 900) <-- carnivores.get(7) <-- Sima hiéna
        
        assertFalse(elephant.isFleeing(), "Elephant shouldn't be fleeing while being far from predators.");
        
        elephant.setPredators(carnivores);
        elephant.setPosition(new Position(80, 80));
        Carnivore expectedPredator = carnivores.get(0);
        assertEquals(expectedPredator, elephant.findNearestPredator(), "The nearest predator wasn't found.");
        assertTrue(elephant.isFleeing(), "Elephant should be fleeing while being near a hungry predator.");
        
        elephant.setPredators(carnivores);
        elephant.setPosition(new Position(220, 220));
        expectedPredator = carnivores.get(1);
        assertEquals(expectedPredator, elephant.findNearestPredator(), "The nearest predator wasn't found.");
        assertTrue(elephant.isFleeing(), "Elephant should be fleeing while being near a hungry predator.");
    }
}