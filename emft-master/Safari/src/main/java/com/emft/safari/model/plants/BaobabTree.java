package com.emft.safari.model.plants;

import com.emft.safari.view.ContentPanel;
import com.emft.safari.model.utilities.Position;


public class BaobabTree extends Plant {
    
    public BaobabTree(Position pos, ContentPanel contentPanel){
        super(pos, contentPanel);
    }
    
    @Override 
    public void grow(){
        sizePoint++;
    }
}
