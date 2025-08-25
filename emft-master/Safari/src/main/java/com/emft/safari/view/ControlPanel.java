package com.emft.safari.view;

import com.emft.safari.model.animals.Animal;
import com.emft.safari.model.animals.Elephant;
import com.emft.safari.model.animals.Giraffe;
import com.emft.safari.model.animals.Hyena;
import com.emft.safari.model.animals.Lion;
import com.emft.safari.model.animals.Hyena;

import com.emft.safari.model.buildables.Lake;
import com.emft.safari.model.people.*;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.buildables.Route;
import com.emft.safari.model.equipment.Airship;
import com.emft.safari.model.equipment.Camera;
import com.emft.safari.model.equipment.Drone;
import com.emft.safari.model.equipment.Jeep;

import com.emft.safari.model.equipment.MobileDevice;

import com.emft.safari.model.equipment.Station;


import com.emft.safari.model.plants.Plant;

import com.emft.safari.model.utilities.Age;
import com.emft.safari.view.ControlPanel.BuyRouteActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.View;


public class ControlPanel extends JPanel{

    private final JPanel infoPanel;
    private final SafariGUI parentFrame;
    private final Director director;
    private final ContentPanel contentPanel;
    private final SafariGameEngine gameEngine;

    private final List<Route> routes;

    private final JLabel infoLion;
    private final JLabel infoHyena;
    private final JLabel infoElephant;
    private final JLabel infoGiraffe;
    private final JLabel infoPlant;
    private final JLabel infoJeep;
    private final JLabel infoPeople;
    
    private final BuyAnimalActionListener buyAnimalActionListener;
    private final BuyPlantActionListener buyPlantActionListener;
    private final BuyLakeActionListener buyLakeActionListener;
    private final BuyPersonnelActionListener buyPersonnelActionListener;
    private final BuyJeepActionListener buyJeepActionListener;
    private final BuyRouteActionListener buyRouteActionListener;
    private  final BuyCameraActionListener buyCameraActionListener;
    private final BuyStationActionListener buyStationActionListener;
    private final BuyDroneActionListener buyDroneActionListener;
    private final BuyAirshipActionListener buyAirshipActionListener;
    
    private final SellAnimalActionListener sellAnimalActionListener;
    private final SellPlantActionListener sellPlantActionListener;
    private final SellLakeActionListener sellLakeActionListener;
    
    private final HuntPredatorActionListener huntPredatorActionListener;
    private final ChipAnimalActionListener chipAnimalActionListener;
    private final RemoveRouteActionListener removeRouteActionListener;
    private final ChangeFlightPathActionListener changeFlightPathActionListener;

    private boolean isBuying;
    private Position lastCursorPosition;
    
    private final JButton buyAnimalButton;
    private final JButton buyPlantButton;
    private final JButton buyLakeButton;
    private final JButton buyPersonnelButton;
    private final JButton buyJeepButton;
    private final JButton buyRouteButton;
    private final JButton buyCameraButton;
    private final JButton buyStationButton;
    private final JButton buyDroneButton;
    private final JButton buyAirshipButton;
    
    private final JButton sellAnimalButton;
    private final JButton sellPlantButton;
    private final JButton sellLakeButton;
    
    private final JButton huntPredatorButton;
    private final JButton chipAnimalButton;
    private final JButton changeFlightPathButton;   
    private final JButton removeRouteButton;
    
    Border buttonBorder = BorderFactory.createLineBorder(new Color(110, 94, 78), 3);
    Font buttonFont = new Font("Arial", Font.BOLD, 14);
    
    
    public ControlPanel(SafariGameEngine gameEngine, ContentPanel contentPanel, 
                        Director director, SafariGUI safariGUI) {
        
        this.parentFrame = safariGUI;
        this.gameEngine = gameEngine; 
        this.contentPanel = contentPanel;
        this.infoPanel = new JPanel();
        this.director = director;
        this.isBuying = false;
        this.routes = contentPanel.getRoutes();
        this.setLayout(new BoxLayout(this, View.X_AXIS));
        this.infoPanel.setPreferredSize(new Dimension(300,80));
        this.infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Infó panel részei:

        ImageIcon jeepIcon = new ImageIcon(getClass().getResource("/pictures/jeep_icon.png"));
        ImageIcon peopleIcon = new ImageIcon(getClass().getResource("/pictures/people_icon.png"));
       
        Image scaledJeep = jeepIcon.getImage().getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledJeepIcon = new ImageIcon(scaledJeep);
     
        Image scaledPeople = peopleIcon.getImage().getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledPeopleIcon = new ImageIcon(scaledPeople);

        infoJeep = new JLabel("", scaledJeepIcon, JLabel.LEFT);
        infoPeople=new JLabel("",scaledPeopleIcon, JLabel.LEFT );
   
        Image originalImage;
        Image scaledImage;
        
        originalImage = Animal.imageCache.get("Lion_MIDDLE_AGED");
        scaledImage = originalImage.getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        infoLion = new JLabel("", new ImageIcon(scaledImage), JLabel.LEFT);
        
        originalImage = Animal.imageCache.get("Hyena_MIDDLE_AGED");
        scaledImage = originalImage.getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        infoHyena = new JLabel("", new ImageIcon(scaledImage), JLabel.LEFT);
        
        originalImage = Animal.imageCache.get("Elephant_MIDDLE_AGED");
        scaledImage = originalImage.getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        infoElephant = new JLabel("", new ImageIcon(scaledImage), JLabel.LEFT);
        
        originalImage = Animal.imageCache.get("Giraffe_MIDDLE_AGED");
        scaledImage = originalImage.getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        infoGiraffe = new JLabel("", new ImageIcon(scaledImage), JLabel.LEFT);
        
        ImageIcon plantImage = new ImageIcon(getClass().getResource("/pictures/acacia_large.png"));
        scaledImage = plantImage.getImage().getScaledInstance(24, 32, Image.SCALE_SMOOTH);
        infoPlant = new JLabel("", new ImageIcon(scaledImage), JLabel.LEFT);
        
        infoPanel.add(infoJeep);
        infoPanel.add(infoPeople);
        infoPanel.add(infoLion);
        infoPanel.add(infoHyena);
        infoPanel.add(infoElephant);
        infoPanel.add(infoGiraffe);
        infoPanel.add(infoPlant);
        infoPanel.setBackground(new Color(144,132,110));
        this.add(infoPanel);
        
        
        Border buttonPadding;
        
        // Útvonal gombok panele:
        JPanel routeButtonPanel = new JPanel();
        routeButtonPanel.setLayout(new BoxLayout(routeButtonPanel, View.Y_AXIS));
        routeButtonPanel.setBackground(new Color(144,132,110));
      
        // Útvonal vásárlása:
        this.buyRouteButton = new JButton("Buy Route");
        this.buyRouteActionListener = new BuyRouteActionListener();
        buyRouteButton.setPreferredSize(new Dimension(140, 30));
        buyRouteButton.setForeground(Color.white);
        buyRouteButton.setBackground(new Color(159, 149, 140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 26, 12, 26);
        buyRouteButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyRouteButton.setFont(buttonFont);
        buyRouteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyRouteButton.setFocusable(false);
        routeButtonPanel.add(buyRouteButton); 
        
        routeButtonPanel.add(Box.createVerticalStrut(10));

        // Útvonal törlése:
        this.removeRouteButton = new JButton("Remove Route");
        this.removeRouteActionListener = new RemoveRouteActionListener();
        removeRouteButton.setPreferredSize(new Dimension(140, 30));
        removeRouteButton.setForeground(Color.white);
        removeRouteButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        removeRouteButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        removeRouteButton.setFont(buttonFont);
        removeRouteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeRouteButton.setFocusable(false);
        routeButtonPanel.add(removeRouteButton);
        
        this.add(routeButtonPanel);
        
        // Kamera és töltőállomás gombok panele:
        JPanel equipmentButtonPanel = new JPanel();
        equipmentButtonPanel.setLayout(new BoxLayout(equipmentButtonPanel, View.Y_AXIS));
        equipmentButtonPanel.setBackground(new Color(144,132,110));
        
        // Kamera vásárlása:
        this.buyCameraButton = new JButton("Buy Camera");
        this.buyCameraActionListener = new BuyCameraActionListener();
        buyCameraButton.setPreferredSize(new Dimension(110, 30));
        buyCameraButton.setForeground(Color.white);
        buyCameraButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyCameraButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyCameraButton.setFont(buttonFont);
        buyCameraButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyCameraButton.setFocusable(false);
        equipmentButtonPanel.add(buyCameraButton); 
        
        equipmentButtonPanel.add(Box.createVerticalStrut(10));
        
        // Töltőállomás vásárlása:
        this.buyStationButton = new JButton("Buy Station");
        this.buyStationActionListener = new BuyStationActionListener();
        buyStationButton.setPreferredSize(new Dimension(110, 30));
        buyStationButton.setForeground(Color.white);
        buyStationButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 12, 12, 12);
        buyStationButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyStationButton.setFont(buttonFont);
        buyStationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyStationButton.setFocusable(false);
        equipmentButtonPanel.add(buyStationButton); 
        
        this.add(equipmentButtonPanel);
        
        
        // Drón és léghaj gombok panele:
        JPanel mobileDeviceButtonPanel = new JPanel();
        mobileDeviceButtonPanel.setLayout(new BoxLayout(mobileDeviceButtonPanel, View.Y_AXIS));
        mobileDeviceButtonPanel.setBackground(new Color(144,132,110));
          
        // Drónok vásárlása:
        this.buyDroneButton = new JButton("Buy Drone");
        this.buyDroneActionListener = new BuyDroneActionListener();
        buyDroneButton.setPreferredSize(new Dimension(110, 30));
        buttonPadding = BorderFactory.createEmptyBorder(12, 13, 12, 13);
        buyDroneButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyDroneButton.setEnabled(false);
        buyDroneButton.setFont(buttonFont);
        buyDroneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyDroneButton.setFocusable(false);
        mobileDeviceButtonPanel.add(buyDroneButton); 
        
        mobileDeviceButtonPanel.add(Box.createVerticalStrut(10));
        
        // Léghajó vásárlása:
        this.buyAirshipButton = new JButton("Buy Airship");
        this.buyAirshipActionListener = new BuyAirshipActionListener();
        buyAirshipButton.setPreferredSize(new Dimension(110, 30));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyAirshipButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyAirshipButton.setEnabled(false);
        buyAirshipButton.setFont(buttonFont);
        buyAirshipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyAirshipButton.setFocusable(false);
        mobileDeviceButtonPanel.add(buyAirshipButton); 
        
        this.add(mobileDeviceButtonPanel);
       
   
        // Jeep és légi útvonal változtatás gombok panele:
        JPanel jeepButtonPanel = new JPanel();
        jeepButtonPanel.setLayout(new BoxLayout(jeepButtonPanel, View.Y_AXIS));
        jeepButtonPanel.setBackground(new Color(144,132,110));
        
        // Repülési útvonal módosítása:
        this.changeFlightPathButton = new JButton("Change Flight Path");
        this.changeFlightPathActionListener = new ChangeFlightPathActionListener();
        changeFlightPathButton.setPreferredSize(new Dimension(160, 30));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        changeFlightPathButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        changeFlightPathButton.setEnabled(false);
        changeFlightPathButton.setFont(buttonFont);
        changeFlightPathButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeFlightPathButton.setFocusable(false);
        jeepButtonPanel.add(changeFlightPathButton); 
        
        jeepButtonPanel.add(Box.createVerticalStrut(10));
        
        // Jeep vásárlása:
        this.buyJeepButton = new JButton("Buy Jeep");
        this.buyJeepActionListener = new BuyJeepActionListener();    
        buyJeepButton.setPreferredSize(new Dimension(160, 30));
        buttonPadding = BorderFactory.createEmptyBorder(12, 43, 12, 43);
        buyJeepButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyJeepButton.setFont(buttonFont);
        buyJeepButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyJeepButton.setFocusable(false);
        jeepButtonPanel.add(buyJeepButton);
        
        this.add(jeepButtonPanel);
        
        
        // Animal panel
        JPanel animalButtonPanel = new JPanel();
        animalButtonPanel.setLayout(new BoxLayout(animalButtonPanel, View.Y_AXIS));
        animalButtonPanel.setBackground(new Color(144,132,110));
        
        // Állat vásárlása:
        this.buyAnimalButton = new JButton("Buy Animal");
        this.buyAnimalActionListener = new BuyAnimalActionListener();
        buyAnimalButton.addActionListener(buyAnimalActionListener);
        buyAnimalButton.setPreferredSize(new Dimension(110, 30));
        buyAnimalButton.setForeground(Color.white);
        buyAnimalButton.setBackground(new Color(159, 149, 140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyAnimalButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyAnimalButton.setFont(buttonFont);
        buyAnimalButton.setFocusable(false);
        buyAnimalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        animalButtonPanel.add(buyAnimalButton);
        
        animalButtonPanel.add(Box.createVerticalStrut(10));
        
        // Állat eladása:
        this.sellAnimalButton = new JButton("Sell Animal");
        this.sellAnimalActionListener = new SellAnimalActionListener();
        sellAnimalButton.addActionListener(sellAnimalActionListener);
        sellAnimalButton.setPreferredSize(new Dimension(110, 30));
        sellAnimalButton.setForeground(Color.white);
        sellAnimalButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        sellAnimalButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        sellAnimalButton.setFont(buttonFont);
        sellAnimalButton.setFocusable(false);
        sellAnimalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        animalButtonPanel.add(sellAnimalButton);
        
        this.add(animalButtonPanel);
        
        
        // Plant panel
        JPanel plantButtonPanel = new JPanel();
        plantButtonPanel.setLayout(new BoxLayout(plantButtonPanel, View.Y_AXIS));
        plantButtonPanel.setBackground(new Color(144,132,110));
        
        // Növény vásárlása:
        this.buyPlantButton = new JButton("Buy Plant");
        this.buyPlantActionListener = new BuyPlantActionListener();
        buyPlantButton.addActionListener(buyPlantActionListener);
        buyPlantButton.setPreferredSize(new Dimension(100, 30));
        buyPlantButton.setForeground(Color.white);
        buyPlantButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyPlantButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyPlantButton.setFont(buttonFont);
        buyPlantButton.setFocusable(false);
        buyPlantButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plantButtonPanel.add(buyPlantButton);
        
        plantButtonPanel.add(Box.createVerticalStrut(10));
        
        // Növény eladása:
        this.sellPlantButton = new JButton("Sell Plant");
        this.sellPlantActionListener = new SellPlantActionListener();
        sellPlantButton.addActionListener(sellPlantActionListener);
        sellPlantButton.setPreferredSize(new Dimension(100, 30));
        sellPlantButton.setForeground(Color.white);
        sellPlantButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        sellPlantButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        sellPlantButton.setFont(buttonFont);
        sellPlantButton.setFocusable(false);
        sellPlantButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plantButtonPanel.add(sellPlantButton);
        
        this.add(plantButtonPanel);
        
        
        // Lake panel
        JPanel lakeButtonPanel = new JPanel();
        lakeButtonPanel.setLayout(new BoxLayout(lakeButtonPanel, View.Y_AXIS));
        lakeButtonPanel.setBackground(new Color(144,132,110));
        
        // Tó vásárlása:
        this.buyLakeButton = new JButton("Buy Lake");
        this.buyLakeActionListener = new BuyLakeActionListener();
        buyLakeButton.addActionListener(buyLakeActionListener);
        buyLakeButton.setPreferredSize(new Dimension(95, 30));
        buyLakeButton.setForeground(Color.white);
        buyLakeButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyLakeButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyLakeButton.setFont(buttonFont);
        buyLakeButton.setFocusable(false);
        buyLakeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lakeButtonPanel.add(buyLakeButton);
        
        lakeButtonPanel.add(Box.createVerticalStrut(10));
        
        // Tó eladása:
        this.sellLakeButton = new JButton("Sell Lake");
        this.sellLakeActionListener = new SellLakeActionListener();
        sellLakeButton.addActionListener(sellLakeActionListener);
        sellLakeButton.setPreferredSize(new Dimension(95, 30));
        sellLakeButton.setForeground(Color.white);
        sellLakeButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        sellLakeButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        sellLakeButton.setFont(buttonFont);
        sellLakeButton.setFocusable(false);
        sellLakeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lakeButtonPanel.add(sellLakeButton);
        
        this.add(lakeButtonPanel);
        
        
        // Person panel
        JPanel personButtonPanel = new JPanel();
        personButtonPanel.setLayout(new BoxLayout(personButtonPanel, View.Y_AXIS));
        personButtonPanel.setBackground(new Color(144,132,110));
        
        // Személyzet vásárlása (orvos/vadőr):
        this.buyPersonnelButton = new JButton("Buy Personnel");
        this.buyPersonnelActionListener = new BuyPersonnelActionListener();
        buyPersonnelButton.addActionListener(buyPersonnelActionListener);
        buyPersonnelButton.setPreferredSize(new Dimension(130, 30));
        buyPersonnelButton.setForeground(Color.white);
        buyPersonnelButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        buyPersonnelButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        buyPersonnelButton.setFont(buttonFont);
        buyPersonnelButton.setFocusable(false);
        buyPersonnelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        personButtonPanel.add(buyPersonnelButton);
        
        personButtonPanel.add(Box.createVerticalStrut(10));
        
        // Ragadozó levadászása (vadőrrel):
        this.huntPredatorButton = new JButton("Hunt Predator");
        this.huntPredatorActionListener = new HuntPredatorActionListener();
        huntPredatorButton.addActionListener(huntPredatorActionListener);
        huntPredatorButton.setPreferredSize(new Dimension(130, 30));
        buttonPadding = BorderFactory.createEmptyBorder(12, 12, 12, 12);
        huntPredatorButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        huntPredatorButton.setFont(buttonFont);
        huntPredatorButton.setFocusable(false);
        huntPredatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        personButtonPanel.add(huntPredatorButton);
        
        this.add(personButtonPanel);
        
        // Állat chippelése:
        this.chipAnimalButton = new JButton("Buy Chip");
        this.chipAnimalActionListener = new ChipAnimalActionListener();
        chipAnimalButton.addActionListener(chipAnimalActionListener);
        chipAnimalButton.setPreferredSize(new Dimension(100, 30));
        chipAnimalButton.setForeground(Color.white);
        chipAnimalButton.setBackground(new Color(159,149,140));
        buttonPadding = BorderFactory.createEmptyBorder(12, 10, 12, 10);
        chipAnimalButton.setBorder(BorderFactory.createCompoundBorder(buttonBorder, buttonPadding));
        chipAnimalButton.setFont(buttonFont);
        chipAnimalButton.setFocusable(false);
        this.add(chipAnimalButton);

        buyAirshipButton.addActionListener(buyAirshipActionListener);
        buyDroneButton.addActionListener(buyDroneActionListener);
        buyCameraButton.addActionListener(buyCameraActionListener);
        buyStationButton.addActionListener(buyStationActionListener); 
        
        buyRouteButton.addActionListener(buyRouteActionListener); 
        buyJeepButton.addActionListener(buyJeepActionListener); 
        removeRouteButton.addActionListener(removeRouteActionListener); 
        changeFlightPathButton.addActionListener(changeFlightPathActionListener);
    }
    
    
    //changeFlightPath megvalositasa
     class ChangeFlightPathActionListener implements ActionListener{
        private  CompletableFuture<Boolean> currentInteraction;
        private  final MessageBox messageBox;
        public MouseListener mouseChangePathListener;
        Cursor dotCursor;
        private Station clickedStation;
        private Position centerCircle;
        private MobileDevice selectedDevice;

        public ChangeFlightPathActionListener(){
            this.messageBox= new MessageBox(gameEngine.getScrollPosition());
            messageBox.setResponseHandler(this::userResponds);
            contentPanel.add(messageBox);
            contentPanel.addMessBox(this.messageBox);
            clickedStation=null;
            centerCircle=null;
            selectedDevice=null;

            BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setColor(Color.RED);  // 
            g2d.fillOval(11, 11, 10, 10);    // 
            g2d.dispose();
            dotCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,
                new Point(16, 16), // A hotspot (kurzor aktív pontja) középen legyen
                "Dot Cursor"
             );                    
        };
       
        @Override
        public void actionPerformed(ActionEvent e) {
          
          this.mouseChangePathListener=new  MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Az egérgomb (bal egergomb) lenyomásának eseménye
                    if (SwingUtilities.isLeftMouseButton(e)) {
                         
                        Position pos=new Position(e.getX(), e.getY());
                        if (contentPanel.thereIsMobileDevice(pos)!=null){
                            if (selectedDevice!=null){
                               selectedDevice.setIsSelected(false);
                           }
                            selectedDevice=contentPanel.thereIsMobileDevice(pos);
                            selectedDevice.setIsSelected(true);
                            return;
                        } 
                            
                            if (contentPanel.thereIsStation(pos)!=null){
                                if (clickedStation!=null){
                                    clickedStation.setMark(false);
                                }
                                clickedStation=contentPanel.thereIsStation(pos);
                                contentPanel.setCursor(dotCursor);
                                clickedStation.setMark(true);
                                return;
                            } 
                            
                            if( clickedStation!=null){
                                if (centerCircle==null){
                                    centerCircle=pos;
                                    contentPanel.buyingDeviceCenterCircle=pos;
                                    return;
                                }else {
                                    contentPanel.buyingDevicePointCircle=pos;
                                    if(director.getMoney()< Drone.PRICE)
                                        messageBox.setMessage("Nincs elegendő pénze!!!");
                                    else{
                                        messageBox.setMessage("Az Airship ára:"+Integer.toString(Airship.PRICE));
                                        messageBox.setDisplay(true);  
                                        messageBox.setVisible(true);
                                        startInteraction( new Position (centerCircle.getX(),centerCircle.getY()),
                                            clickedStation, pos, selectedDevice);
                                        centerCircle=null;
                                        clickedStation=null;
                                        selectedDevice=null;
                                       // contentPanel.revalidate();
                                }     
                            }      
                        }
                    }                  
                    
                    //jobb gomb lenyomasanak eseménye
                    if (SwingUtilities.isRightMouseButton(e)) { 
                        if(selectedDevice!=null)
                            selectedDevice.setIsSelected(false);
                        if(clickedStation!=null)
                            clickedStation.setMark(false);
                        removeAllListeners();
    
                    }
                }
         
            };
            addAllMyListener();
            contentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
         private void startInteraction(Position center, Station clStation, Position start, MobileDevice mobDevice) {
          
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            
            currentInteraction = new CompletableFuture<>();
            SwingUtilities.invokeLater(() -> {
            
                messageBox.setPosition(600, messageBox.getScrollPosition().y +200);
                messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),300,100);
                messageBox.setOpaque(true);
                messageBox.setDisplay(true);
                messageBox.setVisible(true);
            });

            currentInteraction.thenAccept(answer -> {
                if (answer) {
                    mobDevice.changeFlyPath(center,clStation,start);
                  
                    clStation.setMark(false);
                    
                  
                } 
            contentPanel.buyingDeviceCenterCircle=null;
            contentPanel.buyingDevicePointCircle=null;
           
            messageBox.setPosition(0, 0);
            messageBox.setDisplay(false);
            messageBox.setVisible(false);
            
             }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                    removeAllListeners();  // mindig leszedjük a listenereket
                });
            });
        }
         
        public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
            }
        }
        
        public void addAllMyListener(){
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
             }
             contentPanel.addMouseListener(mouseChangePathListener);
        }
        
        private void removeAllListeners() {
           contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
        }
    }
    
    
    
    
    //Airship vasarlasa
    class BuyAirshipActionListener implements ActionListener{
        private  CompletableFuture<Boolean> currentInteraction;
        private  final MessageBox messageBox;
        public MouseListener mouseAirshipListener;
        Cursor dotCursor;
        private Station clickedStation;
        private Position centerCircle;

        public BuyAirshipActionListener(){
            this.messageBox= new MessageBox(gameEngine.getScrollPosition());
            messageBox.setResponseHandler(this::userResponds);
            contentPanel.add(messageBox);
            contentPanel.addMessBox(this.messageBox);
            clickedStation=null;
            centerCircle=null;

            BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setColor(Color.RED);
            g2d.fillOval(11, 11, 10, 10);
            g2d.dispose();
            dotCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,
                new Point(16, 16), // A hotspot (kurzor aktív pontja) középen legyen
                "Dot Cursor"
            );                    
        };
       
        @Override
        public void actionPerformed(ActionEvent e) {
          
          this.mouseAirshipListener=new  MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Az egérgomb (bal egergomb) lenyomásának eseménye
                    if (SwingUtilities.isLeftMouseButton(e)) {
                         
                        Position pos=new Position(e.getX(), e.getY());
                            if (contentPanel.thereIsStation(pos)!=null){
                                if (clickedStation!=null){
                                    clickedStation.setMark(false);
                                }
                                clickedStation=contentPanel.thereIsStation(pos);
                                contentPanel.setCursor(dotCursor);
                                clickedStation.setMark(true);
                                return;
                            } else {
                            
                            }
                            
                            if( clickedStation!=null){
                                if (centerCircle==null){
                                    centerCircle=pos;
                                    contentPanel.buyingDeviceCenterCircle=pos;
                                    return;
                                }else {
                                    contentPanel.buyingDevicePointCircle=pos;
                                    if(director.getMoney()< Drone.PRICE)
                                        messageBox.setMessage("Nincs elegendő pénze!!!");
                                    else{
                                        messageBox.setMessage("Az Airship ára:"+Integer.toString(Airship.PRICE));
                                        messageBox.setDisplay(true);  
                                        messageBox.setVisible(true);
                                        startInteraction( new Position (centerCircle.getX(),centerCircle.getY()),
                                            clickedStation, pos);
                                        centerCircle=null;
                                        clickedStation=null;
                                       // contentPanel.revalidate();
                                }     
                            }      
                        }
                    }                  
                    
                    //jobb gomb lenyomasanak eseménye
                    if (SwingUtilities.isRightMouseButton(e)) { 
                         if(clickedStation!=null)
                            clickedStation.setMark(false);
                        removeAllListeners();
    
                    }
                }
         
            };
            addAllMyListener();
            contentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
         private void startInteraction(Position center, Station clStation, Position start) {
          
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            
            currentInteraction = new CompletableFuture<>();
            SwingUtilities.invokeLater(() -> {
            
                messageBox.setPosition(600, messageBox.getScrollPosition().y +200);
                messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),300,100);
                messageBox.setOpaque(true);
                messageBox.setDisplay(true);
                messageBox.setVisible(true);
            });

            currentInteraction.thenAccept(answer -> {
                if (answer) {
                    contentPanel.buyAirship(center,clStation,start);
                    clStation.setMark(false);
                  
                } 
            contentPanel.buyingDeviceCenterCircle=null;
            contentPanel.buyingDevicePointCircle=null;
           
            messageBox.setPosition(0, 0);
            messageBox.setDisplay(false);
            messageBox.setVisible(false);
            
             }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                    removeAllListeners();  // mindig leszedjük a listenereket
                });
            });
        }
         
        public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
            }
        }
        
        public void addAllMyListener(){
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
             }
             contentPanel.addMouseListener(mouseAirshipListener);
        }
        
        private void removeAllListeners() {
           contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
        }
    }
    
    
    
    ///A Dron megvasarlasa
    class BuyDroneActionListener implements ActionListener{
        private  CompletableFuture<Boolean> currentInteraction;
        private  final MessageBox messageBox;
        public MouseListener mouseDroneListener;
        Cursor dotCursor;
        private Station clickedStation;
        private Position centerCircle;

        public BuyDroneActionListener(){
             this.messageBox= new MessageBox(gameEngine.getScrollPosition());
             messageBox.setResponseHandler(this::userResponds);
             contentPanel.add(messageBox);
             contentPanel.revalidate();
             contentPanel.addMessBox(this.messageBox);
             clickedStation=null;
             centerCircle=null;

            BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setColor(Color.RED);  // 
            g2d.fillOval(11, 11, 10, 10);    // 
            g2d.dispose();
            dotCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,
                new Point(16, 16), // A hotspot (kurzor aktív pontja) középen legyen
                "Dot Cursor"
             );                    
        };
       
        @Override
        public void actionPerformed(ActionEvent e) {
          
          this.mouseDroneListener=new  MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Az egérgomb (bal egergomb) lenyomásának eseménye
                    if (SwingUtilities.isLeftMouseButton(e)) {
                         
                        Position pos=new Position(e.getX(), e.getY());
                            if (contentPanel.thereIsStation(pos)!=null){
                                if (clickedStation!=null){
                                    clickedStation.setMark(false);
                                }
                                clickedStation=contentPanel.thereIsStation(pos);
                                contentPanel.setCursor(dotCursor);
                                clickedStation.setMark(true);
                                return;
                            } else {
                            
                            }
                            
                            if( clickedStation!=null){
                                if (centerCircle==null){
                                    centerCircle=pos;
                                    contentPanel.buyingDeviceCenterCircle=pos;
                                    return;
                                }else {
                                    contentPanel.buyingDevicePointCircle=pos;
                                    if(director.getMoney()< Drone.PRICE)
                                        messageBox.setMessage("Nincs elegendő pénze!!!");
                                    else{
                                        messageBox.setMessage("Az Drone ára:"+Integer.toString(Drone.PRICE));
                                        messageBox.setDisplay(true);  
                                        messageBox.setVisible(true);
                                        startInteraction( new Position (centerCircle.getX(),centerCircle.getY()),
                                            clickedStation, pos);
                                        centerCircle=null;
                                        clickedStation=null;
                                       // contentPanel.revalidate();
                                }     
                            }      
                        }
                    }                  
                    
                    //jobb gomb lenyomasanak eseménye
                    if (SwingUtilities.isRightMouseButton(e)) { 
                          if(clickedStation!=null)
                            clickedStation.setMark(false);
                        removeAllListeners();
    
                    }
                }
         
            };
            addAllMyListener();
            contentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
         private void startInteraction(Position center, Station clStation, Position start) {
          
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            
            currentInteraction = new CompletableFuture<>();
            SwingUtilities.invokeLater(() -> {
            
                messageBox.setPosition(600, messageBox.getScrollPosition().y +200);
                messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),300,100);
                messageBox.setOpaque(true);
                messageBox.setDisplay(true);
                messageBox.setVisible(true);
            });

            currentInteraction.thenAccept(answer -> {
                if (answer) {
                    contentPanel.buyDrone(center,clStation,start);
                    clStation.setMark(false);
                  
                } 
            contentPanel.buyingDeviceCenterCircle=null;
            contentPanel.buyingDevicePointCircle=null;
           
            messageBox.setPosition(0, 0);
            messageBox.setDisplay(false);
            messageBox.setVisible(false);
            
             }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                    removeAllListeners();  // mindig leszedjük a listenereket
                });
            });
        }
         
        public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
            }
        }
        
        public void addAllMyListener(){
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
             }
             contentPanel.addMouseListener(mouseDroneListener);
        }
        
        private void removeAllListeners() {
           contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
        }
    }
    
    //Az allomas megvasarlasa
    class BuyStationActionListener implements ActionListener{
        private  CompletableFuture<Boolean> currentInteraction;
        private final MessageBox messageBox;
        public MouseListener mouseStationListener;
        Cursor dotCursor;
        
        public BuyStationActionListener(){
             this.messageBox= new MessageBox(gameEngine.getScrollPosition());
             messageBox.setResponseHandler(this::userResponds);
             contentPanel.add(messageBox);
             contentPanel.revalidate();
             contentPanel.addMessBox(this.messageBox);
             
            BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setColor(Color.RED);  // 
            g2d.fillOval(11, 11, 10, 10);    // 
            g2d.dispose();
            dotCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,
                new Point(16, 16), // A hotspot (kurzor aktív pontja) középen legyen
                "Dot Cursor"
             );
           
        };
       
     
        @Override
        public void actionPerformed(ActionEvent e) {
            
            this.mouseStationListener=new  MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Az egérgomb (bal egergomb) lenyomásának eseménye
                    if (SwingUtilities.isLeftMouseButton(e)) {
                                  
                        Position pos=new Position(e.getX(), e.getY());
                        if (contentPanel.isSubject(pos)){
                             messageBox.setMessage("Ide nem helyezhető le a ÁLLOMÁS!!!");
                        } else if(director.getMoney()<Station.PRICE){
                            messageBox.setMessage("Nincs elegendő pénze!!!");
                        } else{
                            messageBox.setMessage("Az ÁLLOMÁS ára:"+Integer.toString(Station.PRICE));
                        }
                        messageBox.setDisplay(true);
                        messageBox.setVisible(true);
                        startInteraction(pos);  
                    }                     
                    
                    //jobb gomb lenyomasanak eseménye
                    if (SwingUtilities.isRightMouseButton(e)) {
                      //  System.out.println("Jobb kattintasra bejon");
                         removeAllMyListeners();
                    }
                }
            };
            addAllMyListener();
            contentPanel.setCursor(dotCursor);
        }
        
         private void startInteraction(Position pos) {
          
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            
            currentInteraction = new CompletableFuture<>();
            SwingUtilities.invokeLater(() -> {
            
                messageBox.setPosition(600, messageBox.getScrollPosition().y +200);
                messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),300,100);

                messageBox.setOpaque(true);
                messageBox.setDisplay(true);
                messageBox.setVisible(true);
            });
            currentInteraction.thenAccept(answer -> {
                if (answer) {
                    contentPanel.buyStation(pos);
                }
            }).whenComplete((res, ex) -> {
                    contentPanel.revalidate();
                    removeAllMyListeners();  // mindig leszedjük a listenereket
            });
        }
         
        public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
            }
        }
        
        public void addAllMyListener(){
            for (MouseListener listener : contentPanel.getMouseListeners()) {
               contentPanel.removeMouseListener(listener);
            }
            contentPanel.addMouseListener(mouseStationListener);
        }

        
        private void removeAllMyListeners() {
            contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
        }
    }
    
    
    
    //a camera vasarlas listener
   class BuyCameraActionListener implements ActionListener {
        private CompletableFuture<Boolean> currentInteraction;
        private final MessageBox messageBox;
        private MouseListener mouseCameraListener;
        private Cursor dotCursor;

    public BuyCameraActionListener() {
        this.messageBox = new MessageBox(gameEngine.getScrollPosition());
        messageBox.setResponseHandler(this::userResponds);
        contentPanel.add(messageBox);
        contentPanel.addMessBox(this.messageBox);

        // Create custom cursor
        BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cursorImg.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillOval(11, 11, 10, 10);
        g2d.dispose();

        dotCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,
                new Point(16, 16),
                "Dot Cursor"
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        // Create mouse listener
        this.mouseCameraListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Position pos = new Position(e.getX(), e.getY());

                    if (contentPanel.isSubject(pos)) {
                        messageBox.setMessage("Ide nem helyezhető le a kamera!");
                    } else if (director.getMoney() < Camera.Price) {
                        messageBox.setMessage("Nincs elegendő pénze");
                    } else {
                        messageBox.setMessage("A kamera ára: " + Camera.Price);
                    }
                    messageBox.setDisplay(true);
                    messageBox.setVisible(true);
                  
                    startInteraction(pos);  // Start the interaction
                }

                // Right mouse button pressed, remove listeners
                if (SwingUtilities.isRightMouseButton(e)) {
                    removeAllMyListeners();
                }
            }
        };
        contentPanel.setCursor(dotCursor); // Set cursor when action is triggered
        addAllMyListener();
        
    }

    private void startInteraction(Position pos) {
        if (currentInteraction != null && !currentInteraction.isDone()) {
            currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
        }

        currentInteraction = new CompletableFuture<>();
        SwingUtilities.invokeLater(() -> {
            messageBox.setPosition(600, messageBox.getScrollPosition().y + 200);
            messageBox.setBounds(messageBox.getPosition().getX(), messageBox.getPosition().getY(), 300, 100);

            messageBox.setOpaque(true);
            messageBox.setDisplay(true);
            messageBox.setVisible(true);
        });

        // Handle the interaction completion
        currentInteraction.thenAccept(answer -> {
            if (answer) {
                contentPanel.buyCamera(pos);
            }
            
            }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                    removeAllMyListeners();  // mindig leszedjük a listenereket
                });
            });
          
    }

    public void userResponds(boolean answer) {
        if (currentInteraction != null && !currentInteraction.isDone()) {
            currentInteraction.complete(answer);
        }
    }

    public void addAllMyListener() {
        for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
        contentPanel.addMouseListener(mouseCameraListener);
    }

    private void removeAllMyListeners() {
        contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        for (MouseListener listener : contentPanel.getMouseListeners()) {
            contentPanel.removeMouseListener(listener);
       }
    }
}

    
    //Ez a jeep vasarlasa
    class BuyJeepActionListener implements ActionListener{

        private  CompletableFuture<Boolean> currentInteraction;
        private final MessageBox messageBox;
        
        public BuyJeepActionListener(){

             this.messageBox= new MessageBox(gameEngine.getScrollPosition());
             messageBox.setResponseHandler(this::userResponds);
             contentPanel.add(messageBox);
             contentPanel.addMessBox(this.messageBox);         

        };
       
        @Override
        public void actionPerformed(ActionEvent e) {
            startInteraction();            
           
        }
        
        private void startInteraction() {
          
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            
            currentInteraction = new CompletableFuture<>();
            SwingUtilities.invokeLater(() -> {
            
            messageBox.setPosition(600, messageBox.getScrollPosition().y +200);
            messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),300,100);
            
            if (Jeep.PRICE >director.getMoney()){
                 messageBox.setMessage("Önnek nincs elegendő pénze!");
                 messageBox.setEnabledYesButton(false);
            }
            else{
                messageBox.setMessage("A Jeep ára: "+Jeep.PRICE+" $");
                messageBox.setEnabledYesButton(true);
            }
         
            messageBox.setOpaque(true);
            messageBox.setDisplay(true);
        });

        currentInteraction.thenAccept(answer -> {
            if (answer) {
                contentPanel.buyJeep();
            } 
             }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                });
            });
            
        }
         
        public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
            }
        }
        

    }
    
    
    class BuyAnimalActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isBuying = true;
            
            String[] animalType = {""};
            
            // Az AnimalChoiceDialog-ból kiderül az állatfajta:
            animalType[0] = openAnimalChoiceDialog();
            
            switch (animalType[0]) {
                case "Lion" -> director.spendMoney(40);
                case "Hyena" -> director.spendMoney(30);
                case "Elephant" -> director.spendMoney(50);
                case "Giraffe" -> director.spendMoney(40);
                default -> throw new IllegalArgumentException("Buying - Unknown animal type: " + animalType[0]);
            }
 
            //get the position of the mouse after click
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
                //System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                
                boolean valid = true;
                
                // Objektumok közötti távolságok ellenőrzése:
                for (Animal animal : contentPanel.getAnimals()) {
                    if (mousePos.distance(animal.getPosition()) < 20) {
                        valid = false;
                        break;
                    }
                }

                for (Plant plant : contentPanel.getPlants()) {
                    if (mousePos.distance(plant.getPosition()) < 20) {
                        valid = false;
                        break;
                    }
                }

                for (Lake lake : contentPanel.getLakes()) {
                    if (mousePos.distance(lake.getPosition()) < 30) {
                        valid = false;
                        break;
                    }
                }
                
                if(valid) {
                  //  System.out.println("Buying " + animalType[0]);
                    contentPanel.addAnimal(animalType[0], Age.YOUNG, mousePos);
                    switchBuyButtons();
                }
                
            });

            
            isBuying = false;
        } 
    }
    
    class SellAnimalActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isBuying = false;
            String[] animalType = {""};
            animalType[0] = openAnimalChoiceDialog();
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
             //   System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                contentPanel.removeAnimal(mousePos, animalType[0]);
                switchBuyButtons();
            });
        } 
    }
    
    
    public String openAnimalChoiceDialog() {
        String[] result = {null};
        
        //JDialog
        JDialog dialog = new JDialog(parentFrame, "Choose animal type", true); 
        //dialog.setLocation(position);
        dialog.setSize(200, 200);
        dialog.setLocationRelativeTo(null);
        
        //JPanel a gomboknak
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        
        //Gombok
        JButton lionChoiceButton = new JButton();
        JButton hyenaChoiceButton = new JButton();
        JButton elephantChoiceButton = new JButton();
        JButton giraffeChoiceButton = new JButton();
        
        lionChoiceButton.setFocusable(false);
        hyenaChoiceButton.setFocusable(false);
        elephantChoiceButton.setFocusable(false);
        giraffeChoiceButton.setFocusable(false);
        
        //Gombok kepei 
        ImageIcon lionIcon = new ImageIcon("src/main/resources/pictures/lion_middle_aged.png");
        lionChoiceButton.setIcon(lionIcon);
        ImageIcon hyenaIcon = new ImageIcon("src/main/resources/pictures/hyena_middle_aged.png");
        hyenaChoiceButton.setIcon(hyenaIcon);
        ImageIcon elephantIcon = new ImageIcon("src/main/resources/pictures/elephant_middle_aged.png");
        elephantChoiceButton.setIcon(elephantIcon);
        ImageIcon giraffeIcon = new ImageIcon("src/main/resources/pictures/giraffe_middle_aged.png");
        giraffeChoiceButton.setIcon(giraffeIcon);
        
        //ActionListenerek
        lionChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Lion";
                dialog.dispose(); 
            }
        });
        
        hyenaChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Hyena";
                dialog.dispose(); 
            }
        });
        
        elephantChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Elephant";
                dialog.dispose(); 
            }
        });
        
        giraffeChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Giraffe";
                dialog.dispose(); 
            }
        });
        
        if (isBuying) {
            if (director.getMoney() < 50) {
                elephantChoiceButton.setEnabled(false);
            }
            if (director.getMoney() < 40) {
                lionChoiceButton.setEnabled(false);
                giraffeChoiceButton.setEnabled(false);
            }
            if (director.getMoney() < 30) {
                hyenaChoiceButton.setEnabled(false);
            }
        }
        
        panel.add(lionChoiceButton);
        panel.add(hyenaChoiceButton);
        panel.add(elephantChoiceButton);
        panel.add(giraffeChoiceButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    
    class BuyPlantActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isBuying = true;
            
            String[] plantType = {""};
            plantType[0] = openPlantChoiceDialog();
            
            switch (plantType[0]) {
                case "Acacia" -> director.spendMoney(30);
                case "BaobabTree" -> director.spendMoney(30);
                case "ElephantGrass" -> director.spendMoney(10);
                default -> throw new IllegalArgumentException("Buying - Unknown plant type: " + plantType[0]);
            }


            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
                System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                
                boolean valid = true;
                
                // Objektumok közötti távolságok ellenőrzése:
                for (Animal animal : contentPanel.getAnimals()) {
                    if (mousePos.distance(animal.getPosition()) < 20) {
                        valid = false;
                        break;
                    }
                }

                for (Plant plant : contentPanel.getPlants()) {
                    if (mousePos.distance(plant.getPosition()) < 20) {
                        valid = false;
                        break;
                    }
                }

                for (Lake lake : contentPanel.getLakes()) {
                    if (mousePos.distance(lake.getPosition()) < 30) {
                        valid = false;
                        break;
                    }
                }
                
                if (valid) {
                    System.out.println("Buying " + plantType[0]);
                    contentPanel.addPlant(plantType[0], mousePos);
                    switchBuyButtons();
                }
            });

            isBuying = false;
        } 
    }
    
    class SellPlantActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isBuying = false;
            String[] plantType = {""};
            plantType[0] = openPlantChoiceDialog();
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());       
                contentPanel.removePlant(mousePos, plantType[0]);
                switchBuyButtons();
            });
        } 
    }
    
    
    public String openPlantChoiceDialog() {
        String[] result = {null};
        
        //JDialog
        JDialog dialog = new JDialog(parentFrame, "Choose plant type", true);
        //dialog.setLocation(position);
        dialog.setSize(230, 80);
        dialog.setLocationRelativeTo(null);
        
        //JPanel a gomboknak
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        //Gombok
        JButton acaciaChoiceButton = new JButton();
        JButton baobabChoiceButton = new JButton();
        JButton elephantGrassChoiceButton = new JButton();
        
        acaciaChoiceButton.setFocusable(false);
        baobabChoiceButton.setFocusable(false);
        elephantGrassChoiceButton.setFocusable(false);
        
        //Gombok kepei 
        ImageIcon acaciaIcon = new ImageIcon("src/main/resources/pictures/acacia_medium.png");
        Image acaciaImage = acaciaIcon.getImage();
        Image acaciaScaledImage = acaciaImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon acaciaScaledIcon = new ImageIcon(acaciaScaledImage);
        acaciaChoiceButton.setIcon(acaciaScaledIcon);
        
        ImageIcon baobabIcon = new ImageIcon("src/main/resources/pictures/baobabtree_medium.png");
        Image baobabImage = baobabIcon.getImage();
        Image baobabScaledImage = baobabImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon baobabScaledIcon = new ImageIcon(baobabScaledImage);
        baobabChoiceButton.setIcon(baobabScaledIcon);
        
        ImageIcon elephantGrassIcon = new ImageIcon("src/main/resources/pictures/elephantgrass_medium.png");
        Image elephantGrassImage = elephantGrassIcon.getImage();
        Image elephantGrassScaledImage = elephantGrassImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon elephantGrassScaledIcon = new ImageIcon(elephantGrassScaledImage);
        elephantGrassChoiceButton.setIcon(elephantGrassScaledIcon);
        
        //ActionListenerek
        acaciaChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Acacia";
                dialog.dispose(); 
            }
        });
        
        baobabChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "BaobabTree";
                dialog.dispose(); 
            }
        });
        
        elephantGrassChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "ElephantGrass";
                dialog.dispose(); 
            }
        });
        
        if (isBuying) {
            if (director.getMoney() < 30) {
                acaciaChoiceButton.setEnabled(false);
                baobabChoiceButton.setEnabled(false);
            }
            if (director.getMoney() < 10) {
                elephantGrassChoiceButton.setEnabled(false);
            }
        }
        
        panel.add(acaciaChoiceButton);
        panel.add(baobabChoiceButton);
        panel.add(elephantGrassChoiceButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    
    class BuyLakeActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
                System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                
                boolean valid = true;
                
                // Objektumok közötti távolságok ellenőrzése:
                for (Animal animal : contentPanel.getAnimals()) {
                    if (mousePos.distance(animal.getPosition()) < 70) {
                        valid = false;
                        break;
                    }
                }

                for (Plant plant : contentPanel.getPlants()) {
                    if (mousePos.distance(plant.getPosition()) < 180) {
                        valid = false;
                        break;
                    }
                }

                for (Lake lake : contentPanel.getLakes()) {
                    if (mousePos.distance(lake.getPosition()) < 170) {
                        valid = false;
                        break;
                    }
                }
                
                if (valid) {
                    contentPanel.addLake(mousePos);
                    director.spendMoney(100);
                    switchBuyButtons();
                }
            });
        } 
    }
    
    
    class SellLakeActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
              //  System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                contentPanel.removeLake(mousePos);
                switchBuyButtons();
            });
        } 
    }
    
    
    // Konkurrensen lekéri a következő kattintás pozícióját a megadott panelből
    private CompletableFuture<Point> waitForClick(JPanel contentPanel) {
        CompletableFuture<Point> future = new CompletableFuture<>();
        
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.removeMouseListener(this);
                future.complete(e.getPoint());
            }
        };
        
        contentPanel.addMouseListener(adapter);
        return future;
    }
    
    
    private void switchBuyButtons() {
        if (director.getMoney() < 30) {
            buyAnimalButton.setEnabled(false);
        }
        else {
            buyAnimalButton.setEnabled(true);
        }
        
        if (director.getMoney() < 10) {
            buyPlantButton.setEnabled(false);
            chipAnimalButton.setEnabled(false);
        }
        else {
            buyPlantButton.setEnabled(true);
            chipAnimalButton.setEnabled(true);
        }
        
        if (director.getMoney() < 100) {
            buyLakeButton.setEnabled(false);
        }
        else {
            buyLakeButton.setEnabled(true);
        }
        
        if (director.getMoney() < 80) {
            buyPersonnelButton.setEnabled(false);
        }
        else {
            buyPersonnelButton.setEnabled(true);
        }
    }
    
    // Személyzet (Orvos, Vadőr) vásárlása
    class BuyPersonnelActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent e) {
            isBuying = true;
            String personnelType = "";
            
            // Az AnimalChoiceDialog-ból kiderül az állatfajta:
            personnelType = openPersonnelChoiceDialog();
            
            switch (personnelType) {
                case "Vet" -> {
                    director.spendMoney(80);
                    contentPanel.addVet(contentPanel.getUniquePosition());
                }
                case "Ranger" -> {
                    director.spendMoney(100);
                    contentPanel.addRanger(contentPanel.getUniquePosition());
                }
                default -> throw new IllegalArgumentException("Buying - Unknown personnel type: " + personnelType);
            }
            
            switchBuyButtons();
           // System.out.println("Buying " + personnelType);
            isBuying = false;
        } 
    }
    
    
    // Személyzet (Orvos, Vadőr) választása
    public String openPersonnelChoiceDialog() {
        String[] result = {null};
        
        // JDialog:
        JDialog dialog = new JDialog(parentFrame, "Choose personnel type", true);
        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(null);
        
        // JPanel a gomboknak:
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        
        // Gombok:
        JButton vetChoiceButton = new JButton();
        JButton rangerChoiceButton = new JButton();
        
        vetChoiceButton.setFocusable(false);
        rangerChoiceButton.setFocusable(false);
       
        
        // Gombok képei:
        ImageIcon vetIcon = new ImageIcon("src/main/resources/pictures/vet_buy.png");

        Image scaledImage = vetIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        vetChoiceButton.setIcon(new ImageIcon(scaledImage));

        ImageIcon rangerIcon = new ImageIcon("src/main/resources/pictures/ranger_buy.png");
        
        Image scaledRangerIcon = rangerIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        rangerChoiceButton.setIcon(new ImageIcon(scaledRangerIcon));
        
        
        
        // ActionListenerek:
        vetChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Vet";
                dialog.dispose(); 
            }
        });
        
        rangerChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Ranger";
                dialog.dispose(); 
            }
        });
        
        if (isBuying) {
            if (director.getMoney() < 100) {
                rangerChoiceButton.setEnabled(false);
            }
            if (director.getMoney() < 80) {
                vetChoiceButton.setEnabled(false);
            }
        }
        
        panel.add(vetChoiceButton);
        panel.add(rangerChoiceButton);
       
        dialog.add(panel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    
    // Ragadozó állat levadászása egy vadőrrel
    class HuntPredatorActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] animalType = {""};
            animalType[0] = openPredatorChoiceDialog();
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());            
                contentPanel.startPredatorHunt(mousePos, animalType[0]);
            });
        } 
    }
    
    
    // Vadászandó ragadozó állat választása:
    public String openPredatorChoiceDialog() {
        String[] result = {null};
        
        JDialog dialog = new JDialog(parentFrame, "Choose predator type", true); 
        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        
        JButton lionChoiceButton = new JButton();
        JButton hyenaChoiceButton = new JButton();
        
        lionChoiceButton.setFocusable(false);
        hyenaChoiceButton.setFocusable(false);
        
        ImageIcon lionIcon = new ImageIcon("src/main/resources/pictures/lion_middle_aged.png");
        lionChoiceButton.setIcon(lionIcon);
        ImageIcon hyenaIcon = new ImageIcon("src/main/resources/pictures/hyena_middle_aged.png");
        hyenaChoiceButton.setIcon(hyenaIcon);
        
        lionChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Lion";
                dialog.dispose(); 
            }
        });
        
        hyenaChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result[0] = "Hyena";
                dialog.dispose(); 
            }
        });
        
        panel.add(lionChoiceButton);
        panel.add(hyenaChoiceButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    
    class ChipAnimalActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] animalType = {""};
            animalType[0] = openAnimalChoiceDialog();
            
            waitForClick(contentPanel).thenAccept(point -> {
                Position mousePos = new Position((int)point.getX(), (int)point.getY());
                System.out.println("Eger pos X:" + mousePos.getX()+ " Y: " + mousePos.getY());
                
                contentPanel.chipAnimal(mousePos, animalType[0]);
                switchBuyButtons();
            });
        } 
    }
        
    
    class RemoveRouteActionListener implements ActionListener{
        public MouseListener mouseListener;
        private Map<String, Object> actualRoute=null;
        private Route selectedRoute=null;
        private CompletableFuture<Boolean> currentInteraction;
        private final MessageBox messageBox;

        public RemoveRouteActionListener(){

             this.messageBox= new MessageBox(gameEngine.getScrollPosition());
             messageBox.setResponseHandler(this::userResponds);
             contentPanel.add(messageBox);
             contentPanel.addMessBox(this.messageBox); 
        }
        
        private void startInteraction() {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            currentInteraction = new CompletableFuture<>();
         
            SwingUtilities.invokeLater(() -> {
               
                messageBox.setMessage("Biztosan töröli az utvonalat?");
                messageBox.setPosition(500, messageBox.getScrollPosition().y +200);
                messageBox.setBounds(messageBox.getPosition().getX(),messageBox.getPosition().getY(),200,100);
                messageBox.setDisplay(true);
                messageBox.setOpaque(true);
            });
            currentInteraction.thenAccept(answer -> {
                if(answer){
                    contentPanel.removeRoute(selectedRoute);
                   
                }else {
                    selectedRoute.setSelected(false);
                }
            }).whenComplete((res, ex) -> {
               
                SwingUtilities.invokeLater(() -> {
                    messageBox.setPosition(0, 0);
                    messageBox.setDisplay(false);
                    messageBox.setVisible(false);
                    contentPanel.revalidate();
                    removeAllListeners();  // mindig leszedjük a listenereket
                });
            });
                
            }

         public void userResponds(boolean answer) {
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.complete(answer);
                }
            }
        

        @Override
        public void actionPerformed(ActionEvent e) {
             contentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
             contentPanel.actionRemoveRoute=true;
        
            this.mouseListener=new  MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    // Az egérgomb (bal egergomb) lenyomásának eseménye
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (selectedRoute!=null)
                            selectedRoute.setSelected(false);
                                                 
                        Position pos=new Position(e.getX(), e.getY());
                        if(!((boolean)contentPanel.onRoute(pos).get("exist"))){
                            selectedRoute=null;
                            messageBox.setDisplay(false);
                           
                        } else{
                            actualRoute=contentPanel.onRoute(pos); 
                            selectedRoute=(Route)actualRoute.get("route");
                            selectedRoute.setSelected(true);
                            messageBox.setDisplay(true);
                            startInteraction();  
                            
                            }                     
                        }
 
                    //jobb gomb lenyomasanak eseménye
                 if (SwingUtilities.isRightMouseButton(e)) { // Jobb gomb (BUTTON3)
                    removeAllListeners();
                    }
                }
            };
            addAllMyListener();
        }
      public void addAllMyListener(){
          contentPanel.actionRemoveRoute=true;
          for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
          contentPanel.addMouseListener(mouseListener);
      }
        private void removeAllListeners() {
            contentPanel.actionRemoveRoute=false;
            contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
           }
            
         }
         
    }
    
    // az ut vasarlanak esemenykezeloje,ez van a button gombhoz kapcolva.
    class BuyRouteActionListener implements ActionListener{
        public MouseListener mouseListener;
        public MouseMotionListener mouseMotionListener;
        private Map<String, Object> actualRoute;
        private int a;
        private CompletableFuture<Boolean> currentInteraction;
        private final MessageBox messageBox;
        
        public BuyRouteActionListener(){
            
            actualRoute=new HashMap<>();
            this.messageBox=new MessageBox(gameEngine.getScrollPosition());
            this.messageBox.setResponseHandler(this::userResponds);
            contentPanel.add(this.messageBox);   
            contentPanel.addMessBox(this.messageBox);
            
        }
        
        private void startInteraction() {
            
            if (currentInteraction != null && !currentInteraction.isDone()) {
                currentInteraction.completeExceptionally(new CancellationException("Korábbi interakció félbeszakadt"));
            }
            currentInteraction = new CompletableFuture<>();
         
            SwingUtilities.invokeLater(() -> {
            //    System.out.println("Bejott ide");
                int x=this.messageBox.getPosition().getX();
                int y=this.messageBox.getPosition().getY();
                if (x>ContentPanel.WINDOW_WIDTH-300)
                    x-=300;
                if (y>ContentPanel.WINDOW_HEIGHT-300)
                    y-=300;
                if (x<200)
                    x+=200;
                if (y<200)
                    y+=200;
                  
                 this.messageBox.setBounds( x,y,100,50);
                 this.messageBox.setDisplay(true);
                 this.messageBox.setOpaque(true);
               
            });
            
            currentInteraction.thenAccept(answer -> {
                if(answer){
                    director.spendMoney((contentPanel.getRouteSection().size()/3)*contentPanel.getRouteCost());
                    contentPanel.buyRoute();        
                    switchBuyButtons();
                   
                }else {
                    contentPanel.setPathPosition(contentPanel.getRouteSection().getFirst());
                    contentPanel.clearRouteSection();
                }
                 }).whenComplete((res, ex) -> {
               
                    SwingUtilities.invokeLater(() -> {
                        messageBox.setPosition(0, 0);
                        messageBox.setDisplay(false);
                        messageBox.setVisible(false);
                        contentPanel.revalidate();
                        // mindig leszedjük a listenereket
                    });
                }); 
                
             }

             public void userResponds(boolean answer) {
                if (currentInteraction != null && !currentInteraction.isDone()) {
                    currentInteraction.complete(answer);
                    }
                }
         
         @Override
        public void actionPerformed(ActionEvent e) {
            addAllMyListener();
            
            contentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            contentPanel.actionBuyRoute=true;
            
            this.mouseListener=new  MouseAdapter() {
            
                @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Futó szálak száma: " + Thread.activeCount());  
                // Az egérgomb (bal egergomb) lenyomásának eseménye
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Position pos=new Position(e.getX(), e.getY());
                    actualRoute=contentPanel.onRoute(pos);
                  
                    if ((boolean)actualRoute.get("exist")){     
                        Position pathPos=(Position) actualRoute.get("position");
                        int deltaX=e.getX()-pathPos.getX();
                        int deltaY=e.getY()-pathPos.getY();
                        contentPanel.setPathPosition(new Position(pathPos.getX()+deltaX,pathPos.getY()+deltaY));
                        lastCursorPosition=new Position(e.getX(),e.getY());
                        }
                    }
                // a jobb egergomb megnyomasa... 
                 if (SwingUtilities.isRightMouseButton(e)) { // Jobb gomb (BUTTON3)
                    contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                     contentPanel.clearRouteSection();
                    removeAllListeners();
                    }
                }

             @Override
            public void mouseReleased(MouseEvent e) {
                // Az egérgomb felengedésének eseménye
                if ((boolean)actualRoute.get("exist")){
                    if (contentPanel.getRouteSection().size()>10){
                        messageBox.setPosition(e.getX(),e.getY());
                       // messageBox.setDisplay(true);
                        startInteraction();
                    }
                }        
            }
            };
           
            mouseMotionListener= new MouseMotionAdapter() {
            
            //bal egergomb lenyomva tartasaval eger mozgatas, az ut rajzolasa
            @Override
            public void mouseDragged(MouseEvent e) {
               
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (contentPanel.onIcon(new Position(e.getX(),e.getY()))){ 
                        if (a%2==1 && haveMoneyForRoute()&&(!contentPanel.onExitGate(lastCursorPosition))&&!contentPanel.isSubject(lastCursorPosition)){
                            int deltaX=e.getX()-lastCursorPosition.getX();
                            int deltaY=e.getY()-lastCursorPosition.getY();
                           
                            Position  newCenter =contentPanel.getPathPosition();
                            newCenter.setPosition(newCenter.getX()+deltaX, newCenter.getY()+deltaY);
                            lastCursorPosition=new Position(e.getX(), e.getY());
                            contentPanel.setPathPosition(newCenter);
                            contentPanel.addRouteSection(new Position(newCenter.getX(),newCenter.getY()));
                            a=0;
                        } 
                        else {
                            a++;
                        }
                    } 
                }
            }
        };
    }
        public void addAllMyListener(){
            contentPanel.actionBuyRoute=true;
            
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
            }
              for (MouseMotionListener listener : contentPanel.getMouseMotionListeners()) {
                contentPanel.removeMouseMotionListener(listener);
            }
            contentPanel.addMouseListener(mouseListener);
            contentPanel.addMouseMotionListener(mouseMotionListener);
        }
        private void removeAllListeners() {
            contentPanel.actionBuyRoute=false;
            contentPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
       
            for (MouseListener listener : contentPanel.getMouseListeners()) {
                contentPanel.removeMouseListener(listener);
           }
            for (MouseMotionListener listener : contentPanel.getMouseMotionListeners()) {
                contentPanel.removeMouseMotionListener(listener);
            }
              
            contentPanel.removeMouseMotionListener(mouseMotionListener);
  
         }
    }
    
    public boolean haveMoneyForRoute(){
       return (contentPanel.getRouteSection().size()/3)<director.getMoney();
    }

    public void setEnabledButtonJeep(){
        if (contentPanel.routeComplete()){
            buyJeepButton.setEnabled(true);
            buyJeepButton.setForeground(Color.white);
            buyJeepButton.setBackground(new Color(159, 149, 140));
        }
        else {
            buyJeepButton.setEnabled(false);   
        }
        
        if (contentPanel.haveStation()){
            buyDroneButton.setEnabled(true);
            buyDroneButton.setForeground(Color.white);
            buyDroneButton.setBackground(new Color(159,149,140));
            
            buyAirshipButton.setEnabled(true);
            buyAirshipButton.setForeground(Color.white);
            buyAirshipButton.setBackground(new Color(159,149,140));
            
            changeFlightPathButton.setEnabled(true);
            changeFlightPathButton.setForeground(Color.white);
            changeFlightPathButton.setBackground(new Color(159,149,140));
        }
    }
    
    public void setEnabledButtonHuntPredator(){
        if (!contentPanel.getRangers().isEmpty()) {
            huntPredatorButton.setEnabled(true);
            huntPredatorButton.setForeground(Color.white);
            huntPredatorButton.setBackground(new Color(159,149,140));
        }
        else {
            huntPredatorButton.setEnabled(false);   
            huntPredatorButton.setForeground(Color.black);
            huntPredatorButton.setBackground(Color.white);
        }
    }
    
    public void setInfoPanel(){
        infoJeep.setText(String.valueOf(contentPanel.getJeepCount()));
        infoPeople.setText(String.valueOf(gameEngine.getTouristNumber()));
        
        List<Lion> lions = contentPanel.getAnimals().stream().filter(a -> a instanceof Lion && !a.isDead()).map(a -> (Lion) a).collect(Collectors.toList());
        infoLion.setText(String.valueOf(lions.size()));
        
        List<Hyena> hyenas = contentPanel.getAnimals().stream().filter(a -> a instanceof Hyena && !a.isDead()).map(a -> (Hyena) a).collect(Collectors.toList());
        infoHyena.setText(String.valueOf(hyenas.size()));
        
        List<Elephant> elephants = contentPanel.getAnimals().stream().filter(a -> a instanceof Elephant && !a.isDead()).map(a -> (Elephant) a).collect(Collectors.toList());
        infoElephant.setText(String.valueOf(elephants.size()));
        
        List<Giraffe> giraffes = contentPanel.getAnimals().stream().filter(a -> a instanceof Giraffe && !a.isDead()).map(a -> (Giraffe) a).collect(Collectors.toList());
        infoGiraffe.setText(String.valueOf(giraffes.size()));
        
        infoPlant.setText(String.valueOf(contentPanel.getPlants().size()));
    }    
}
