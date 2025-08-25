package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.emft.safari.model.buildables.Route;

public class JeepTest {
    private Route mockRoute;
    private ArrayList<Position> posList;
    private ArrayList<Position> forward;
    private ArrayList<Position> backward;
    private Jeep jeep;
   
    
    private void createJeep() {
       
        mockRoute=EasyMock.createMock(Route.class);
        posList = new ArrayList<>();
        posList.add( new Position(10, 10));
        posList.add(new Position(13, 15));
        posList.add(new Position(17, 21));
        posList.add(new Position(27, 27));
        
        forward = new  ArrayList<>();
        forward.add(new Position(10, 10));
        forward.add(new Position(10, 10));
        forward.add(new Position(10, 10));
        forward.add(new Position(10, 10));
        
        backward = new ArrayList<>();
        backward.add(new Position(-10, -10));
        backward.add(new Position(-10, -10));
        backward.add(new Position(-10, -10));
        backward.add(new Position(-10, -10));
        
       
        EasyMock.expect(mockRoute.getPoints()).andReturn(posList).anyTimes();
        EasyMock.expect(mockRoute.getForwDirection()).andReturn(forward).anyTimes();
        EasyMock.expect(mockRoute.getBackwDirection()).andReturn(backward).anyTimes();
        EasyMock.expect(mockRoute.isThereAnimal(EasyMock.<Position>anyObject())).andReturn(false).anyTimes();
        EasyMock.expect(mockRoute.isTherePeople(EasyMock.<Position>anyObject())).andReturn(false).anyTimes();
        
        mockRoute.setFree(true);
        EasyMock.expectLastCall().once();
        
        mockRoute.setFree(false);
        EasyMock.expectLastCall().once();
        
        this.jeep = new Jeep();
        EasyMock.replay(mockRoute);
    }
    
  
    
    @AfterEach
    public void tearDown() {
        mockRoute = null;
    }

    
    /**
     * A Jeep osztály move metódusának tesztelése.
     */
    @Test
    public void testMove() {
        this.createJeep();
        jeep.move(mockRoute);
        
        assertEquals(forward.get(0), jeep.getDirection());
        assertEquals(forward.get(0).getX(), jeep.getDirection().getX());
        assertEquals(forward.get(0).getY(), jeep.getDirection().getY());
        
        assertTrue(jeep.getUnderWay());
    }

    
    /**
     * A Jeep osztály step metódusának tesztelése.
     */
    @Test
    public void testStep() {
        this.createJeep();
        jeep.move(mockRoute);
        
        for (int i = 0; i < 3; i++){
            jeep.step();
        }
        assertTrue(jeep.getUnderWay());
        
        for(int i = 0; i < 25; i++) {
            jeep.step();
        }
        assertFalse(jeep.getUnderWay());
    }

   
    /**
     * A Jeep osztály getDirection metódusának tesztelése.
     */
    @Test
    public void testGetDirection() {
        this.createJeep();
        jeep.move(mockRoute);
        
        for(int i = 1; i < 4; i++){
            jeep.step();
        }
        assertEquals(mockRoute.getForwDirection().get(0), jeep.getDirection());
        
        for(int i = 1; i < 30; i++){
            jeep.step();
        }
        assertEquals(mockRoute.getBackwDirection().get(0), jeep.getDirection());
    }

    
    /**
     * A Jeep osztály getUnderWay metódusának tesztelése.
     */
    @Test
    public void testGetUnderWay() {
        this.createJeep();
        jeep.move(mockRoute);
        
        for(int i = 1; i < 3; i++){
            jeep.step();
        }
        assertTrue(jeep.getUnderWay());
        
        for(int i = 1; i < 30; i++){
            jeep.step();
        }
        assertFalse(jeep.getUnderWay());
    }

   
    /**
     * A Jeep osztály getPosition metódusának tesztelése.
     */
    @Test
    public void testGetPosition() {
        this.createJeep();
        assertNull(jeep.getPosition());
        jeep.move(mockRoute);
      
        assertEquals(mockRoute.getPoints().getFirst(), jeep.getPosition());
    }

   
    /**
     * A Jeep osztály getOrientation metódusának tesztelése.
     */
    @Test
    public void testGetOrientation() {
        this.createJeep();
        jeep.move(mockRoute);
        
        for(int i = 1; i < 3; i++){
            jeep.step();
        }
        assertTrue(jeep.getOrientation());
        
        for(int i = 1; i < 25; i++){
            jeep.step();
        }
        assertFalse(jeep.getOrientation());
    }

    
    /**
     * A Jeep osztály getRoute metódusának tesztelése.
     */
    @Test
    public void testGetRoute() {
      this.createJeep();
        
      assertNull(jeep.getRoute());
      jeep.move(mockRoute);
      
      assertSame(mockRoute, jeep.getRoute());
    }
}