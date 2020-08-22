/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo;

import net.minecraft.advancement.AdvancementProgress;

/**
 *
 * @author gbl
 */
public interface AdvancementProgressSupplier {
    public AdvancementProgress getProgress();
}
