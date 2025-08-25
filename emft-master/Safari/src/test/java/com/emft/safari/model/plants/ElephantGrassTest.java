package com.emft.safari.model.plants;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import com.emft.safari.model.utilities.Size;
import com.emft.safari.model.animals.Herbivore;
import com.emft.safari.model.plants.Plant;
import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ElephantGrassTest {

    private ElephantGrass elephantGrass;
    private ContentPanel contentPanel;
    private Position startPos;

    @BeforeEach
    public void setUp() {
        contentPanel = EasyMock.createMock(ContentPanel.class);
        startPos = new Position(100, 100);

        List<Herbivore> mockHerbivores = new ArrayList<>();
        EasyMock.expect(contentPanel.getHerbivores()).andReturn(mockHerbivores).anyTimes();

        EasyMock.replay(contentPanel);

        elephantGrass = new ElephantGrass(startPos, contentPanel);
    }

    @AfterEach
    public void tearDown() {
        elephantGrass = null;
        contentPanel = null;
    }

    @Test
    public void testInitialState() {
        assertEquals(startPos, elephantGrass.getPosition(), "Pozíció nem megfelelő.");
        assertEquals(1, elephantGrass.sizePoint, 0.01f, "Kezdeti méretpont nem megfelelő.");
        assertFalse(elephantGrass.isEatable(), "A növény kezdetben nem lehet ehető.");
        assertTrue(elephantGrass.isAlive, "A növénynek élőnek kell lennie a létrehozás után.");
        assertEquals(80, elephantGrass.getImageSize(), "A kép méretének 80-nak kell lennie kezdetben.");
    }

    @Test
    public void testGrowIncreasesSizePoint() {
        float originalSizePoint = elephantGrass.sizePoint;
        elephantGrass.grow();
        assertEquals(originalSizePoint + 1, elephantGrass.sizePoint, 0.01f, "A grow() nem növelte meg a sizePoint értékét megfelelően.");
    }

    @Test
    public void testDecreaseSizeDoesNotGoBelowOne() {
        elephantGrass.sizePoint = 15;
        elephantGrass.decreaseSize();
        assertEquals(1, elephantGrass.sizePoint, 0.01f, "A sizePoint nem csökkenhet 1 alá.");
    }

    @Test
    public void testChangeByTimeGrowth() {
        elephantGrass.sizePoint = 5f;
        elephantGrass.actualLifeTime = 900;

        elephantGrass.changeByTime();

        assertEquals(Size.MEDIUM, elephantGrass.size, "A növény méretének MEDIUM-nak kell lennie ebben az állapotban.");
        assertTrue(elephantGrass.isEatable(), "A növénynek ehetőnek kell lennie közepes méret felett.");
    }

    @Test
    public void testIsBeingEatenReturnsTrueWhenNearbyHerbivoreIsEating() {
        Herbivore herbivore = EasyMock.createMock(Herbivore.class);

        EasyMock.expect(herbivore.isDead()).andReturn(false);
        EasyMock.expect(herbivore.isEating()).andReturn(true);
        EasyMock.expect(herbivore.getPosition()).andReturn(new Position(105, 105));

        List<Herbivore> mockHerbivores = new ArrayList<>();
        mockHerbivores.add(herbivore);

        contentPanel = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(contentPanel.getHerbivores()).andReturn(mockHerbivores).anyTimes();

        EasyMock.replay(contentPanel, herbivore);

        elephantGrass = new ElephantGrass(startPos, contentPanel);

        assertTrue(elephantGrass.isBeingEaten(), "A növényt éppen eszik, true értéket kell visszaadnia.");

        EasyMock.verify(contentPanel, herbivore);
    }

    @Test
    public void testIsBeingEatenReturnsFalseWhenHerbivoreIsTooFar() {
        Herbivore herbivore = EasyMock.createMock(Herbivore.class);

        EasyMock.expect(herbivore.isDead()).andReturn(false);
        EasyMock.expect(herbivore.isEating()).andReturn(true);
        EasyMock.expect(herbivore.getPosition()).andReturn(new Position(200, 200));

        List<Herbivore> mockHerbivores = new ArrayList<>();
        mockHerbivores.add(herbivore);

        contentPanel = EasyMock.createMock(ContentPanel.class);
        EasyMock.expect(contentPanel.getHerbivores()).andReturn(mockHerbivores).anyTimes();

        EasyMock.replay(contentPanel, herbivore);

        elephantGrass = new ElephantGrass(startPos, contentPanel);

        assertFalse(elephantGrass.isBeingEaten(), "A növény túl messze van, nem szabadna hogy épp eszik.");

        EasyMock.verify(contentPanel, herbivore);
    }
}