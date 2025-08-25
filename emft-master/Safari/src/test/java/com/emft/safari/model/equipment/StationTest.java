/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.emft.safari.model.equipment;

import com.emft.safari.model.utilities.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dream Store
 */
public class StationTest {
        private Station station;
    public StationTest() {
        this.station = new Station(new Position (200, 200));
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPosition method, of class Station.
     */
    @Test
    public void testGetPosition() {
       
        assertEquals(new Position(200,200),station.getPosition());
     
    }

    /**
     * Test of getImageWidth method, of class Station.
     */
    @Test
    public void testGetImageWidth() {
        assertEquals(60, station.getImageWidth());
    }

    /**
     * Test of getImageHeight method, of class Station.
     */
    @Test
    public void testGetImageHeight() {
        assertEquals(100, station.getImageHeight());
    }

    /**
     * Test of setMark method, of class Station.
     */
    @Test
    public void testSetMark() {
        station.setMark(true);
        assertTrue(station.getMarked());
        station.setMark(false);
        assertFalse(station.getMarked());
    }

    /**
     * Test of getMarked method, of class Station.
     */
    @Test
    public void testGetMarked() {
        station.setMark(false);
        assertFalse(station.getMarked());
        station.setMark(true);
        assertTrue(station.getMarked());
    }
    
    @Test
      public void testPRICE() {
       assertEquals(200, Station.PRICE);
       
    }
}
