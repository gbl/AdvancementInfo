/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.accessors;

import net.minecraft.advancement.AdvancementCriterion;

/**
 *
 * @author gbl
 */
public interface AdvancementProgressAccessor {
    public AdvancementCriterion getCriterion(String name);
}
