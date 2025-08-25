/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * A drone osztály metódusainak tesztelése
 */
public class DroneTest {
        private Drone drone;
        private Station mockStation;
        
        public DroneTest(){
            
        
        }
    
    private void createDrone() {
         this.drone = new Drone(new Position(300, 300), mockStation, new Position(900, 900));
    }
    
    
    

    @BeforeEach
    public void setUp() {
        mockStation = EasyMock.createMock(Station.class);

        Position stationPos = new Position(400, 400);

        // Beállítjuk, hogy a mock mindig ezt a pozíciót adja vissza, ha meghívják a getPosition()-t
        EasyMock.expect(mockStation.getPosition()).andReturn(stationPos).anyTimes();

        EasyMock.replay(mockStation);
    }
    
    @AfterEach
    public void tearDown() {
        //EasyMock.verify(mockStation);  // Ellenőrizzük, hogy a mock helyesen lett használva
       
    }

    @Test public void testGetPosition(){
       createDrone();
       assertEquals(mockStation.getPosition(), drone.getPosition());
    }
    
     @Test
    public void testGetRadiusOfVisibility() {
       createDrone();
       assertEquals(80, drone.getRadiusOfVisibility());  
    }
    
      @Test
    public void testGetSize() {
       createDrone();
       assertEquals(50, drone.getSize());
    }
    
    @Test
      public void testPRICE() {
       assertEquals(100, Drone.PRICE);
    }
    
      @Test 
      public void testGetFly(){
        createDrone();
        drone.fly();
        assertTrue(drone.getIsFlying());
        while(drone.getIsFlying()){
          drone.fly();
        }
         Position posres=new Position(401,400);
         assertEquals(posres, drone.getPosition());
        
         assertFalse(drone.getIsFlying());
          while(!drone.getIsFlying()){
          drone.fly();
        }
        assertTrue(drone.getIsFlying());
          
      }
    @Test 
    public  void testSetIsSelected(){
        createDrone();
        drone.setIsSelected(true);
        assertTrue(drone.getIsSelected());
        drone.setIsSelected(false);
        assertFalse(drone.getIsSelected());
    }
    @Test
    public void testGetStation(){
        createDrone();
        assertEquals(mockStation, drone.getStation());
    }
    
    @Test
    public void testChangeFlyPath(){
        createDrone();
        Station mockStation2 = EasyMock.createMock(Station.class);        
        Position stationPos2 = new Position(800, 800);
        EasyMock.expect(mockStation2.getPosition()).andReturn(stationPos2).anyTimes();
        EasyMock.replay(mockStation2);
        
        drone.changeFlyPath(new Position(200,200), mockStation2, new Position(700,700));
        assertEquals(mockStation2, drone.getStation());
        assertFalse(drone.isFlying);
        
    }
}
