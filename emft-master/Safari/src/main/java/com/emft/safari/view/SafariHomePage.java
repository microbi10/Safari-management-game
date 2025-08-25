package com.emft.safari.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;

public class SafariHomePage extends JPanel{
    
    private String directorName;
    private String difficulty;
    private JTextField nameField;
    private JComboBox difficultyBox;
    private JLabel nameErrorLabel;
    private SafariGUI safariGUI;

    
    public SafariHomePage(SafariGUI safariGUI){
        this.setPreferredSize(new Dimension(1000, 700));
        this.safariGUI = safariGUI;
        this.setLayout(new BoxLayout(this, View.Y_AXIS));
        
        add(Box.createVerticalStrut(500));

        // Név választása:
        JLabel nameLabel = new JLabel("NAME:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setForeground(Color.white);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(200, 20));
        
        Border coloredBorder = new LineBorder(Color.white, 2);
        Border padding = new EmptyBorder(5, 5, 5, 5);
        nameField.setBorder(BorderFactory.createCompoundBorder(coloredBorder, padding));
        
        nameField.setFont(new Font("Arial", Font.BOLD, 16));
        nameField.setForeground(Color.white);
        nameField.setOpaque(false);
        add(nameField);
        
        add(Box.createVerticalStrut(8));
        
        // NameError megjelenítése:
        nameErrorLabel = new JLabel("");
        nameErrorLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameErrorLabel.setPreferredSize(new Dimension(300, 13));
        nameErrorLabel.setMinimumSize(new Dimension(300, 13));
        add(nameErrorLabel);
        
        add(Box.createVerticalStrut(10));
        
        // Nehézségi szint választása:
        JLabel diffLabel = new JLabel("DIFFICULTY:");
        diffLabel.setFont(new Font("Arial", Font.BOLD, 18));
        diffLabel.setAlignmentX(CENTER_ALIGNMENT);
        diffLabel.setForeground(Color.white);
        add(diffLabel);
        
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyBox = new JComboBox(difficulties);
        difficultyBox.setMaximumSize(new Dimension(200, 20));
        difficultyBox.setFont(new Font("Arial", Font.BOLD, 16));
        add(difficultyBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonPanel.setOpaque(false);
        
        // Game Manual gomb:
        JButton manualButton = new JButton("Game Manual");
        manualButton.addActionListener(e -> safariGUI.switchToOtherPanel("gameManual"));
        manualButton.setBackground(new Color(210, 130, 49));
        manualButton.setForeground(Color.WHITE);
        manualButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(1, Color.BLACK, new Color(117, 49, 13)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        manualButton.setPreferredSize(new Dimension(150, 40));
        manualButton.setFont(new Font("Arial", Font.BOLD, 16));
        manualButton.setFocusPainted(false);
        manualButton.setContentAreaFilled(true);

        manualButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                manualButton.setBackground(new Color(230, 150, 70));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                manualButton.setBackground(new Color(210, 130, 49));
            }
        });
       
        buttonPanel.add(manualButton);

        buttonPanel.add(Box.createHorizontalGlue());

        // Start gomb:
        JButton startButton = new JButton("START");
        startButton.setBackground(new Color(210, 130, 49));
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(1, Color.BLACK, new Color(117, 49, 13)),
            new EmptyBorder(5, 15, 5, 15)
        ));
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(true);

        startButton.addActionListener(e -> {
            directorName = nameField.getText();
            if (directorName.isEmpty()) {
                nameErrorLabel.setText("Please give a name!");
                return;
            }
            difficulty = (String) difficultyBox.getSelectedItem();
            safariGUI.switchToOtherPanel("gameEngine");
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(new Color(230, 150, 70));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(210, 130, 49));
            }
        });

        buttonPanel.add(startButton);
        
        add(buttonPanel);
    }

    
    public String getDifficulty() {
        return difficulty;
    }
    
    public String getDirectorName() {
        return directorName;
    }
}
