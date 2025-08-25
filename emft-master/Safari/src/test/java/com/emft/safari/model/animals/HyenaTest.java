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

public class HyenaTest {
    
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
                case "Hyena" -> {
                    pos = new Position(100, 100);
                }
                case "Lion" -> {
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
            case "Hyena" -> {
                return new Hyena(pos, age, contentPanel);
            }
            case "Lion" -> {
                return new Lion(pos, age, contentPanel);
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
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);

            hyena.increaseHunger();
            int newHunger = hyena.getHunger();
            
            assertTrue(newHunger >= 1 && newHunger <= 3, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    @Test
    public void testIncreaseHungerByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.DAY, null);

            hyena.increaseHunger();
            int newHunger = hyena.getHunger();

            assertTrue(newHunger >= 4 && newHunger <= 6, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseHungerByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.WEEK, null);

            hyena.increaseHunger();
            int newHunger = hyena.getHunger();

            assertTrue(newHunger >= 7 && newHunger <= 9, "Hunger didn't increase accordingly. New value: " + newHunger);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);

            hyena.increaseThirst();
            int newThirst = hyena.getThirst();

            assertTrue(newThirst >= 0 && newThirst <= 2, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.DAY, null);

            hyena.increaseThirst();
            int newThirst = hyena.getThirst();

            assertTrue(newThirst >= 2 && newThirst <= 4, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseThirstByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.WEEK, null);

            hyena.increaseThirst();
            int newThirst = hyena.getThirst();

            assertTrue(newThirst >= 5 && newThirst <= 7, "Thirst didn't increase accordingly. New value: " + newThirst);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);

            hyena.increaseAge();
            int newAge = hyena.getActualLifeTime();

            assertTrue(newAge >= 0 && newAge <= 5, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.DAY, null);

            hyena.increaseAge();
            int newAge = hyena.getActualLifeTime();

            assertTrue(newAge >= 2 && newAge <= 7, "Age didn't increase accordingly. New value: " + newAge);
        }
    }
    
    
    @Test
    public void testIncreaseAgeByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.WEEK, null);

            hyena.increaseAge();
            int newAge = hyena.getActualLifeTime();

            assertTrue(newAge >= 4 && newAge <= 9, "Age didn't increase accordingly. New value: " + newAge);
        }
    }

    
    @Test
    public void testIncreaseSicknessByHour() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);

            hyena.increaseSickness();
            int newSickness = hyena.getSickness();

            assertTrue(newSickness >= 0 && newSickness <= 2, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
    
    
    @Test
    public void testIncreaseSicknessByDay() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.DAY, null);

            hyena.increaseSickness();
            int newSickness = hyena.getSickness();

            assertTrue(newSickness >= 2 && newSickness <= 4, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }
 
    
    @Test
    public void testIncreaseSicknessByWeek() {                
        for (int i = 0; i < 100; i++) {
            Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.WEEK, null);

            hyena.increaseSickness();
            int newSickness = hyena.getSickness();

            assertTrue(newSickness >= 4 && newSickness <= 6, "Sickness didn't increase accordingly. New value: " + newSickness);
        }
    }

    
    @Test
    public void testDeath() {                
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.die();
        
        assertTrue(hyena.isDead(), "Didn't die succesfully.");
        assertEquals(0, hyena.getSpeedX(), "speedX didn't reset when dying.");
        assertEquals(0, hyena.getSpeedY(), "speedY didn't reset when dying.");
    }
    
    
    @Test
    public void testGetHealed() {                
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.isSick = true;
        hyena.getHealed();
        
        assertTrue(!hyena.isSick() && hyena.getSickness() == 0, "Didn't heal succesfully.");
    }


    @Test
    public void testImageSizeByAge() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(hyena.getImageSize() == 40, "Image size for YOUNG should be 40.");

        hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(hyena.getImageSize() == 50, "Image size for MIDDLE_AGED should be 50.");

        hyena = createAnimal("Hyena", Age.OLD, GameSpeed.HOUR, null);
        assertTrue(hyena.getImageSize() == 60, "Image size for OLD should be 60.");
    }


    @Test
    public void testSetActualLifeTimeByAge() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.setActualLifeTimeByAge();
        assertEquals(0, hyena.getActualLifeTime(), "Actual life time should be 0 for YOUNG age.");

        hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.setActualLifeTimeByAge();
        assertEquals(Hyena.MIDDLE_AGE_THRESHOLD, hyena.getActualLifeTime(), "Actual life time should match MIDDLE_AGE_THRESHOLD for MIDDLE_AGED.");

        hyena = createAnimal("Hyena", Age.OLD, GameSpeed.HOUR, null);
        hyena.setActualLifeTimeByAge();
        assertEquals(Hyena.OLD_AGE_THRESHOLD, hyena.getActualLifeTime(), "Actual life time should match OLD_AGE_THRESHOLD for OLD age.");
    }


    @Test
    public void testCanMate() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertTrue(hyena.canMate(), "Hyena should be able to mate.");
    }


    @Test
    public void testCannotMateDueToAge() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        assertTrue(!hyena.canMate(), "Young hyena should not be able to mate.");
    }

    @Test
    public void testCannotMateDueToHunger() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.setHunger(1200);
        assertTrue(!hyena.canMate(), "Hungry hyena should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToThirst() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.setThirst(1000);
        assertTrue(!hyena.canMate(), "Thirsty hyena should not be able to mate.");
    }
    
    
    @Test
    public void testCannotMateDueToTiredness() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.setTired();
        assertTrue(!hyena.canMate(), "Tired hyena should not be able to mate.");
    }

    
    @Test
    public void testCannotMateDueToNotMoving() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.setEating();
        assertTrue(!hyena.canMate(), "Eating hyena should not be able to mate.");
        hyena.setDrinking();
        assertTrue(!hyena.canMate(), "Drinking hyena should not be able to mate.");
        hyena.setResting();
        assertTrue(!hyena.canMate(), "Resting hyena should not be able to mate.");
    }
    

    @Test
    public void testCannotMateDueToDeath() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        hyena.die();
        assertTrue(!hyena.canMate(), "Dead hyena should not be able to mate.");
    }


    @Test
    public void testIsGroupable() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));

        assertTrue(Animal.isGroupable(hyena1, hyena2), "Two hyenas should be groupable.");
    }


    @Test
    public void testNotIsGroupableDueToDifferentSpecies() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal lion = createAnimal("Lion", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal elephant = createAnimal("Elephant", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        Animal giraffe = createAnimal("Giraffe", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        assertFalse(Animal.isGroupable(hyena, lion), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(hyena, elephant), "Different species shouldn't be groupable.");
        assertFalse(Animal.isGroupable(hyena, giraffe), "Different species shouldn't be groupable.");
    }

    
    @Test
    public void testNotIsGroupableDueToDistance() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(300, 300));

        assertFalse(Animal.isGroupable(hyena1, hyena2), "Hyenas too far apart shouldn't be groupable.");
    }
    
    
    @Test
    public void testNotIsGroupableDueToHunger() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        hyena1.setHunger(1200);
        assertFalse(Animal.isGroupable(hyena1, hyena2), "A hungry hyena shouldn't be groupable with a well-fed hyena.");     

        hyena2.setHunger(1200);
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Hungry hyenas shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToThirst() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        hyena1.setThirst(1000);
        assertFalse(Animal.isGroupable(hyena1, hyena2), "A thirsty hyena shouldn't be groupable with a well-hydrated hyena.");     

        hyena2.setThirst(1000);
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Thirsty hyenas shouldn't be groupable.");        
    }

    
    @Test
    public void testNotIsGroupableDueToTiredness() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        hyena1.setTired();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "A tired hyena shouldn't be groupable with an energetic hyena.");     

        hyena2.setTired();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Tired hyenas shouldn't be groupable.");        
    }
    
    
    @Test
    public void testNotIsGroupableDueToNotMoving() {
        Animal hyena1 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(100, 100));
        Animal hyena2 = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, new Position(120, 120));
        
        hyena1.setEating();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "An eating hyena shouldn't be groupable with a well-fed hyena.");     

        hyena2.setEating();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Tired hyenas shouldn't be groupable."); 
        

        hyena1.setDrinking();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "A drinking hyena shouldn't be groupable with a well-hydrated hyena.");

        hyena2.setDrinking();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Drinking hyenas shouldn't be groupable.");


        hyena1.setResting();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "A resting hyena shouldn't be groupable with a moving hyena.");

        hyena2.setResting();
        assertFalse(Animal.isGroupable(hyena1, hyena2), "Resting hyenas shouldn't be groupable.");
    }
    
    
    @Test
    public void testSetRandomSpeed() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        List<Integer> possibleValues = new ArrayList<>(List.of(-2, -1, 1, 2));
        
        for (int i = 0; i < 100; i++) {
            hyena.setRandomSpeed();
            int speedX = (int)hyena.getSpeedX();
            int speedY = (int)hyena.getSpeedY();

            assertTrue(possibleValues.contains(speedX), "speedX should be one of the allowed values");
            assertTrue(possibleValues.contains(speedY), "speedY should be one of the allowed values");
        }
    }
    
    
    @Test
    public void testMoveTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        hyena.moveTowards(targetPos);
        
        assertEquals(3.0, hyena.getSpeedX(), 0.001);
        assertEquals(0.0, hyena.getSpeedY(), 0.001);
        
        // Balra irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(80, 100);
        hyena.moveTowards(targetPos);
        
        assertEquals(-3.0, hyena.getSpeedX(), 0.001);
        assertEquals(0.0, hyena.getSpeedY(), 0.001);
        
        // Felfelé irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 120);
        hyena.moveTowards(targetPos);
        
        assertEquals(0.0, hyena.getSpeedX(), 0.001);
        assertEquals(3.0, hyena.getSpeedY(), 0.001);

        // Lefelé irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 80);
        hyena.moveTowards(targetPos);
        
        assertEquals(0.0, hyena.getSpeedX(), 0.001);
        assertEquals(-3.0, hyena.getSpeedY(), 0.001);

        // Átlós mozgás:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(120, 120);
        hyena.moveTowards(targetPos);
        double expected = 3 / Math.sqrt(2);
        assertEquals(expected, hyena.getSpeedX(), 0.001);
        assertEquals(expected, hyena.getSpeedY(), 0.001);
        
        // Rövid távolság:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(101, 100);
        hyena.moveTowards(targetPos);
        assertTrue(Math.abs(hyena.getSpeedX()) < 3.0);
        assertEquals(0.0, hyena.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 100);
        hyena.moveTowards(targetPos);
        assertEquals(2.0, hyena.getSpeedX(), 0.001);
        assertEquals(2.0, hyena.getSpeedY(), 0.001);
    }


    @Test
    public void testHandleWallCollision() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);

        // Bal falhoz ütközés:
        hyena.setPosition(new Position(1, 200));
        hyena.setSpeedX(-3);
        hyena.setSpeedY(0);
        hyena.handleWallCollision();
        assertEquals(25, hyena.getPosition().getX());
        assertEquals(3, hyena.getSpeedX(), 0.001);

        // Jobb falhoz ütközés:
        hyena.setPosition(new Position(ContentPanel.WINDOW_WIDTH, 200));
        hyena.setSpeedX(3);
        hyena.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_WIDTH - 25, hyena.getPosition().getX());
        assertEquals(-3, hyena.getSpeedX(), 0.001);

        // Felső falhoz ütközés:
        hyena.setPosition(new Position(300, 0));
        hyena.setSpeedX(0);
        hyena.setSpeedY(3);
        hyena.handleWallCollision();
        assertEquals(25, hyena.getPosition().getY());
        assertEquals(-3, hyena.getSpeedY(), 0.001);

        // Alsó falhoz ütközés:
        hyena.setPosition(new Position(300, ContentPanel.WINDOW_HEIGHT));
        hyena.setSpeedX(0);
        hyena.setSpeedY(-3);
        hyena.handleWallCollision();
        assertEquals(ContentPanel.WINDOW_HEIGHT - 25, hyena.getPosition().getY());
        assertEquals(3, hyena.getSpeedY(), 0.001);
    }
    
    
    @Test
    public void testCheckNearbyWaterSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        hyena.checkNearbyWaterSources();
        List<Position> sources = hyena.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");
        
        // Duplikációk elkerülése:
        hyena.checkNearbyWaterSources();
        sources = hyena.getWaterSources();
        assertEquals(2, sources.size(), "The size of the waterSources list didn't change accordingly.");

        // Mentett tavak listájának tartalomellenőrzése:
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertFalse(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));

        // Újabb tó hozzáadása:
        hyena.setPosition(new Position(200, 200));
        hyena.checkNearbyWaterSources();
        sources = hyena.getWaterSources();
        
        assertEquals(3, sources.size());
        assertTrue(sources.contains(lakePositions.get(0)));
        assertTrue(sources.contains(lakePositions.get(1)));
        assertTrue(sources.contains(lakePositions.get(2)));
        assertFalse(sources.contains(lakePositions.get(3)));
    }
    
    
    @Test
    public void testFindNearestWaterSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
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
        
        hyena.setWaterSources(lakePositions);
        
        // Legközelebbi tó kiválasztása:
        
        hyena.findNearestWaterSource();
        Position expectedTarget = lakePositions.get(0); // (150, 150)
        assertEquals(expectedTarget, hyena.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(hyena.isDrinking(), "Hyena shouldn't be drinking while being far from the target lake.");
        
        hyena.setPosition(new Position(210, 210));
        hyena.findNearestWaterSource();
        expectedTarget = lakePositions.get(1); // (180, 180)
        assertEquals(expectedTarget, hyena.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(hyena.isDrinking(), "Hyena shouldn't be drinking while being far from the target lake.");
        
        hyena.setPosition(new Position(250, 250));
        hyena.findNearestWaterSource();
        expectedTarget = lakePositions.get(2); // (300, 300)
        assertEquals(expectedTarget, hyena.getTargetPosition(), "The nearest lake wasn't set as target position.");
        assertFalse(hyena.isDrinking(), "Hyena shouldn't be drinking while being far from the target lake.");
        
        // Ivás megkezdése:
        hyena.setPosition(new Position(130, 130));
        hyena.findNearestWaterSource();
        assertTrue(hyena.isDrinking(), "Hyena should be drinking while being near the target lake.");
    }
    
    
    @Test
    public void testIsBeingEaten() {
        Animal hyena = createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        assertFalse(hyena.isBeingEaten(), "Alive hyena near eating carnivore shouldn't be considered as being eaten.");
        
        hyena.setPosition(new Position(0, 0));
        assertFalse(hyena.isBeingEaten(), "Alive hyena far from eating carnivore shouldn't be considered as being eaten.");

        hyena.setPosition(new Position(100, 100));
        hyena.die();
        assertTrue(hyena.isBeingEaten(), "Dead hyena near eating carnivore should be considered as being eaten.");
        
        hyena.setPosition(new Position(500, 300));
        assertFalse(hyena.isBeingEaten(), "Dead hyena far from eating carnivore shouldn't be considered as being eaten.");
    }

    
    @Test
    public void testChangeByTimeAgeChanging() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.changeByTime();
        assertEquals(Age.YOUNG, hyena.getAge());
        assertEquals(40, hyena.getImageSize());

        hyena.setActualLifeTime(MIDDLE_AGE_THRESHOLD);
        hyena.changeByTime();
        assertEquals(Age.MIDDLE_AGED, hyena.getAge());
        assertEquals(50, hyena.getImageSize());
        
        hyena.setActualLifeTime(OLD_AGE_THRESHOLD);
        hyena.changeByTime();
        assertEquals(Age.OLD, hyena.getAge());
        assertEquals(60, hyena.getImageSize());       
        assertEquals(0, hyena.getChildCount());
    }
    
    
    @Test
    public void testChangeByTimeDeath() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.setActualLifeTime(hyena.getMaxLifeTime());
        hyena.changeByTime();
        assertTrue(hyena.isDead());
    }
    
    
    @Test
    public void testStartActivityEating() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.EATING);
        assertEquals(0, hyena.getHunger(), "Hunger didn't reset while eating.");
        assertEquals(0, hyena.getSpeedX(), 0.001, "speedX didn't reset while eating.");
        assertEquals(0, hyena.getSpeedY(), 0.001, "speedY didn't reset while eating.");
        assertTrue(hyena.isEating(), "Didn't start eating.");
    }
    
    
    @Test
    public void testStartActivityDrinking() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.DRINKING);
        assertEquals(0, hyena.getThirst(), "Thirst didn't reset while drinking.");
        assertEquals(0, hyena.getSpeedX(), 0.001, "speedX didn't reset while drinking.");
        assertEquals(0, hyena.getSpeedY(), 0.001, "speedY didn't reset while drinking.");
        assertTrue(hyena.isDrinking(), "Didn't start drinking.");
    }
    
    
    @Test
    public void testStartActivityResting() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.RESTING);
        assertEquals(0, hyena.getSpeedX(), 0.001, "speedX didn't reset while resting.");
        assertEquals(0, hyena.getSpeedY(), 0.001, "speedY didn't reset while resting.");
        assertTrue(hyena.isResting(), "Didn't start resting.");
    }
    
    
    @Test
    public void testStopActivityEating() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.EATING);
        hyena.stopActivity(State.EATING);
        
        assertTrue(hyena.isMoving(), "Didn't start moving after stopping eating activity.");
        assertTrue(hyena.getSpeedX() != 0, "speedX stayed 0 even after stopping eating activity.");
        assertTrue(hyena.getSpeedY() != 0, "speedY stayed 0 even after stopping eating activity.");
    }
    
    
    @Test
    public void testStopActivityDrinking() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.DRINKING);
        hyena.stopActivity(State.DRINKING);
        
        assertTrue(hyena.isMoving(), "Didn't start moving after stopping drinking activity.");
        assertTrue(hyena.getSpeedX() != 0, "speedX stayed 0 even after stopping drinking activity.");
        assertTrue(hyena.getSpeedY() != 0, "speedY stayed 0 even after stopping drinking activity.");
    }
    
    
    @Test
    public void testStopActivityResting() {
        Animal hyena = createAnimal("Hyena", Age.YOUNG, GameSpeed.HOUR, null);
        hyena.startActivity(State.RESTING);
        hyena.stopActivity(State.RESTING);
        
        assertTrue(hyena.isMoving(), "Didn't start moving after stopping resting activity.");
        assertTrue(hyena.getSpeedX() != 0, "speedX stayed 0 even after stopping resting activity.");
        assertTrue(hyena.getSpeedY() != 0, "speedY stayed 0 even after stopping resting activity.");
    }
    
    
    @Test
    public void testCheckNearbyFoodSources() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Hyena hyena = (Hyena)createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növényevők pozíciói:
        // (120, 120) <-- herbivores.get(0)
        // (140, 140) <-- herbivores.get(1)
        // (200, 200) <-- herbivores.get(2)
        // (600, 600) <-- herbivores.get(3)
                
        
        // Közeli növényevők mentése:
        hyena.checkNearbyFoodSources();
        List<Herbivore> preys = hyena.getPreys();
        assertEquals(3, preys.size());
        
        // Duplikációk elkerülése / lista törlése:
        hyena.checkNearbyFoodSources();
        preys = hyena.getPreys();
        assertEquals(3, preys.size());

        // Mentett növényevők listájának tartalomellenőrzése:
        assertTrue(preys.contains(herbivores.get(0)));
        assertTrue(preys.contains(herbivores.get(1)));
        assertTrue(preys.contains(herbivores.get(2)));
        assertFalse(preys.contains(herbivores.get(3)));

        // Újabb növényevő hozzáadása / eddigiek törlése:
        hyena.setPosition(new Position(500, 500));
        hyena.checkNearbyFoodSources();
        preys = hyena.getPreys();
        
        assertEquals(1, preys.size());
        assertFalse(preys.contains(herbivores.get(0)));
        assertFalse(preys.contains(herbivores.get(1)));
        assertFalse(preys.contains(herbivores.get(2)));
        assertTrue(preys.contains(herbivores.get(3)));       
    }
    
    
    @Test
    public void testFindNearestFoodSource() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Hyena hyena = (Hyena)createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Növényevők pozíciói:
        // (120, 120) <-- herbivores.get(0)
        // (140, 140) <-- herbivores.get(1)
        // (200, 200) <-- herbivores.get(2)
        // (600, 600) <-- herbivores.get(3)
        
        hyena.setPreys(herbivores);
        hyena.setPosition(new Position(80, 80));
        
        // Legközelebbi növényevő kiválasztása:
        hyena.findNearestFoodSource();
        Herbivore expectedTarget = herbivores.get(0); // (120, 120)
        assertEquals(expectedTarget.getPosition(), hyena.getTargetPosition(), "The nearest herbivore wasn't set as target position.");
        assertFalse(hyena.isEating(), "Hyena shouldn't be eating while being far from the target herbivore.");
        
        hyena.setPosition(new Position(300, 300));
        hyena.setPreys(herbivores);
        hyena.findNearestFoodSource();
        expectedTarget = herbivores.get(2); // (200, 200)
        assertEquals(expectedTarget.getPosition(), hyena.getTargetPosition(), "The nearest herbivore wasn't set as target position.");
        assertFalse(hyena.isEating(), "Hyena shouldn't be eating while being far from the target herbivore.");
        
        // Evés megkezdése:
        hyena.setPosition(new Position(130, 130));
        hyena.setPreys(herbivores);
        hyena.findNearestFoodSource();
        assertTrue(hyena.isEating(), "Hyena should be eating while being near the target herbivore.");
        assertTrue(herbivores.get(0).isDead(), "Prey should be dead while being very near a hungry carnivore.");
        assertFalse(hyena.isSick(), "Hyena shouldn't be sick while eating a healthy herbivore.");
        hyena.setMoving();
        
        hyena.setPosition(new Position(610, 610));
        hyena.setPreys(herbivores);
        hyena.findNearestFoodSource();
        assertTrue(hyena.isEating(), "Hyena should be eating while being near the target herbivore.");
        assertTrue(herbivores.get(0).isDead(), "Prey should be dead while being very near a hungry carnivore.");
        assertTrue(hyena.isSick(), "Hyena should be sick while eating a sick herbivore.");
        assertEquals(600, hyena.getSickness(), "Hyena should have 600 sickness while eating a sick herbivore.");
    }
    
    
    @Test
    public void testRunTowards() {
        // Állat alapértelmezett pozíciója: (100, 100)
        Hyena hyena = (Hyena)createAnimal("Hyena", Age.MIDDLE_AGED, GameSpeed.HOUR, null);
        
        // Jobbra irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        Position targetPos = new Position(120, 100);
        hyena.runTowards(targetPos);
        
        assertEquals(4.5, hyena.getSpeedX(), 0.001);
        assertEquals(0.0, hyena.getSpeedY(), 0.001);
        
        // Balra irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(80, 100);
        hyena.runTowards(targetPos);
        
        assertEquals(-4.5, hyena.getSpeedX(), 0.001);
        assertEquals(0.0, hyena.getSpeedY(), 0.001);
        
        // Felfelé irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 120);
        hyena.runTowards(targetPos);
        
        assertEquals(0.0, hyena.getSpeedX(), 0.001);
        assertEquals(4.5, hyena.getSpeedY(), 0.001);

        // Lefelé irány:
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 80);
        hyena.runTowards(targetPos);
        
        assertEquals(0.0, hyena.getSpeedX(), 0.001);
        assertEquals(-4.5, hyena.getSpeedY(), 0.001);

        // Nincs mozgás (azonos pozíció):
        hyena.setSpeedX(2);
        hyena.setSpeedY(2);
        targetPos = new Position(100, 100);
        hyena.runTowards(targetPos);
        assertEquals(2.0, hyena.getSpeedX(), 0.001);
        assertEquals(2.0, hyena.getSpeedY(), 0.001);
    }
}