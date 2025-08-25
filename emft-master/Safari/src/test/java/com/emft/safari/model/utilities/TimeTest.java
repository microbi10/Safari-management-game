package com.emft.safari.model.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TimeTest {

    /**
     * Test of setGameSpeed method, of class Time.
     */
    @Test
    public void testSetGameSpeed() {
        System.out.println("setGameSpeed");
        GameSpeed gameSpeed = GameSpeed.DAY;
        Time time = new Time(gameSpeed);
        GameSpeed newSpeed = GameSpeed.WEEK;
        time.setGameSpeed(newSpeed);
        assertEquals(newSpeed, time.getGameSpeed());
        assertEquals(GameSpeed.WEEK, time.getGameSpeed());
      
    }

    
    /**
     * Test of setDayHour method, of class Time.
     */
    @Test
    public void testUpdateTime() {
        System.out.println("updateTime");
        
        Time time = new Time(GameSpeed.HOUR);
        for(int i=1; i<=30;i++){
            time.updateTime();
        }
        assertEquals(6, time.getGameHour());
        assertEquals(1, time.getGameDay());
        assertEquals(0, time.getGameWeek());
      
    }

    
    /**
     * Test of timePrint method, of class Time.
     */
    @Test
    public void testTimePrint() {
        System.out.println("timePrint");
        
        Time time = new Time(GameSpeed.HOUR);
        
        for(int i = 1; i <= 30; i++){
            time.updateTime();
        }
      
        String result = time.print();
        assertEquals("Week: 0; Day: 1; Hour: 6", result);
    }

    
    /**
     * Test of getTimeValue method, of class Time.
     */
    @Test
    public void testGetTimeValue() {
        System.out.println("getTimeValue");
        Time time = new Time(GameSpeed.HOUR);
         for(int i=1; i<=32;i++){
            time.updateTime();
         }
        
        assertEquals(32, time.getTimeInHours());
        
    }

    /**
     * Test of getGameHour method, of class Time.
     */
    @Test
    public void testGetGameHour() {
        System.out.println("getGameHour");
        Time time = new Time(GameSpeed.HOUR);
        for(int i=1; i<=32;i++){
            time.updateTime();
        }
        assertEquals(8, time.getGameHour());
       
       
    }

    
    /**
     * Test of getGameWeek method, of class Time.
     */
    @Test
    public void testGetGameWeek() {
        System.out.println("getGameWeek");
        Time time = new Time(GameSpeed.WEEK);
        for(int i=1; i<=3;i++){
            time.updateTime();
        }
        
        assertEquals(3, time.getGameWeek());
    }

    
    /**
     * Test of getGameDay method, of class Time.
     */
    @Test
    public void testGetGameDay() {
        System.out.println("getGameDay");
        Time time = new Time(GameSpeed.HOUR);
        for(int i=1; i<=25;i++){
            time.updateTime();
        }
        assertEquals(1, time.getGameDay());
    }

    /**
     * Test of getPartOfDay method, of class Time.
     */
    @Test
    public void testGetPartOfDay() {
        Time time = new Time(GameSpeed.HOUR);
        
        for(int i = 1; i <= 12; i++){
            time.updateTime();
        }
        
        assertEquals(PartOfDay.MORNING, time.getPartOfDay());
        
        for(int i = 1; i <= 12; i++){
            time.updateTime();
        }
          
        assertEquals(PartOfDay.NIGHT, time.getPartOfDay());
    }
}