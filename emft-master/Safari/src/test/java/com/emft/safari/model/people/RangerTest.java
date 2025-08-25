package com.emft.safari.model.people;

import com.emft.safari.model.animals.Carnivore;
import com.emft.safari.model.animals.Hyena;
import com.emft.safari.model.animals.Lion;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.utilities.GameSpeed;
import com.emft.safari.view.ContentPanel;
import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RangerTest {

    private Ranger ranger;
    private ContentPanel contentPanel;
    private Position startPos;

    @BeforeEach
    public void setUp() {
        contentPanel = EasyMock.createMock(ContentPanel.class);
        startPos = new Position(100, 100);

        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.expect(contentPanel.getPoachers()).andReturn(new ArrayList<>()).anyTimes();
        EasyMock.replay(contentPanel);

        ranger = new Ranger(startPos, contentPanel, 0);
    }

    @Test
    public void testRangerInitialState() {
        assertNull(ranger.getTargetPredator(), "A kezdeti célpontnak nullnak kell lennie.");
    }

    @Test
    public void testRangerMovesTowardsPredatorAndKills_Lion() {
        Lion lion = EasyMock.createMock(Lion.class);
        Position lionPos = new Position(105, 105); // Közel van, hogy azonnal megölhesse

        EasyMock.expect(lion.isDead()).andReturn(false).anyTimes();
        EasyMock.expect(lion.getPosition()).andReturn(lionPos).anyTimes();
        lion.die();
        EasyMock.expectLastCall().once();

        Director director = EasyMock.createMock(Director.class);
        director.earnMoney(40);
        EasyMock.expectLastCall().once();

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();
        EasyMock.expect(contentPanel.getPoachers()).andReturn(new ArrayList<>()).anyTimes();
        EasyMock.expect(contentPanel.getDirector()).andReturn(director).anyTimes();

        EasyMock.replay(contentPanel, lion, director);

        ranger = new Ranger(startPos, contentPanel, 0);
        ranger.isHunting = true;
        ranger.targetPredator = lion;

        ranger.move();

        EasyMock.verify(lion, director);
    }

    @Test
    public void testRangerFindsAndKillsPoacher() {
        Poacher poacher = EasyMock.createMock(Poacher.class);
        Position poacherPos = new Position(105, 105); // Közel van

        EasyMock.expect(poacher.getPosition()).andReturn(poacherPos).anyTimes();
        poacher.die();
        EasyMock.expectLastCall().once();

        List<Poacher> poacherList = new ArrayList<>();
        poacherList.add(poacher);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getPoachers()).andReturn(poacherList).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.HOUR).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();

        EasyMock.replay(contentPanel, poacher);

        ranger = new Ranger(startPos, contentPanel, 0);
        ranger.move(); // Meghívja a checkNearbyPoachers + findNearestPoacher metódust is

        EasyMock.verify(poacher);
    }

    @Test
    public void testRangerMovesTowardsPoacher_DaySpeed() {
        Poacher poacher = EasyMock.createMock(Poacher.class);
        Position poacherPos = new Position(500, 500);

        EasyMock.expect(poacher.getPosition()).andReturn(poacherPos).anyTimes();

        List<Poacher> poacherList = new ArrayList<>();
        poacherList.add(poacher);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getPoachers()).andReturn(poacherList).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.DAY).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();

        EasyMock.replay(contentPanel, poacher);

        ranger = new Ranger(startPos, contentPanel, 0);

        Position original = new Position(ranger.getPosition().getX(), ranger.getPosition().getY());

        ranger.move();

        assertNotEquals(original.getX(), ranger.getPosition().getX(), "X koordinátának változnia kellett volna.");
        assertNotEquals(original.getY(), ranger.getPosition().getY(), "Y koordinátának változnia kellett volna.");

        EasyMock.verify(poacher);
    }

    @Test
    public void testRangerMovesTowardsPoacher_WeekSpeed() {
        Poacher poacher = EasyMock.createMock(Poacher.class);
        Position poacherPos = new Position(600, 600);

        EasyMock.expect(poacher.getPosition()).andReturn(poacherPos).anyTimes();

        List<Poacher> poacherList = new ArrayList<>();
        poacherList.add(poacher);

        EasyMock.reset(contentPanel);
        EasyMock.expect(contentPanel.getPoachers()).andReturn(poacherList).anyTimes();
        EasyMock.expect(contentPanel.getGameSpeed()).andReturn(GameSpeed.WEEK).anyTimes();
        EasyMock.expect(contentPanel.getTimeValue()).andReturn(0).anyTimes();

        EasyMock.replay(contentPanel, poacher);

        ranger = new Ranger(startPos, contentPanel, 0);

        Position original = new Position(ranger.getPosition().getX(), ranger.getPosition().getY());

        ranger.move();

        assertNotEquals(original.getX(), ranger.getPosition().getX(), "X koordinátának változnia kellett volna.");
        assertNotEquals(original.getY(), ranger.getPosition().getY(), "Y koordinátának változnia kellett volna.");

        EasyMock.verify(poacher);
    }
}
