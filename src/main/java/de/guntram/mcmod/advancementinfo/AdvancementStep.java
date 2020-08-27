/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo;

import java.util.List;

public class AdvancementStep {
    
    // As we need to run trimToWidth over all entries anyway, which wants 
    // Strings, not Texts, we can save a few µs by using all Strings here
    // and not converting to and fro.
    
    private String name;
    private boolean obtained;
    private List<String> details;
    
    AdvancementStep(String name, boolean obtained, List<String> details) {
        this.name = name;
        this.obtained = obtained;
        this.details = details;
    }
    
    public boolean getObtained() {
        return obtained;
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getDetails() {
        return details;
    }
}
