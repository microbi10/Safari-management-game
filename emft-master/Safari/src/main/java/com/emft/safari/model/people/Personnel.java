package com.emft.safari.model.people;

import com.emft.safari.model.utilities.Position;
import com.emft.safari.view.ContentPanel;


public abstract class Personnel extends Person {
    
    private final int employmentStartDate;
    private boolean isDoneWorking;
    
    public Personnel (Position pos, ContentPanel contentPanel, int employmentStartDate) {
        super(pos, contentPanel);
        this.employmentStartDate = employmentStartDate;
        this.isDoneWorking = false;
    }
    
    public boolean isDoneWorking() {
        return isDoneWorking;
    }
    
    
    /**
     * Ellenőrzi, hogy a személyzet tagja végzett-e a munkájával (eltelt-e 1 hónap a megvásárlása óta).
     */ 
    public void checkEmploymentStatus() {
        if (employmentStartDate + 30 * 24 <= contentPanel.getTimeValue()) {
            isDoneWorking = true;
        }
    }
    
    public Position getPosition() {
        return pos;
    }
}
