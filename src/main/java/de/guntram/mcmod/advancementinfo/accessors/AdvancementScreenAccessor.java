/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.accessors;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.network.ClientAdvancementManager;

/**
 *
 * @author gbl
 */
public interface AdvancementScreenAccessor {
        public ClientAdvancementManager getAdvancementHandler();
        public AdvancementTab myGetTab(PlacedAdvancement advancement);
}
