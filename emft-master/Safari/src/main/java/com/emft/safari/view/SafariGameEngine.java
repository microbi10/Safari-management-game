package com.emft.safari.view;

import com.emft.safari.model.buildables.Route;
import com.emft.safari.model.utilities.Time;
import com.emft.safari.model.utilities.GameSpeed;
import com.emft.safari.model.utilities.GameLevel;
import com.emft.safari.model.people.*;
import com.emft.safari.model.animals.Animal;

import com.emft.safari.model.animals.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLayeredPane;
import javax.swing.JToggleButton;
import javax.swing.Timer;

public final class SafariGameEngine extends JPanel{

    private final GameLevel gameLevel;
    private GameSpeed gameSpeed;
    public static int GAME_SPEED = 1;
    private final Time time;
    
    public final ContentPanel contentPanel;
    private final ControlPanel controlPanel;
    private final Director director;
    private final MiniMap miniMap;
    private int touristNumber;
    private int entranceFee;
    
    private final int WINDOW_WIDTH = 1600;
    private final int WINDOW_HEIGHT = 1200;

    private JRadioButton speedHourButton;
    private JRadioButton speedDayButton;
    private JRadioButton speedWeekButton;
    private JToggleButton pauseButton;
    private JLabel timeLabel;
    private JLabel directorNameLabel;
    private JLabel moneyLabel;
    private JLabel partOfDayLabel;

    private final SafariGUI parentFrame;
    private final JPanel headerPanel;
    
    private int lastCheckDay = 0;
    private int lastCheckWeek = 0;
    private JSpinner entranceCount;

    private final JScrollPane scrollPane;
    private final JPanel scrollBarPanel;
    private final Point scrollPosition;

    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private boolean isPaused;


    public SafariGameEngine(Director director, GameLevel level, SafariGUI safariGUI) {
        super();
        
        this.isPaused = false;
        this.scrollPosition = new Point(0,0);
        this.touristNumber = 0;
        this.director = director;
        this.gameSpeed = GameSpeed.HOUR;
        this.time = new Time(gameSpeed);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.gameLevel = level;
        this.parentFrame = safariGUI;
        
        // HeaderPanel inicializálása:
        this.headerPanel = new JPanel();
        this.headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.headerPanel.setOpaque(false);
        this.headerPanel.setPreferredSize(new Dimension(this.WINDOW_WIDTH, 40));
        initHeaderItems();
        this.add(headerPanel);

        // ContentPanel inicializálása:
        this.contentPanel = new ContentPanel(director, this, parentFrame);
        this.contentPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.contentPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.contentPanel.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);
        
        // Minimap inicializálása:
        this.miniMap = new MiniMap(300, 350, WINDOW_WIDTH, WINDOW_HEIGHT, contentPanel, this);
        this.miniMap.setVisible(true);
        this.miniMap.setBounds(this.getWidth() - this.getWidth() + scrollPosition.x, this.getHeight() - 333 + scrollPosition.y, 250, 200);
        layeredPane.add(miniMap, JLayeredPane.PALETTE_LAYER);
        this.contentPanel.setComponentZOrder(miniMap, 0);

        // ScrollPane inicializálása:
        this.scrollPane = new JScrollPane(layeredPane);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        this.scrollBarPanel = new JPanel(new BorderLayout());
        this.scrollBarPanel.add(horizontalScrollBar, BorderLayout.CENTER);

        // ControlPanel inicializálása:
        this.controlPanel = new ControlPanel(this, this.contentPanel, this.director, parentFrame);
        this.controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.controlPanel.setPreferredSize(new Dimension(WINDOW_WIDTH-300, 100));
        this.controlPanel.setBackground(new Color(144,132,110));
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.add(scrollPane);
        bodyPanel.add(controlPanel);
        bodyPanel.add(scrollBarPanel);
        this.add(bodyPanel);

        // Minden időzítő indítása:
        startAllTimers();
    }

    
    /**
     * Elindítja az összes játékbeli időzítőt.
     */ 
    private void startAllTimers() {
        timer1 = new Timer(1000, e -> {
            if (!isPaused) {
                time.updateTime();
            }
                
            contentPanel.setProgressBar();
        });
        timer1.start();

        timer2 = new Timer(100, e -> {
            updateGameSpeed();
          
            timeLabel.setText(time.print());
            
            partOfDayLabel.setText("Period: " + time.getPartOfDay());
            
            entranceFee = (int)entranceCount.getValue();
            moneyLabel.setText("Money: " + director.getMoney() + " $");
 
            controlPanel.setEnabledButtonJeep();
            controlPanel.setEnabledButtonHuntPredator();
            controlPanel.setInfoPanel();
            changeByTime();
        });
        timer2.start();
          
        timer3 = new Timer(40, e-> {
            contentPanel.jeepTour();
            contentPanel.messageBoxObserv();

            // Állapotok frissítése:
            contentPanel.updateAnimals();
            contentPanel.updatePlants();
            contentPanel.updateLakes();
            contentPanel.updateVets();
            contentPanel.updateRangers();
            contentPanel.updatePoachers();
            contentPanel.updateDayTime();           
        });
        timer3.start();

        timer4 = new Timer(10, e -> {
            scrollPosition.x = scrollPane.getViewport().getViewPosition().x;
            scrollPosition.y = scrollPane.getViewport().getViewPosition().y;
            miniMap.setBounds(this.getWidth() - this.getWidth() + scrollPosition.x, this.getHeight() - 321 + scrollPosition.y, 250, 200);
            if(!isPaused){
                contentPanel.moveJeeps();
                contentPanel.flyMobileDevice();
            }
            contentPanel.repaint();
           
        });
        timer4.start();
    }   
    
    
    /**
     * Leállítja az összes játékbeli időzítőt.
     */ 
    public void stopAllTimers(){
        timer1.stop();
        timer2.stop();
        timer3.stop();
        timer4.stop();
    }
    
     
    /**
     * Inicializálja a felhasználói felület fejlécének tartalmát.
     */ 
    public void initHeaderItems() {
        Font normalFont = new Font("Arial", Font.BOLD, 14);
        
        // Az idő megjelenítése:
        timeLabel = new JLabel(time.print());
        timeLabel.setFont(normalFont);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 20));
        headerPanel.add(timeLabel);
        
        // A napszak megjelenítése:
        partOfDayLabel = new JLabel("Period: " + time.getPartOfDay());
        partOfDayLabel.setFont(normalFont);
        partOfDayLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 40));
        headerPanel.add(partOfDayLabel);

        // A játéksebesség megjelenítése:
        speedHourButton = new JRadioButton("Hour");
        speedHourButton.setFocusable(false);
        speedDayButton = new JRadioButton("Day");
        speedDayButton.setFocusable(false);
        speedWeekButton = new JRadioButton("Week");
        speedWeekButton.setFocusable(false);
        
        headerPanel.add(speedHourButton);
        headerPanel.add(speedDayButton);
        headerPanel.add(speedWeekButton);
        
        ButtonGroup speedGroup = new ButtonGroup();
        speedGroup.add(speedHourButton);
        speedGroup.add(speedDayButton);
        speedGroup.add(speedWeekButton);
        speedHourButton.setSelected(true);
        
        JLabel spaceBetweenElementsLabel = new JLabel();
        spaceBetweenElementsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        headerPanel.add(spaceBetweenElementsLabel);
        
        // Játékleállító gomb megjelenítése:
        pauseButton = new JToggleButton("Pause");
        pauseButton.setFocusable(false);
        
        pauseButton.addItemListener(e -> {
            if (pauseButton.isSelected()) {
                pauseButton.setText("Resume");
                isPaused = true;
            }
            else {
                pauseButton.setText("Pause");
                isPaused = false;
            }
        });
        
        headerPanel.add(pauseButton);
        
        // A játék nehézségi fokozatának megjelenítése:
        JLabel gameLevelLabel = new JLabel("Difficulty: " + gameLevel);
        gameLevelLabel.setFont(normalFont);
        gameLevelLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 25));
        headerPanel.add(gameLevelLabel);
          
        // Az igazgató nevének megjelenítése:
        directorNameLabel = new JLabel("Director: " + director.getName());
        directorNameLabel.setFont(normalFont);
        directorNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
        headerPanel.add(directorNameLabel);

        // Az igazgató pénzösszegének megjelenítése:
        moneyLabel = new JLabel("Money: " + director.getMoney() + " $");
        moneyLabel.setFont(normalFont);
        moneyLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
        headerPanel.add(moneyLabel);

        // Belépőjegy árának megszabása:
        entranceCount = new JSpinner(new SpinnerNumberModel(100, 100, 300, 20));
        ((JSpinner.DefaultEditor) entranceCount.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) entranceCount.getEditor()).getTextField().setFocusable(false);
        entranceFee = (int) entranceCount.getValue();
        entranceCount.setPreferredSize(new Dimension(60, 25)); 
        JLabel entranceLabel = new JLabel("Belépődíj: ");
        entranceLabel.setFont(normalFont);
        entranceLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        headerPanel.add(entranceLabel);
        headerPanel.add(entranceCount);
    }

    
    /**
     * Frissíti a játék sebességét a sebességváltoztató rádiógombok alapján.
     */
    public void updateGameSpeed() {
        if (speedHourButton.isSelected()) {
            gameSpeed = GameSpeed.HOUR;
            GAME_SPEED = 1;
        }
        else if (speedDayButton.isSelected()) {
            gameSpeed = GameSpeed.DAY;
            GAME_SPEED = 2;
        }
        else if (speedWeekButton.isSelected()) {
            gameSpeed = GameSpeed.WEEK;
            GAME_SPEED = 3;
        }
         
        time.setGameSpeed(this.gameSpeed);
    }
    
   
    /**
     * Frissíti a játék állapotát (véget ért-e) és a turisták számát.
     */
    private void changeByTime() {
        int actWeek = time.getGameWeek();
        
        if (SafariGameEngine.GAME_SPEED < 2){
            int actDay = time.getGameDay();
            
            if (Math.abs(actDay - lastCheckDay) == 1){
                lastCheckDay = actDay;
                int tourist = setTouristNumbers();
               //  System.out.println("A alacsony sebesseg. Touritsak: "+tourist);

                this.changeTouristNumber(tourist);
                this.director.addTourist(tourist);

            } 
        }
        else if(actWeek!=lastCheckWeek) {
                int tourist=setTouristNumbers();
              //  System.out.println("A leggyorsabb sebesseg. Touritsak: "+tourist);
                this.director.addTourist(7*tourist);
                this.changeTouristNumber(7*tourist);
        } 
              
        if ((boolean)checkGameEnd().get("end")){
            MessageBox endBox = contentPanel.getEndMessageBox();
            endBox.setVisible(true);
            
            if ((boolean) checkGameEnd().get("win")) {

                endBox.setMessage("GRATULÁLUN MEGNYERTE A JATEKOT, FOLYTATHATJA A JATEKOT TOVABB!!");
            } else {
                endBox.setMessage("ÖN ELVESZTETTE A JATEKOT!");
            }
        }
       
        lastCheckWeek = actWeek;
    }
    
    
    /**
     * Ellenőrzi, hogy a játék véget ért-e.
     * @return a játék aktuális állapota (tart-e még, vége van-e)
     */ 
    private Map<String, Boolean> checkGameEnd() {
        
        Map<String, Boolean> result = new HashMap<>();
        
        if (contentPanel.getAnimals().isEmpty() || (director.getMoney() < 100)){
            result.put("end", true);
            result.put("win", false);
            return result;
        }
       
        int actMonth = time.getGameWeek()/4; 
        
        if (actMonth == (lastCheckWeek / 4)) {
            result.put("end", false);
            return result;
        }
        else {
            int minMonth = 3;
            
            switch (gameLevel) {
                case(GameLevel.EASY)   -> minMonth = 3;
                case(GameLevel.MEDIUM) -> minMonth = 6;
                case(GameLevel.HARD)   -> minMonth = 12;
            }
            
            if (actMonth > minMonth){
                result.put("end", true);
                result.put("win", true);
                return result;
            } 
            
            if (isGameOver()) {
                result.put("end", true);
                result.put("win", false);
                return result;
            }
            else {
                result.put("end", false);
                return result;
            }
        }
    }
     
    public static int getGameSpeedInt(){
        return SafariGameEngine.GAME_SPEED;
    }
    
    
    /** 
     * Ellenőrzi a nehézség alapján, hogy a játékos elvesztette-e a játékot (állatok és turisták száma alapján).
     * @return a játékos elvesztette-e a játékot
     */
    private boolean isGameOver() {
        int minCarnivore = 0;
        int minHerbivore = 0;
        int minTourist = 0;
        
        switch (gameLevel) {
            case(GameLevel.EASY) -> {
                minCarnivore = 10;
                minHerbivore = 15;
                minTourist = 100;
            }
            case(GameLevel.MEDIUM) -> {
                minCarnivore = 15;
                minHerbivore = 20;
                minTourist = 150;
            }
            case(GameLevel.HARD) -> {
                minCarnivore = 20;
                minHerbivore = 25;
                minTourist = 200;
            }
        }
        
        int carnivoreCount = (int)contentPanel.getAnimals().stream().filter(animal -> animal instanceof Carnivore && !animal.isDead()).count();
        int herbivoreCount = (int)contentPanel.getAnimals().stream().filter(animal -> animal instanceof Herbivore && !animal.isDead()).count();
        int touristCount = director.getTouristInLastMonth();
        director.setTourist();
        
        return (carnivoreCount < minCarnivore) || (herbivoreCount < minHerbivore) || (touristCount < minTourist);
    }

    
    /**
     * Frissíti a turisták számát az állatok és növények száma, illetve a nehézség alapján.
     * @return a turisták száma
     */
    private int setTouristNumbers() {
        double result;
        
        int animalCount = this.contentPanel.getAnimals().size();
        int plantCount = this.contentPanel.getPlants().size();
        
        List<Route> routeComplete = this.contentPanel.getRoutes().stream().filter(rr -> rr.getComplete()).collect(Collectors.toList());
        
        if (routeComplete.size() < 1) {
            return 0;
        }
      
        List<Animal> sickAnimals = this.contentPanel.getAnimals().stream().filter(animal -> animal.isSick()).collect(Collectors.toList());
        int sickAnimalCount = sickAnimals.size();
        int levelCount=1;
        
        switch (gameLevel) {
            case (GameLevel.EASY)   -> levelCount = 1;
            case (GameLevel.MEDIUM) -> levelCount = 2;
            case (GameLevel.HARD)   -> levelCount = 3;
        }
        
        double entr = (entranceFee - 100) / 200;
        
        result = 0.2 * (animalCount + plantCount) * Math.exp(-0.3 * sickAnimalCount) * Math.exp(0.2 * levelCount) * (1 - 0.9 * entr);
        result = Math.min(20, Math.max(0, result));
        System.out.println(result);
        return (int) result; 
    }
    
    
    public Time getTime(){
        return time;
    }
     
    public GameSpeed getGameSpeed(){
        return gameSpeed;
    }
    
    public GameLevel getGameLevel() {
        return gameLevel;
    }
    
    public Point getScrollPosition(){
        return this.scrollPosition;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
         
    public int getTouristNumber(){
        return touristNumber;
    }
    
    public void changeTouristNumber(int delta) {
        touristNumber += delta;
    }
}
