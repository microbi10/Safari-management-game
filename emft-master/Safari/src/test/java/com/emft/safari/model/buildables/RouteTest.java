package com.emft.safari.model.buildables;

import com.emft.safari.model.equipment.Station;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;
import java.util.ArrayList;
import java.util.Map;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;


/**
 *
 * @author Dream Store
 */
public class RouteTest {
    private ContentPanel mockContentPanel;
    private ArrayList<Position> positions;
    private Route route;
    private Route spyRoute;
    private ArrayList<Position> testList;
    
    public RouteTest() {
        positions = new ArrayList<Position>();
        positions.add(new Position(50,50));
        positions.add(new Position(100,100));
        testList=new ArrayList<>();
        testList.add(new Position(15,15));
        testList.add(new Position(17,17));
        testList.add(new Position(19,19));
        testList.add(new Position(20,20));
    }
    
    private void createRoute(){
        Position start=new Position(15,15);
        route=new Route(start,mockContentPanel);
        spyRoute=spy(route);
        doReturn(positions).when(spyRoute).getPoints();
        doReturn(positions).when(spyRoute).getForwDirection();
        doReturn(positions).when(spyRoute).getBackwDirection();
        
    }
  
    @BeforeEach
    public void setUp() {
        mockContentPanel = EasyMock.createMock(ContentPanel.class);
        
      
        EasyMock.expect(mockContentPanel.isThereAnimal(new Position(500,500))).andReturn(false).anyTimes();
        EasyMock.expect(mockContentPanel.isThereAnimal(new Position(700,700))).andReturn(true).anyTimes();
        
        EasyMock.expect(mockContentPanel.isTherePeople(new Position(400,400))).andReturn(false).anyTimes();
        EasyMock.expect(mockContentPanel.isTherePeople(new Position(600,600))).andReturn(true).anyTimes();

        EasyMock.replay(mockContentPanel);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of setFree method, of class Route.
     */
    @Test
    public void testSetFree() {
       createRoute();
       assertTrue(route.getFree());
       route.setFree(false);
       assertFalse(route.getFree());
       route.setFree(true);
       assertTrue(route.getFree());
    }

    /**
     * Test of getFree method, of class Route.
     */
    @Test
    public void testGetFree() {
        createRoute();
        assertTrue(route.getFree());
    }

    /**
     * Test of setSelected method, of class Route.
     */
    @Test
    public void testSetSelected() {
        createRoute();
        assertFalse(route.getSelected());
        route.setSelected(true);
        assertTrue(route.getSelected());
        route.setSelected(false);
        assertFalse(route.getSelected());
    }

    /**
     * Test of getSelected method, of class Route.
     */
    @Test
    public void testGetSelected() {
        createRoute();
        route.setSelected(true);
        assertTrue(route.getSelected());
        route.setSelected(false);
        assertFalse(route.getSelected());
        
    }

    /**
     * Test of getPoints method, of class Route.
     */
    @Test
    public void testGetPoints() {
        createRoute();
        assertEquals(positions, spyRoute.getPoints());
       
    }

   

    /**
     * Test of addRoute method, of class Route.
     */
    @Test
    public void testAddRoute() {
        createRoute();
        ArrayList<Position> section= new ArrayList<>();
        section.add(new Position(17,17));
        section.add(new Position(19,19));
        section.add(new Position(20,20));
        route.addRoute(section);
        int index=0;
        for(Position position: route.getPoints()){
            assertEquals(position, testList.get(index));
            index++;
        }
        }

    /**
     * Test of getEndRoute method, of class Route.
     */
    @Test
    public void testGetEndRoute() {
        createRoute();
        assertEquals(testList.get(0), route.getEndRoute());
        ArrayList<Position> section= new ArrayList<>();
        section.add(new Position(17,17));
        section.add(new Position(19,19));
        section.add(new Position(20,20));
        route.addRoute(section);
        assertEquals(testList.get(3), route.getEndRoute());
        
    }

    /**
     * Test of onRoute method, of class Route.
     */
    @Test
    public void testOnRoute() {
        createRoute();
        ArrayList<Position> section= new ArrayList<>();
        section.add(new Position(17,17));
        section.add(new Position(19,19));
        section.add(new Position(20,20));
        route.addRoute(section);
        Map<String, Object> res=route.onRoute(new Position(18,18));
        assertTrue((boolean)res.get("exist"));
        Position pos1=(Position)res.get("position");
        assertEquals(testList.get(0),pos1);
        res=route.onRoute(new Position(50,50));
        assertFalse((boolean)res.get("exist"));
        assertNull(res.get("position"));
         
    }

    /**
     * Test of setComplete method, of class Route.
     */
    @Test
    public void testSetComplete() {
       createRoute();
       assertFalse(route.getComplete());
       ArrayList<Position> section2=new ArrayList<>();
       section2.add(new Position(40,40));
       section2.add(new Position(50,50));
       route.addRoute(section2);
       route.setComplete();
       assertTrue(route.getComplete());
    }

    /**
     * Test of getComplete method, of class Route.
     */
    @Test
    public void testGetComplete() {
        createRoute();
        assertFalse(route.getComplete());
    }

    /**
     * Test of getForwDirection method, of class Route.
     */
    @Test
    public void testGetForwDirection() {
        createRoute();
        assertEquals(positions, spyRoute.getForwDirection());
    }

    /**
     * Test of getBackwDirection method, of class Route.
     */
    @Test
    public void testGetBackwDirection() {
        createRoute();
        assertEquals(positions, spyRoute.getBackwDirection());
    }

    /**
     * Test of isThereAnimal method, of class Route.
     */
    @Test
    public void testIsThereAnimal() {
        createRoute();
        assertFalse(route.isThereAnimal(new Position(500,500)));
        assertTrue(route.isThereAnimal(new Position(700,700)));
    }

    /**
     * Test of isTherePeople method, of class Route.
     */
    @Test
    public void testIsTherePeople() {
        createRoute();
        assertFalse(route.isTherePeople(new Position(400,400)));
        assertTrue(route.isTherePeople(new Position(600,600)));
    }
    
}
