package com.emft.safari.view;

import com.emft.safari.model.plants.Plant;
import com.emft.safari.model.animals.Animal;
import com.emft.safari.model.buildables.Lake;
import com.emft.safari.model.buildables.Route;
import com.emft.safari.model.animals.Lion;
import com.emft.safari.model.animals.Hyena;
import com.emft.safari.model.animals.Giraffe;
import com.emft.safari.model.animals.Elephant;
import com.emft.safari.model.utilities.Position;
import com.emft.safari.model.people.Vet;
import com.emft.safari.model.people.Poacher;
import com.emft.safari.model.people.Ranger;
import com.emft.safari.model.equipment.Jeep;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;

public final class MiniMap extends JPanel {
    private List<Animal> animals;
    private List<Plant> plants;
    private List<Lake> lakes;
    private List<Vet> vets;
    private List<Poacher> poachers;
    private List<Ranger> rangers;
    private List<Jeep> jeeps;
    private final int windowWidth;
    private final int windowHeight;
    private Image backgroundImage;
    private final ContentPanel contentPanel;
    private final Image miniVetIcon;
    private final Image miniRangerIcon;
    private final Image miniPoacherIcon;
    private final Image miniJeepIcon;
    private final SafariGameEngine gameEngine;

    public MiniMap(int width, int height, int windowWidth, int windowHeight, ContentPanel contentPanel, SafariGameEngine gameEngine) {
        this.setSize(width, height);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;  
        this.contentPanel = contentPanel;
        this.gameEngine  = gameEngine;
       
        // Képek betöltése és ellenőrzése:
        URL imageUrl = getClass().getResource("/pictures/background.jpg");
        
        if (imageUrl == null) {
            System.out.println("Nem található a kép: /pictures/background.jpg");
        }
        else {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        }
        
        miniVetIcon = new ImageIcon(getClass().getResource("/pictures/vet_icon.png")).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        miniRangerIcon = new ImageIcon(getClass().getResource("/pictures/ranger_icon.png")).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        miniPoacherIcon = new ImageIcon(getClass().getResource("/pictures/poacher_icon.png")).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        miniJeepIcon = new ImageIcon(getClass().getResource("/pictures/jeep_minimap.png")).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        
        this.setAnimals(contentPanel.getAnimals());
        this.setPlants(contentPanel.getPlants());
        this.setLakes(contentPanel.getLakes());
        this.setRoute(contentPanel.getRoute());
        this.setVets(contentPanel.getVets());
        this.setRangers(contentPanel.getRangers());
        this.setPoachers(contentPanel.getPoachers());
        this.setJeeps(contentPanel.getJeeps());
                              
        repaint();  // Azonnal frissítjük a rajzolást
        
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
        repaint();
    }
    
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
        repaint();
    }
            
    public void setLakes(List<Lake> lakes) {
        this.lakes = lakes;
        repaint();
    }
    
    public void setRoute (Route route) {
        repaint();
    }
    
    public void setVets(List<Vet> vets){
        this.vets = vets;
        repaint();
    }
    
    public void setPoachers(List<Poacher> poachers){
        this.poachers = poachers;
        repaint();
    }
    
    public void setRangers(List<Ranger> rangers){
        this.rangers = rangers;
        repaint();
    }
    
    public void setJeeps(List<Jeep> jeeps){
        this.jeeps = jeeps;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
      //  System.out.println(jeeps);

        // Háttér
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        else {
            g2d.setColor(new Color(0, 0, 0, 80));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (animals == null || plants == null || lakes == null) return;
        
        double scaleX = (double) this.getWidth() / windowWidth;
        double scaleY = (double) this.getHeight() / windowHeight;


        // Állatok:
        for (Animal animal : animals) {
            if (animal instanceof Lion) {
                g2d.setColor(Color.red);
            }
            else if (animal instanceof Hyena) {
                g2d.setColor(new Color(170, 88, 64));
            }
            else if (animal instanceof Giraffe) {
                g2d.setColor(new Color(251,219,80));
            }
            else if (animal instanceof Elephant) {
                g2d.setColor(new Color(204, 204, 204));
            }
            else {
                g2d.setColor(Color.green);
            }

            int miniAnimalX = (int) (animal.getPosition().getX() * scaleX);
            int miniAnimalY = (int) (animal.getPosition().getY() * scaleY);
            g2d.fillOval(miniAnimalX, miniAnimalY, 7, 7);
        }
        

        // Növények:
        g2d.setColor(Color.green);
        for (Plant plant : plants) {
            int miniPlantX = (int) (plant.getPosition().getX() * scaleX);
            int miniPlantY = (int) (plant.getPosition().getY() * scaleY);
            g2d.fillRect(miniPlantX, miniPlantY, 8, 8);
        }

        
        // Tavak:
        g2d.setColor(new Color(0, 190, 255));
        for (Lake lake : lakes) {
            int miniLakeX = (int) (lake.getPosition().getX() * scaleX);
            int miniLakeY = (int) (lake.getPosition().getY() * scaleY);
            g2d.fillRect(miniLakeX, miniLakeY, 8, 8);
        }
        
        // Út:
        for(Route route : contentPanel.getRoutes()){
            int ind = 0;

            if (route != null && !contentPanel.getDeleted()) {
                contentPanel.getRoutes().get(ind);
                g2d.setColor(new Color(214, 124, 44));
                g2d.setStroke(new BasicStroke(3));

                for (int i = 1; i < route.getPoints().size(); i++) {
                    Position p1 = route.getPoints().get(i - 1);
                    Position p2 = route.getPoints().get(i);

                    int miniP1X = (int) (p1.getX() * scaleX);
                    int miniP1Y = (int) (p1.getY() * scaleY);
                    int miniP2X = (int) (p2.getX() * scaleX);
                    int miniP2Y = (int) (p2.getY() * scaleY);

                    g2d.drawLine(miniP1X, miniP1Y, miniP2X, miniP2Y);
                }
            } else {
                ind++;
            }
        }

        
        // Orvos:
        for(Vet vet : vets){
            int miniVetX = (int) (vet.getPosition().getX() * scaleX);
            int miniVetY = (int) (vet.getPosition().getY() * scaleY);
            g2d.drawImage(miniVetIcon, miniVetX - 5, miniVetY - 5, null);
        }      
        
        
        // Vadőr:
        for(Ranger ranger : rangers){
            int miniRangerX = (int) (ranger.getPosition().getX() * scaleX);
            int miniRangerY = (int) (ranger.getPosition().getY() * scaleY);
            g2d.drawImage(miniRangerIcon, miniRangerX - 5, miniRangerY - 5, null);
        }
                
        // Orvvadász:
        for(Poacher poacher : poachers){
            int miniPoacherX = (int) (poacher.getPosition().getX() * scaleX);
            int miniPoacherY = (int) (poacher.getPosition().getY() * scaleY);
            g2d.drawImage(miniPoacherIcon, miniPoacherX - 5, miniPoacherY - 5, null);
        }
        
        // Jeep:
        if(gameEngine.getTouristNumber() >= 4){
            for(Jeep jeep : jeeps){
                if(jeep.getPosition()!=null){
                    int miniJeepX = (int) (jeep.getPosition().getX() * scaleX);
                    int miniJeepY = (int) (jeep.getPosition().getY() * scaleY);
                    if(jeep.getOrientation()){
                        g2d.drawImage(miniJeepIcon, miniJeepX - 5, miniJeepY - 5, null);
                    }
                    else {
                        Graphics2D g2dMirrored = (Graphics2D) g2d.create();

                        g2dMirrored.translate(miniJeepX, miniJeepY);
                        g2dMirrored.scale(-1, 1);

                        g2dMirrored.drawImage(miniJeepIcon, 0, 0, null);
                    }
                }
            }  
        }
    }
}
