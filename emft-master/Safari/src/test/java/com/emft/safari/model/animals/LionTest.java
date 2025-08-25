package com.emft.safari.model.animals;

import static com.emft.safari.model.animals.Animal.MIDDLE_AGE_THRESHOLD;
import static com.emft.safari.model.animals.Animal.OLD_AGE_THRESHOLD;
import com.emft.safari.model.buildables.Lake;
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

public class LionTest {
    
    List<Herbivore> herbivores;
    List<Carnivore> carnivores;

    @BeforeEach
    public void setup() {
        herbivores = new ArrayList<>();
        carnivores = new ArrayList<>();
    }
        
    private Animal createAnimal(String animalType, Age age, GameSpeed gameSpeed, Position pos) {        
        ContentPanel contentPanel = EasyMock.createMock(ContentPanel.class);
        
        // Alapértelmezett pozíció beállítása:
        if (pos == null) {            
            switch (animalType) {
                case "Lion" -> {
                    pos = new Position(100, 100);
                }
                case "Hyena" -> {
                    pos = new Position(120, 120);
                }
                case "Elephant" -> {
                    pos = new Position(100, 120);
                }
                case "Giraffe" -> {
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
        
        
        // Környező ragadozók hozzáadása:                
        
        // Étkező oroszlán:
        ContentPanel dummyPanelCarnivore1 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore1.getUniquePosition()).andReturn(new Position(120, 120)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore1);
        Lion lion1 = new Lion(new Position(120, 120), Age.MIDDLE_AGED, dummyPanelCarnivore1);
        lion1.setEating();
        carnivores.add(lion1);
        
        // Étkező hiéna:
        ContentPanel dummyPanelCarnivore2 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore2.getUniquePosition()).andReturn(new Position(300, 300)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore2);
        Hyena hyena1 = new Hyena(new Position(300, 300), Age.MIDDLE_AGED, dummyPanelCarnivore2);
        hyena1.setEating();
        carnivores.add(hyena1);
        
        // Sima oroszlán:
        ContentPanel dummyPanelCarnivore3 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore3.getUniquePosition()).andReturn(new Position(400, 400)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore3);
        Lion lion2 = new Lion(new Position(400, 400), Age.MIDDLE_AGED, dummyPanelCarnivore3);
        carnivores.add(lion2);
        
        // Sima hiéna:
        ContentPanel dummyPanelCarnivore4 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelCarnivore4.getUniquePosition()).andReturn(new Position(600, 600)).anyTimes();
        EasyMock.replay(dummyPanelCarnivore4);
        Hyena hyena2 = new Hyena(new Position(600, 600), Age.MIDDLE_AGED, dummyPanelCarnivore4);
        carnivores.add(hyena2);        
        
        EasyMock.expect(contentPanel.getCarnivores()).andReturn(carnivores).anyTimes();
        
        
        // Környező növényevők hozzáadása:
        
        // Sima elefánt:
        ContentPanel dummyPanelHerbivore1 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelHerbivore1.getUniquePosition()).andReturn(new Position(120, 120)).anyTimes();
        EasyMock.expect(dummyPanelHerbivore1.getGameSpeed()).andReturn(gameSpeed).anyTimes();
        EasyMock.replay(dummyPanelHerbivore1);
        Elephant elephant1 = new Elephant(new Position(120, 120), Age.MIDDLE_AGED, dummyPanelHerbivore1);
        herbivores.add(elephant1);

        // Sima zsiráf:
        ContentPanel dummyPanelHerbivore2 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelHerbivore2.getUniquePosition()).andReturn(new Position(140, 140)).anyTimes();
        EasyMock.expect(dummyPanelHerbivore2.getGameSpeed()).andReturn(gameSpeed).anyTimes();
        EasyMock.replay(dummyPanelHerbivore2);
        Giraffe giraffe1 = new Giraffe(new Position(140, 140), Age.MIDDLE_AGED, dummyPanelHerbivore2);
        herbivores.add(giraffe1);

        // Beteg elefánt:
        ContentPanel dummyPanelHerbivore3 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelHerbivore3.getUniquePosition()).andReturn(new Position(200, 200)).anyTimes();
        EasyMock.expect(dummyPanelHerbivore3.getGameSpeed()).andReturn(gameSpeed).anyTimes();
        EasyMock.replay(dummyPanelHerbivore3);
        Elephant elephant2 = new Elephant(new Position(200, 200), Age.MIDDLE_AGED, dummyPanelHerbivore3);
        elephant2.setSick();
        herbivores.add(elephant2);

        // Beteg zsiráf:
        ContentPanel dummyPanelHerbivore4 = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(dummyPanelHerbivore4.getUniquePosition()).andReturn(new Position(600, 600)).anyTimes();
        EasyMock.expect(dummyPanelHerbivore4.getGameSpeed()).andReturn(gameSpeed).anyTimes();
        EasyMock.replay(dummyPanelHerbivore4);
        Giraffe giraffe2 = new Giraffe(new Position(600, 600), Age.MIDDLE_AGED, dummyPanelHerbivore4);
        giraffe2.setSick();
        herbivores.add(giraffe2);

        EasyMock.expect(contentPanel.getHerbivores()).andReturn(herbivores).anyTimes();
        
        EasyMock.replay(contentPanel);

            
        // Állat létrehozása:
        switch (animalType) {
            case "Lion" -> {
                return new Lion(pos, age, contentPanel);
            }
            case "Hyena" -> {
                return new Hyena(pos, age, contentPanel);
            }
            case "Elephant" -> {
                return new Elephant(pos, age, contentPanel);
            }
            case "Giraffe" -> {
                return new Giraffe(pos, age, contentPanel);
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
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);

            lion.increaseHunger();
            int newHunger = lion.getHunger();
            
            assertTrue(newHunger >= 1 && newHunger <= 3, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    @Test
    public void testIncreaseHungerByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.DAY, null);

            lion.increaseHunger();
            int newHunger = lion.getHunger();

            assertTrue(newHunger >= 4 && newHunger <= 6, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseHungerByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.WEEK, null);

            lion.increaseHunger();
            int newHunger = lion.getHunger();

            assertTrue(newHunger >= 7 && newHunger <= 9, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);

            lion.increaseThirst();
            int newThirst = lion.getThirst();

            assertTrue(newThirst >= 0 && newThirst <= 2, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.DAY, null);

            lion.increaseThirst();
            int newThirst = lion.getThirst();

            assertTrue(newThirst >= 2 && newThirst <= 4, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.WEEK, null);

            lion.increaseThirst();
            int newThirst = lion.getThirst();

            assertTrue(newThirst >= 5 && newThirst <= 7, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);

            lion.increaseAge();
            int newAge = lion.getActualLifeTime();

            assertTrue(newAge >= 0 && newAge <= 5, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.DAY, null);

            lion.increaseAge();
            int newAge = lion.getActualLifeTime();

            assertTrue(newAge >= 2 && newAge <= 7, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.WEEK, null);

            lion.increaseAge();
            int newAge = lion.getActualLifeTime();

            assertTrue(newAge >= 4 && newAge <= 9, "Age didn't increase accordingly. New value: " + newAge);
        }
    }

    
    @Test
    public void testIncreaseSicknessByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);

            lion.increaseSickness();
            int newSickness = lion.getSickness();

            assertTrue(newSickness >= 0 && newSickness <= 2, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
    
    
    @Test
    public void testIncreaseSicknessByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.DAY, null);

            lion.increaseSickness();
            int newSickness = lion.getSickness();

            assertTrue(newSickness >= 2 && newSickness <= 4, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
 
    
    @Test
    public void testIncreaseSicknessByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.WEEK, null);

            lion.increaseSickness();
            int newSickness = lion.getSickness();

            assertTrue(newSickness >= 4 && newSickness <= 6, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }

    
    @Test
    public void testDeath() {                
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.die();
        
        assertTrue(lion.isDead(), "Didn't die succesfully.");
        assertEquals(0, lion.getSpeedX(), "speedX didn't reset when dying.");
        assertEquals(0, lion.getSpeedY(), "speedY didn't reset when dying.");
    }
    
    
    @Test
    public void testGetHealed() {                
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.isSick = true;
        lion.getHealed();
        
        assertTrue(!lion.isSick() && lion.getSickness() == 0, "Didn't heal succesfully.");
    }


    @Test
    public void testImageSizeByAge() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(lion.getImageSize() == 40, "Image size for YOUNG should be 40.");

        lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(lion.getImageSize() == 50, "Image size for MIDDLE_AGED should be 50.");

        lion = createAnimal("Lion", Age.OLD, GameSpeed.HOUR, null);
        assertTrue(lion.getImageSize() == 60, "Image size for OLD should be 60.");
    }


    @Test
    public void testSetActualLifeTimeByAge() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.setActualLifeTimeByAge();
        assertEquals(0, lion.getActualLifeTime(), "Actual life time should be 0 for YOUNG age.");

        lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.setActualLifeTimeByAge();
        assertEquals(Lion.MIDDLE_AGE_THRESHOLD, lion.getActualLifeTime(), "Actual life time should match MIDDLE_AGE_THRESHOLD for MIDDLE_AGED.");

        lion = createAnimal("Lion", Age.OLD, GameSpeed.HOUR, null);
        lion.setActualLifeTimeByAge();
        assertEquals(Lion.OLD_AGE_THRESHOLD, lion.getActualLifeTime(), "Actual life time should match OLD_AGE_THRESHOLD for OLD age.");
    }


    @Test
    public void testCanMate() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(lion.canMate(), "Lion should be able to mate.");
    }


    @Test
    public void testCannotMateDueToAge() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(!lion.canMate(), "Young lion should not be able to mate.");
    }

    @Test
    public void testCannotMateDueToHunger() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.setHunger(1200);
        assertTrue(!lion.canMate(), "Hungry lion should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToThirst() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.setThirst(1000);
        assertTrue(!lion.canMate(), "Thirsty lion should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToTiredness() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.setTired();
        assertTrue(!lion.canMate(), "Tired lion should not be able to mate.");
    }

    
    @Test
    public void testCannotMateDueToNotMoving() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.setEating();
        assertTrue(!lion.canMate(), "Eating lion should not be able to mate.");
        lion.setDrinking();
        assertTrue(!lion.canMate(), "Drinking lion should not be able to mate.");
        lion.setResting();
        assertTrue(!lion.canMate(), "Resting lion should not be able to mate.");
    }
    

    @Test
    public void testCannotMateDueToDeath() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        lion.die();
        assertTrue(!lion.canMate(), "Dead lion should not be able to mate.");
    }


    @Test
    public void testIsGroupable() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));

        assertTrue(Animal.isGroupable(lion1, lion2), "Two lions should be groupable.");
    }


    @Test
    public void testNotIsGroupableDueToDifferentSpecies() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        assertFalse(Animal.isGroupable(lion, hyena), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(lion, elephant), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(lion, giraffe), "Different species shouldn't be groupable.");
    }

    
    @Test
    public void testNotIsGroupableDueToDistance() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(300, 300));

        assertFalse(Animal.isGroupable(lion1, lion2), "Lions too far apart shouldn't be groupable.");
    }
    
    
    @Test
    public void testNotIsGroupableDueToHunger() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        lion1.setHunger(1200);
        assertFalse(Animal.isGroupable(lion1, lion2), "A hungry lion shouldn't be groupable with a well-fed lion.");     

        lion2.setHunger(1200);
        assertFalse(Animal.isGroupable(lion1, lion2), "Hungry lions shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToThirst() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        lion1.setThirst(1000);
        assertFalse(Animal.isGroupable(lion1, lion2), "A thirsty lion shouldn't be groupable with a well-hydrated lion.");     

        lion2.setThirst(1000);
        assertFalse(Animal.isGroupable(lion1, lion2), "Thirsty lions shouldn't be groupable.");        
    }

    
    @Test
    public void testNotIsGroupableDueToTiredness() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        lion1.setTired();
        assertFalse(Animal.isGroupable(lion1, lion2), "A tired lion shouldn't be groupable with an energetic lion.");     

        lion2.setTired();
        assertFalse(Animal.isGroupable(lion1, lion2), "Tired lions shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToNotMoving() {
        Animal lion1 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal lion2 = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        lion1.setEating();
        assertFalse(Animal.isGroupable(lion1, lion2), "An eating lion shouldn't be groupable with a well-fed lion.");     

        lion2.setEating();
        assertFalse(Animal.isGroupable(lion1, lion2), "Tired lions shouldn't be groupable."); 
        

        lion1.setDrinking();
        assertFalse(Animal.isGroupable(lion1, lion2), "A drinking lion shouldn't be groupable with a well-hydrated lion.");

        lion2.setDrinking();
        assertFalse(Animal.isGroupable(lion1, lion2), "Drinking lions shouldn't be groupable.");


        lion1.setResting();
        assertFalse(Animal.isGroupable(lion1, lion2), "A resting lion shouldn't be groupable with a moving lion.");

        lion2.setResting();
        assertFalse(Animal.isGroupable(lion1, lion2), "Resting lions shouldn't be groupable.");
    }
    
    
    @Test
    public void testSetRandomSpeed() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        List<Integer> possibleValues = new ArrayList<>(List.of(-2, -1, 1, 2));
        
        for (int i = 0; i < 100; i++) {
            lion.setRandomSpeed();
            int speedX = (int)lion.getSpeedX();
            int speedY = (int)lion.getSpeedY();

            assertTrue(possibleValues.contains(speedX), "speedX should be one of the allowed values.");
            assertTrue(possibleValues.contains(speedY), "speedY should be one of the allowed values.");
        }
    }
    
    
    @Test
    public void testMoveTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        lion.moveTowards(targetPos);
        
        assertEquals(3.0, lion.getSpeedX(), 0.001);
        assertEquals(0.0, lion.getSpeedY(), 0.001);
        
        // Balra irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(80, 100);
        lion.moveTowards(targetPos);
        
        assertEquals(-3.0, lion.getSpeedX(), 0.001);
        assertEquals(0.0, lion.getSpeedY(), 0.001);
        
        // Felfelé irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 120);
        lion.moveTowards(targetPos);
        
        assertEquals(0.0, lion.getSpeedX(), 0.001);
        assertEquals(3.0, lion.getSpeedY(), 0.001);

        // Lefelé irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 80);
        lion.moveTowards(targetPos);
        
        assertEquals(0.0, lion.getSpeedX(), 0.001);
        assertEquals(-3.0, lion.getSpeedY(), 0.001);

        // Átlós mozgás:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(120, 120);
        lion.moveTowards(targetPos);
        double expected = 3 / Math.sqrt(2);
        assertEquals(expected, lion.getSpeedX(), 0.001);
        assertEquals(expected, lion.getSpeedY(), 0.001);
        
        // Rövid távolság:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(101, 100);
        lion.moveTowards(targetPos);
        assertTrue(Math.abs(lion.getSpeedX()) < 3.0);
        assertEquals(0.0, lion.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 100);
        lion.moveTowards(targetPos);
        assertEquals(2.0, lion.getSpeedX(), 0.001);
        assertEquals(2.0, lion.getSpeedY(), 0.001);
    }


    @Test
    public void testHandleWallCollision() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);

        // Bal falhoz ütközés:
        lion.setPosition(new Position(1, 200));
        lion.setSpeedX(-3);
        lion.setSpeedY(0);
        lion.handleWallCollision();
        assertEquals(25, lion.getPosition().getX());
        assertEquals(3, lion.getSpeedX(), 0.001);

        // Jobb falhoz ütközés:
        lion.setPosition(new Position(ContentPanel.WINDOW_WIDTH, 200));
        lion.setSpeedX(3);
        lion.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_WIDTH - 25, lion.getPosition().getX());
        assertEquals(-3, lion.getSpeedX(), 0.001);

        // Felső falhoz ütközés:
        lion.setPosition(new Position(300, 0));
        lion.setSpeedX(0);
        lion.setSpeedY(3);
        lion.handleWallCollision();
        assertEquals(25, lion.getPosition().getY());
        assertEquals(-3, lion.getSpeedY(), 0.001);

        // Alsó falhoz ütközés:
        lion.setPosition(new Position(300, ContentPanel.WINDOW_HEIGHT));
        lion.setSpeedX(0);
        lion.setSpeedY(-3);
        lion.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_HEIGHT - 25, lion.getPosition().getY());
        assertEquals(3, lion.getSpeedY(), 0.001);
    }
    
    
    @Test
    public void testCheckNearbyWaterSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        lion.checkNearbyWaterSources();
        List<Position> sources = lion.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");
        
        // Duplikációk elkerülése:
        lion.checkNearbyWaterSources();
        sources = lion.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");

        // Mentett tavak listájának tartalomellenőrzése:
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertFalse(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));

        // Újabb tó hozzáadása:
        lion.setPosition(new Position(200, 200));
        lion.checkNearbyWaterSources();
        sources = lion.getWaterSources();
        
        assertEquals(3, sources.size());
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertTrue(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));
    }
    
    
    @Test
    public void testFindNearestWaterSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        
        lion.setWaterSources(lakePositions);
        
        // Legközelebbi tó kiválasztása:
        
        lion.findNearestWaterSource();
        Position expectedTarget = lakePositions.get(0); // (150, 150)
        assertEquals(expectedTarget, lion.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(lion.isDrinking(), "Lion shouldn't be drinking while being far from the target lake.");
        
        lion.setPosition(new Position(210, 210));
        lion.findNearestWaterSource();
        expectedTarget = lakePositions.get(1); // (180, 180)
        assertEquals(expectedTarget, lion.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(lion.isDrinking(), "Lion shouldn't be drinking while being far from the target lake.");
        
        lion.setPosition(new Position(250, 250));
        lion.findNearestWaterSource();
        expectedTarget = lakePositions.get(2); // (300, 300)
        assertEquals(expectedTarget, lion.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(lion.isDrinking(), "Lion shouldn't be drinking while being far from the target lake.");
        
        // Ivás megkezdése:
        lion.setPosition(new Position(130, 130));
        lion.findNearestWaterSource();
        assertTrue(lion.isDrinking(), "Lion should be drinking while being near the target lake.");
    }
    
    
    @Test
    public void testIsBeingEaten() {
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertFalse(lion.isBeingEaten(), "Alive lion near eating carnivore shouldn't be considered as being eaten.");
        
        lion.setPosition(new Position(0, 0));
        assertFalse(lion.isBeingEaten(), "Alive lion far from eating carnivore shouldn't be considered as being eaten.");

        lion.setPosition(new Position(100, 100));
        lion.die();
        assertTrue(lion.isBeingEaten(), "Dead lion near eating carnivore should be considered as being eaten.");
        
        lion.setPosition(new Position(500, 300));
        assertFalse(lion.isBeingEaten(), "Dead lion far from eating carnivore shouldn't be considered as being eaten.");
    }

    
    @Test
    public void testChangeByTimeAgeChanging() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.changeByTime();
        assertEquals(Age.YOUNG, lion.getAge());
        assertEquals(40, lion.getImageSize());

        lion.setActualLifeTime(MIDDLE_AGE_THRESHOLD);
        lion.changeByTime();
        assertEquals(Age.MIDDLE_AGED, lion.getAge());
        assertEquals(50, lion.getImageSize());
        
        lion.setActualLifeTime(OLD_AGE_THRESHOLD);
        lion.changeByTime();
        assertEquals(Age.OLD, lion.getAge());
        assertEquals(60, lion.getImageSize());       
        assertEquals(0, lion.getChildCount());
    }
    
    
    @Test
    public void testChangeByTimeDeath() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.setActualLifeTime(lion.getMaxLifeTime());
        lion.changeByTime();
        assertTrue(lion.isDead());
    }
    
    
    @Test
    public void testStartActivityEating() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.EATING);
        assertEquals(0, lion.getHunger(), "Hunger didn't reset while eating.");
        assertEquals(0, lion.getSpeedX(), 0.001, "speedX didn't reset while eating.");
        assertEquals(0, lion.getSpeedY(), 0.001, "speedY didn't reset while eating.");
        assertTrue(lion.isEating(), "Didn't start eating.");
    }
    
    
    @Test
    public void testStartActivityDrinking() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.DRINKING);
        assertEquals(0, lion.getThirst(), "Thirst didn't reset while drinking.");
        assertEquals(0, lion.getSpeedX(), 0.001, "speedX didn't reset while drinking.");
        assertEquals(0, lion.getSpeedY(), 0.001, "speedY didn't reset while drinking.");
        assertTrue(lion.isDrinking(), "Didn't start drinking.");
    }
    
    
    @Test
    public void testStartActivityResting() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.RESTING);
        assertEquals(0, lion.getSpeedX(), 0.001, "speedX didn't reset while resting.");
        assertEquals(0, lion.getSpeedY(), 0.001, "speedY didn't reset while resting.");
        assertTrue(lion.isResting(), "Didn't start resting.");
    }
    
    
    @Test
    public void testStopActivityEating() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.EATING);
        lion.stopActivity(State.EATING);
        
        assertTrue(lion.isMoving(), "Didn't start moving after stopping eating activity.");
        assertTrue(lion.getSpeedX() != 0, "speedX stayed 0 even after stopping eating activity.");
        assertTrue(lion.getSpeedY() != 0, "speedY stayed 0 even after stopping eating activity.");
    }
    
    
    @Test
    public void testStopActivityDrinking() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.DRINKING);
        lion.stopActivity(State.DRINKING);
        
        assertTrue(lion.isMoving(), "Didn't start moving after stopping drinking activity.");
        assertTrue(lion.getSpeedX() != 0, "speedX stayed 0 even after stopping drinking activity.");
        assertTrue(lion.getSpeedY() != 0, "speedY stayed 0 even after stopping drinking activity.");
    }
    
    
    @Test
    public void testStopActivityResting() {
        Animal lion = createAnimal("Lion", Age.YOUNG, GameSpeed.HOUR, null);
        lion.startActivity(State.RESTING);
        lion.stopActivity(State.RESTING);
        
        assertTrue(lion.isMoving(), "Didn't start moving after stopping resting activity.");
        assertTrue(lion.getSpeedX() != 0, "speedX stayed 0 even after stopping resting activity.");
        assertTrue(lion.getSpeedY() != 0, "speedY stayed 0 even after stopping resting activity.");
    }
    
    
    @Test
    public void testCheckNearbyFoodSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Lion lion = (Lion)createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növényevők pozíciói:
        // (120, 120) <-- herbivores.get(0)
        // (140, 140) <-- herbivores.get(1)
        // (200, 200) <-- herbivores.get(2)
        // (600, 600) <-- herbivores.get(3)
                
        
        // Közeli növényevők mentése:
        lion.checkNearbyFoodSources();
        List<Herbivore> preys = lion.getPreys();
        assertEquals(3, preys.size());
        
        // Duplikációk elkerülése / lista törlése:
        lion.checkNearbyFoodSources();
        preys = lion.getPreys();
        assertEquals(3, preys.size());

        // Mentett növényevők listájának tartalomellenőrzése:
        assertTrue(preys.contains(herbivores.get(0)));
        assertTrue(preys.contains(herbivores.get(1)));
        assertTrue(preys.contains(herbivores.get(2)));
        assertFalse(preys.contains(herbivores.get(3)));

        // Újabb növényevő hozzáadása / eddigiek törlése:
        lion.setPosition(new Position(500, 500));
        lion.checkNearbyFoodSources();
        preys = lion.getPreys();
        
        assertEquals(1, preys.size());
        assertFalse(preys.contains(herbivores.get(0)));
        assertFalse(preys.contains(herbivores.get(1)));
        assertFalse(preys.contains(herbivores.get(2)));
        assertTrue(preys.contains(herbivores.get(3)));       
    }
    
    
    @Test
    public void testFindNearestFoodSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Lion lion = (Lion)createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növényevők pozíciói:
        // (120, 120) <-- herbivores.get(0)
        // (140, 140) <-- herbivores.get(1)
        // (200, 200) <-- herbivores.get(2)
        // (600, 600) <-- herbivores.get(3)
        
        lion.setPreys(herbivores);
        lion.setPosition(new Position(80, 80));
        
        // Legközelebbi növényevő kiválasztása:
        lion.findNearestFoodSource();
        Herbivore expectedTarget = herbivores.get(0); // (120, 120)
        assertEquals(expectedTarget.getPosition(), lion.getTargetPosition(), "The nearest herbivore wasn't set as target position.");
        assertFalse(lion.isEating(), "Lion shouldn't be eating while being far from the target herbivore.");
        
        lion.setPosition(new Position(300, 300));
        lion.setPreys(herbivores);
        lion.findNearestFoodSource();
        expectedTarget = herbivores.get(2); // (200, 200)
        assertEquals(expectedTarget.getPosition(), lion.getTargetPosition(), "The nearest herbivore wasn't set as target position.");
        assertFalse(lion.isEating(), "Lion shouldn't be eating while being far from the target herbivore.");
        
        // Evés megkezdése:
        lion.setPosition(new Position(130, 130));
        lion.setPreys(herbivores);
        lion.findNearestFoodSource();
        assertTrue(lion.isEating(), "Lion should be eating while being near the target herbivore.");
        assertTrue(herbivores.get(0).isDead(), "Prey should be dead while being very near a hungry carnivore.");
        assertFalse(lion.isSick(), "Lion shouldn't be sick while eating a healthy herbivore.");
        lion.setMoving();
        
        lion.setPosition(new Position(610, 610));
        lion.setPreys(herbivores);
        lion.findNearestFoodSource();
        assertTrue(lion.isEating(), "Lion should be eating while being near the target herbivore.");
        assertTrue(herbivores.get(0).isDead(), "Prey should be dead while being very near a hungry carnivore.");
        assertTrue(lion.isSick(), "Lion should be sick while eating a sick herbivore.");
        assertEquals(600, lion.getSickness(), "Lion should have 600 sickness while eating a sick herbivore.");
    }
    
    
    @Test
    public void testRunTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Lion lion = (Lion)createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        lion.runTowards(targetPos);
        
        assertEquals(4.5, lion.getSpeedX(), 0.001);
        assertEquals(0.0, lion.getSpeedY(), 0.001);
        
        // Balra irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(80, 100);
        lion.runTowards(targetPos);
        
        assertEquals(-4.5, lion.getSpeedX(), 0.001);
        assertEquals(0.0, lion.getSpeedY(), 0.001);
        
        // Felfelé irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 120);
        lion.runTowards(targetPos);
        
        assertEquals(0.0, lion.getSpeedX(), 0.001);
        assertEquals(4.5, lion.getSpeedY(), 0.001);

        // Lefelé irány:
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 80);
        lion.runTowards(targetPos);
        
        assertEquals(0.0, lion.getSpeedX(), 0.001);
        assertEquals(-4.5, lion.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        lion.setSpeedX(2);
        lion.setSpeedY(2);
        targetPos = new Position(100, 100);
        lion.runTowards(targetPos);
        assertEquals(2.0, lion.getSpeedX(), 0.001);
        assertEquals(2.0, lion.getSpeedY(), 0.001);
    }
}