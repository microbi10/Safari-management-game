package com.emft.safari.view;

import com.emft.safari.model.people.Director;
import com.emft.safari.model.utilities.GameLevel;
import static com.emft.safari.model.utilities.GameLevel.EASY;
import static com.emft.safari.model.utilities.GameLevel.HARD;
import static com.emft.safari.model.utilities.GameLevel.MEDIUM;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;


public class SafariGUI extends JFrame {
    
    private SafariHomePage homePage;
    private SafariGameEngine gameEngine;
    private SafariGameManual gameManual;
    private Director director;
    private Image backgroundImage;
    
    
    public SafariGUI(){
        
        this.setTitle("Start a new game");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Háttérkép betöltése:
        this.backgroundImage = new ImageIcon(getClass().getResource("/pictures/homepage.png")).getImage();
        this.backgroundImage = backgroundImage.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        
        // A homePage panel beállítása háttérképpel:
        homePage = new SafariHomePage(this) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            }
        };
        
        homePage.setBorder(new EmptyBorder(20, 60, 20, 60));
        this.add(homePage);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.switchToOtherPanel("homePage");
    }
    
    
    // Másik panelre váltás
    public final void switchToOtherPanel(String other) {
        switch (other) {
            case "gameEngine" -> {
                this.setVisible(false);
                this.getContentPane().removeAll();
                this.revalidate();
                this.repaint();
                
                this.setTitle(homePage.getDirectorName() + "'s Safari");
                
                GameLevel difficulty;
                
                switch (homePage.getDifficulty()) {
                    case "Easy" -> difficulty = EASY;
                    case "Medium" -> difficulty = MEDIUM;
                    case "Hard" -> difficulty = HARD;
                    default -> throw new IllegalArgumentException("Choosing difficulty - Unknown difficulty type: " + homePage.getDifficulty());
                }
                
                director = new Director(homePage.getDirectorName());
                gameEngine = new SafariGameEngine(director, difficulty, this);
                this.add(gameEngine);
              
                JMenuBar menuBar = new JMenuBar();
                JMenu menuGame = new JMenu("Game");
                
                JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                
                JMenuItem menuHomePage = new JMenuItem(new AbstractAction("New Game") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchToOtherPanel("homePage");
                    }
                });
                
                menuGame.add(menuHomePage);
                menuGame.add(menuGameExit);
                menuBar.add(menuGame);
                this.setJMenuBar(menuBar);
                
                this.pack();
                this.setVisible(true);
                this.setExtendedState(MAXIMIZED_BOTH);
            }
            
            case "homePage" -> {
                this.setVisible(false);
                this.getContentPane().removeAll();
               
                if (gameEngine != null){
                    gameEngine.stopAllTimers();
                    this.remove(gameEngine);
                }
                
                this.setJMenuBar(null);
                
                homePage = new SafariHomePage(this) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if (backgroundImage != null) {
                            g.drawImage(backgroundImage, 0, 0, this);
                        }
                    }
                };
                
                homePage.setBorder(new EmptyBorder(20, 60, 20, 60));
                this.add(homePage);
                
                this.setTitle("Start a new game");
                
                this.setVisible(true);
                this.setExtendedState(NORMAL);
                this.setSize(800, 600);
                this.pack();
            }
            
            case "gameManual" -> {
                this.setVisible(false);
                this.getContentPane().removeAll();
                
                gameManual = new SafariGameManual(this);
                this.add(gameManual);
                
                this.setTitle("Safari Game Manual");
                
                this.setVisible(true);
            }
            
            default -> throw new IllegalArgumentException("Switching to other panel - Unknown panel type: " + other);
        }
    }
}
