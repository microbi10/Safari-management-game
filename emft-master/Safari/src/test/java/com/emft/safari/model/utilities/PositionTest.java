/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.emft.safari.model.utilities;

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
public class PositionTest {
        private Position position;
    public PositionTest() {
        
    }
    private void createPosition(){
        position= new Position(13, 17);
    }
    
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of setPosition method, of class Position.
     */
    @Test
    public void testSetPosition() {
        createPosition();
        assertEquals(13, position.getX());
        assertEquals(17, position.getY());
        position.setPosition(19,21);
        assertEquals(19, position.getX());
        assertEquals(21, position.getY());
    }

    /**
     * Test of getX method, of class Position.
     */
    @Test
    public void testGetX() {
        createPosition();
        assertEquals(13, position.getX());
        
    }

    /**
     * Test of setX method, of class Position.
     */
    @Test
    public void testSetX() {
        createPosition();
        position.setX(27);
        assertEquals(27, position.getX());
        assertEquals(17, position.getY());
    }

    /**
     * Test of getY method, of class Position.
     */
    @Test
    public void testGetY() {
        createPosition();
        
        assertEquals(17, position.getY());
       
    }

    /**
     * Test of setY method, of class Position.
     */
    @Test
    public void testSetY() {
        createPosition();
        assertEquals(17, position.getY());
        position.setY(29);
        assertEquals(29, position.getY());
        assertEquals(13, position.getX());
    }

    /**
     * Test of distance method, of class Position.
     */
    @Test
    public void testDistance() {
       createPosition();
       position.setPosition(0,0);
       Position pos2=new Position(8,6);
       assertEquals(10,position.distance(pos2),0.01);
       position.setPosition(3,7);
       pos2.setPosition(6, 3);
       assertEquals(5, position.distance(pos2),0.1);
    }

    /**
     * Test of differ method, of class Position.
     */
    @Test
    public void testDiffer() {
        createPosition(); //13, 17
        Position pos2=new Position(8,6);
        Position diff=position.differ(pos2);
        assertEquals(new Position(5,11), diff);
        pos2.setPosition(6, 2);
        assertEquals(new Position(7,15), position.differ(pos2));
        
    }

    /**
     * Test of average method, of class Position.
     */
    @Test
    public void testAverage() {
       createPosition(); //13, 17
       Position pos2=new Position(8,6);
       Position pos3=new Position(3,7);
       assertEquals(new Position(8,10),position.average(pos3, pos2));
       pos2.setPosition(1,2);
       assertEquals(new Position(5,8),position.average(pos3, pos2));
    }

    /**
     * Test of equals method, of class Position.
     */
    @Test
    public void testEquals() {
        createPosition();
        Position pos2=new Position(21,11);
        assertFalse(position==pos2);
        Position pos3=position;
        assertTrue(position==pos3);
        Position pos4=null;
        assertNull(pos4);
    }

    /**
     * Test of hashCode method, of class Position.
     */
    @Test
    public void testHashCode() {
        createPosition(); //13 17
        assertEquals(31*13+17, position.hashCode());
    }

    /**
     * Test of toString method, of class Position.
     */
    @Test
    public void testToString() {
       createPosition();//13 17
       assertEquals("(13, 17)", position.toString());
    }
    
}
