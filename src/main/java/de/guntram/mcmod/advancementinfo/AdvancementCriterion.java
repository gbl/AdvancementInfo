/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo;

import net.minecraft.text.Text;

/**
 *
 * @author gbl
 */
public class AdvancementCriterion {
    private Text name;
    private boolean obtained;
    
    AdvancementCriterion(Text name, boolean obtained) {
        this.name = name;
        this.obtained = obtained;
    }
    
    public boolean getObtained() {
        return obtained;
    }
    
    public Text getName() {
        return name;
    } 
}
