package com.emft.safari.view;

import com.emft.safari.model.utilities.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageBox  extends JPanel{
    
    private int WIDTH = 300;
    private int HEIGHT = 200;
    private boolean answer;
    private boolean visible =false;
    private final Position position;
    private final JButton yesButton;
    private final JButton noButton;
    private final JLabel message;
    private Consumer<Boolean> responseHandler;
    private final Point scrollPosition;
    
    public MessageBox(Point scrollPosition){
        this.scrollPosition=scrollPosition;
        this.position=new Position(scrollPosition.x,scrollPosition.y);
        this.setLayout(new FlowLayout()); // Gombok egymás mellett lesznek
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); 
        this.setBounds(position.getX(),position.getY(),250,100);
        yesButton = new JButton("\u2713");
        noButton = new JButton("\u2717");
        yesButton.setBackground(Color.green);
        noButton.setBackground(Color.red);
        this.answer=false;
        this.message=new JLabel();
        this.setVisible(false);
        // Hozzáadjuk az elemeket a panelhez
       
        this.add(yesButton);
        this.add(noButton);
        this.setOpaque(true);
        this.add(this.message);
        
       
        
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(responseHandler !=null){
                    responseHandler.accept(true);
                }
                answer=true;
                visible=false;
            }
        });
          noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if(responseHandler !=null){
                    responseHandler.accept(false);
                }
                
                answer=false;
                visible=false;
               
            }
        });
    
    }
    public void checkByTime(){
         this.setVisible(visible);
         this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); 
         yesButton.repaint();
            noButton.repaint();
            yesButton.setBorderPainted(false);
            noButton.setBorderPainted(false);
    
    }
          
         
        public Point getScrollPosition(){
            return this.scrollPosition;
        }
        public void setResponseHandler(Consumer<Boolean> handler){
            this.responseHandler=handler;
        }
        
        public boolean getVisible(){
            return this.visible;
        }
    
        public void setDisplay(boolean bol){
            this.visible=bol;
        }
        public void setEnabledYesButton(boolean bool){
            this.yesButton.setEnabled(bool);
        
        }
        public void setEnabledNoButton(boolean bool){
            this.noButton.setEnabled(bool);
        }
    
        public void setPosition(int x, int y){
            this.position.setPosition(x,y);
        }
        public Position getPosition(){
            return position;
        }
        public boolean getAnswer(){
            return answer;
        }
        
        public void setSize(int w, int h){
            WIDTH = w;
            HEIGHT = h;
        }
        public JButton getYesButton(){
            return this.yesButton;
        }
        public JButton getNoButton(){
            return this.noButton;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            
        }
        public void setMessage(String mes){
            this.message.setText(mes);
        }
    
}
