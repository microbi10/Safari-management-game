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
public class CameraTest {
    private Camera camera;
    
    public CameraTest() {
    }
    
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        this.camera=new Camera(new Position(100,100));
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test public void testGetPosition(){
        assertEquals(new Position(100,100), camera.getPosition());
    
    }
    
    @Test
    public void testGetRadiusOfVisibility() {
       assertEquals(120, camera.getRadiusOfVisibility());
       
    }
     @Test
    public void testGetSize() {
       assertEquals(50, camera.getSize());
       
    }
    
    @Test
      public void testPRICE() {
       assertEquals(200, Camera.Price);
       
    }
    
}
