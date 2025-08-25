package com.emft.safari.model.plants;

import com.emft.safari.view.ContentPanel;
import com.emft.safari.model.utilities.Position;


public class ElephantGrass extends Plant{
    
    public ElephantGrass(Position pos, ContentPanel contentPanel){
        super(pos, contentPanel);
    }
    
    @Override 
    public void grow(){
        sizePoint++;
    }
}