package com.emft.safari.model.utilities;

public class Time {
    
    private GameSpeed gameSpeed;
    private PartOfDay partOfDay;
    private int gameDay;
    private int gameHour;
    private int gameWeek;
    
    public Time (GameSpeed gameSpeed) {
        this.gameHour = 0;
        this.gameDay = 0;
        this.gameWeek = 0;
        this.gameSpeed = gameSpeed; 
        this.partOfDay = PartOfDay.NIGHT;
    }
    
    
    public int getGameHour(){
        return gameHour;
    }
    
    public int getGameDay(){
        return gameDay;
    }
    
    public int getGameWeek(){
        return gameWeek;
    }
        
    public GameSpeed getGameSpeed(){
        return gameSpeed;
    }
    
    public PartOfDay getPartOfDay(){
        return partOfDay;
    }
    
    public void setGameSpeed(GameSpeed gameSpeed){
        this.gameSpeed = gameSpeed;
    }
    
    
    /**
     * Az időértékek (óra, nap, hét) és a napszakok (reggel, este, éjszaka) frissítését végzi a játék aktuális sebessége alapján.
     */
    public void updateTime() {
        
        // Eltelt órák/napok/hetek számának növelése:
        if (gameSpeed != null) {
            switch (gameSpeed) {
                case HOUR -> gameHour++;
                case DAY -> gameDay++;
                default -> gameWeek++;
            }
        }
        
        // 0 és 23 közötti lehet csak az óra értéke:
        if (gameHour == 24){
            gameDay++;
            gameHour = 0;    
        } 
        if (gameDay == 7){
            gameWeek++;
            gameDay = 0;    
        } 
        //this.gameWeek=gameDay/7;
        // Napszak frissítése:
        if (gameSpeed == GameSpeed.HOUR){
            switch (gameHour) {
                case 22 -> partOfDay = PartOfDay.NIGHT;   
                case 18 -> partOfDay = PartOfDay.EVENING;
                case 6  -> partOfDay = PartOfDay.MORNING;
            }
        }
    }
    
    
    /**
     * Az idő szöveges megjelenítését végzi (Week: 0; Day: 0; Hour: 0).
     * @return az idő szöveges változata
     */ 
    public String print(){
        return "Week: " + gameWeek + "; " + 
               "Day: "  + gameDay  + "; " +
               "Hour: " + gameHour;
    }
    
    
    /**
     * Kiszámítja az aktuális időt órákban.
     * @return aktuális idő órákban
     */
    public int getTimeInHours() {
        return (gameWeek * 7 * 24 + gameDay * 24 + gameHour);
    }   
}
