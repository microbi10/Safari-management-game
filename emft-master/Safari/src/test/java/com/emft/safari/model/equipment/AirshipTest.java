/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Az Airship osztály metodusainak tesztelése
 */
public class AirshipTest {
        private Airship airship;
        private Station mockStation;
    
        
    
    public AirshipTest() {
    }
    
    private void createAirship() {
         this.airship = new Airship(new Position(300, 300), mockStation, new Position(900, 900));
    }
    
   @BeforeEach
    public void setUp() {
        mockStation = EasyMock.createMock(Station.class);
        Position stationPos = new Position(400, 400);
        EasyMock.expect(mockStation.getPosition()).andReturn(stationPos).anyTimes();
        EasyMock.replay(mockStation);
    }
 
  
    @AfterEach
    public void tearDown() {
    }

    @Test public void testGetPosition(){
       createAirship();
       assertEquals(mockStation.getPosition(), airship.getPosition());
    }
    
    @Test
    public void testGetRadiusOfVisibility() {
       createAirship();
       assertEquals(120, airship.getRadiusOfVisibility());  
    }
     @Test
    public void testGetSize() {
       createAirship();
       assertEquals(60, airship.getSize());
    }
    
    @Test
      public void testPRICE() {
       assertEquals(150, Airship.PRICE);
    }
    @Test 
     public void testGetFly(){
        createAirship();
        airship.fly();
        assertTrue(airship.getIsFlying());
        while(airship.getIsFlying()){
          airship.fly();
        }
        Position posres=new Position(401,400);
         assertEquals(posres, airship.getPosition());
         assertFalse(airship.getIsFlying());
          while(!airship.getIsFlying()){
          airship.fly();
        }
        assertTrue(airship.getIsFlying());
          
      }
     @Test 
    public  void testSetIsSelected(){
        createAirship();
        airship.setIsSelected(true);
        assertTrue(airship.getIsSelected());
        airship.setIsSelected(false);
        assertFalse(airship.getIsSelected());
    }
    @Test
    public void testGetStation(){
        createAirship();
        assertEquals(mockStation, airship.getStation());
    }
    @Test
    public void testChangeFlyPath(){
        createAirship();
        Station mockStation2 = EasyMock.createMock(Station.class);        
        Position stationPos2 = new Position(800, 800);
        EasyMock.expect(mockStation2.getPosition()).andReturn(stationPos2).anyTimes();
        EasyMock.replay(mockStation2);
        
        airship.changeFlyPath(new Position(200,200), mockStation2, new Position(700,700));
        assertEquals(mockStation2, airship.getStation());
        assertFalse(airship.isFlying);
        
    }
    
}


