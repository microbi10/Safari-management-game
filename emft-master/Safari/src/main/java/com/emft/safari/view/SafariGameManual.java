package com.emft.safari.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;

public class SafariGameManual extends JPanel {
    private SafariGUI safariGUI;
    private Image backgroundImage;

    public SafariGameManual(SafariGUI safariGUI) {
        this.safariGUI = safariGUI;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.backgroundImage = new ImageIcon(getClass().getResource("/pictures/manual.png")).getImage();
        this.backgroundImage = backgroundImage.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);

        add(Box.createVerticalStrut(30));

        JLabel titleLabel = new JLabel("Safari Game Manual");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.white);
        add(titleLabel);

        add(Box.createVerticalStrut(20));

        // Szöveg beolvasása fájlból
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(0, 0, 0, 180)); // fekete, enyhén átlátszó háttér
        textArea.setBorder(new EmptyBorder(10, 20, 10, 20));
       
        // HTML formázású szöveg megjelenítése
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/emft/safari/view/manual.txt"))) {
            StringBuilder htmlContent = new StringBuilder("<html><body style='color:white; background-color:rgba(0,0,0,0.7); font-family:Arial; font-size:14pt;'>");
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("<br>");
            }
            htmlContent.append("</body></html>");
            editorPane.setText(htmlContent.toString());
        }
        catch (IOException e) {
            editorPane.setText("<html><body><b>Hiba a fájl olvasásakor.</b></body></html>");
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setBorder(new EmptyBorder(0, 50, 0, 50));

        add(scrollPane);
        add(Box.createVerticalStrut(20));

        JButton backButton = new JButton("Back To Menu");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> safariGUI.switchToOtherPanel("homePage"));
        backButton.setBackground(new Color(210, 130, 49));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(1, Color.BLACK, new Color(117, 49, 13)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(true);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(230, 150, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(210, 130, 49));
            }
        });

        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}
