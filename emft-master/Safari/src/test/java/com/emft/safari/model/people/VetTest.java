package com.emft.safari.model.people;

import com.emft.safari.model.animals.Animal;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import com.emft.safari.model.utilities.GameSpeed;
import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VetTest {

    private Vet vet;
    private ContentPanel contentPanel;
    private Position startPos;

    @BeforeEach
    public void setUp() {
        contentPanel = EasyMock.createMock(ContentPanel.class);
        startPos = new Position(100, 100);

        List<Animal> animals = new ArrayList<>();
        EasyMock.expect(contentPanel.getAnimals()).andReturn(animals).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();

        EasyMock.replay(contentPanel);

        vet = new Vet(startPos, contentPanel, 0);
    }

    @Test
    public void testVetInitialState() {
        assertFalse(vet.isDoneWorking(), "Az állatorvos nem végezhetett még a munkájával az elején.");
    }

    @Test
    public void testCheckEmploymentStatus_MarkedAsDoneAfter30Days() {
        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(30 * 24 + 1).anyTimes(); // 1 hónap + 1 óra
        EasyMock.replay(contentPanel);

        vet = new Vet(startPos, contentPanel, 0);
        vet.checkEmploymentStatus();

        assertTrue(vet.isDoneWorking(), "30 nap után az állatorvosnak végeznie kellene a munkával.");
    }

    @Test
    public void testVetHealsSickAnimal() {
        Animal sickAnimal = EasyMock.createMock(Animal.class);
        Position sickPos = new Position(120, 120);

        EasyMock.expect(sickAnimal.isSick()).andReturn(true).anyTimes();
        EasyMock.expect(sickAnimal.isDead()).andReturn(false).anyTimes();
        EasyMock.expect(sickAnimal.getPosition()).andReturn(sickPos).anyTimes();

        sickAnimal.getHealed(); // Várjuk, hogy ezt meghívja
        EasyMock.expectLastCall().once();

        List<Animal> animals = new ArrayList<>();
        animals.add(sickAnimal);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getAnimals()).andReturn(animals).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.replay(contentPanel, sickAnimal);

        vet = new Vet(startPos, contentPanel, 0);

        vet.move(); // Itt történik a gyógyítás is

        EasyMock.verify(sickAnimal);
    }

    @Test
    public void testVetMovesTowardsSickAnimalHour() {
        Animal sickAnimal = EasyMock.createMock(Animal.class);
        Position sickPos = new Position(500, 500);

        EasyMock.expect(sickAnimal.isSick()).andReturn(true).anyTimes();
        EasyMock.expect(sickAnimal.isDead()).andReturn(false).anyTimes();
        EasyMock.expect(sickAnimal.getPosition()).andReturn(sickPos).anyTimes();

        List<Animal> animals = new ArrayList<>();
        animals.add(sickAnimal);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getAnimals()).andReturn(animals).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.replay(contentPanel, sickAnimal);

        vet = new Vet(startPos, contentPanel, 0);

        Position originalPos = new Position(vet.getPosition().getX(), vet.getPosition().getY());

        vet.move();

        assertNotEquals(originalPos.getX(), vet.getPosition().getX(), "Az állatorvosnak mozdulnia kellett volna X irányban.");
        assertNotEquals(originalPos.getY(), vet.getPosition().getY(), "Az állatorvosnak mozdulnia kellett volna Y irányban.");

        EasyMock.verify(sickAnimal);
    }

    @Test
    public void testVetMovesTowardsSickAnimalDay() {
        Animal sickAnimal = EasyMock.createMock(Animal.class);
        Position sickPos = new Position(500, 500);

        EasyMock.expect(sickAnimal.isSick()).andReturn(true).anyTimes();
        EasyMock.expect(sickAnimal.isDead()).andReturn(false).anyTimes();
        EasyMock.expect(sickAnimal.getPosition()).andReturn(sickPos).anyTimes();

        List<Animal> animals = new ArrayList<>();
        animals.add(sickAnimal);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getAnimals()).andReturn(animals).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.DAY).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.replay(contentPanel, sickAnimal);

        vet = new Vet(startPos, contentPanel, 0);

        Position originalPos = new Position(vet.getPosition().getX(), vet.getPosition().getY());

        vet.move();

        assertNotEquals(originalPos.getX(), vet.getPosition().getX(), "Az állatorvosnak mozdulnia kellett volna X irányban.");
        assertNotEquals(originalPos.getY(), vet.getPosition().getY(), "Az állatorvosnak mozdulnia kellett volna Y irányban.");

        EasyMock.verify(sickAnimal);
    }

    @Test
    public void testVetMovesTowardsSickAnimalWeek() {
        Animal sickAnimal = EasyMock.createMock(Animal.class);
        Position sickPos = new Position(500, 500);

        EasyMock.expect(sickAnimal.isSick()).andReturn(true).anyTimes();
        EasyMock.expect(sickAnimal.isDead()).andReturn(false).anyTimes();
        EasyMock.expect(sickAnimal.getPosition()).andReturn(sickPos).anyTimes();

        List<Animal> animals = new ArrayList<>();
        animals.add(sickAnimal);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getAnimals()).andReturn(animals).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.WEEK).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.replay(contentPanel, sickAnimal);

        vet = new Vet(startPos, contentPanel, 0);

        Position originalPos = new Position(vet.getPosition().getX(), vet.getPosition().getY());

        vet.move();

        assertNotEquals(originalPos.getX(), vet.getPosition().getX(), "Az állatorvosnak mozdulnia kellett volna X irányban.");
        assertNotEquals(originalPos.getY(), vet.getPosition().getY(), "Az állatorvosnak mozdulnia kellett volna Y irányban.");

        EasyMock.verify(sickAnimal);
    }
}