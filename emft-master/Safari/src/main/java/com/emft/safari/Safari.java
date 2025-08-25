package com.emft.safari;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import com.emft.safari.view.SafariGUI;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dream Store
 */
public class Safari {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SafariGUI safariGUI = new SafariGUI();
        });
    }
}
