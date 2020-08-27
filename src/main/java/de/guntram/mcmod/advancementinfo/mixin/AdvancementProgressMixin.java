/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.mixin;

import de.guntram.mcmod.advancementinfo.accessors.AdvancementProgressAccessor;
import java.util.Map;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementProgress.class)
public class AdvancementProgressMixin implements AdvancementProgressAccessor {
    
    private Map<String, AdvancementCriterion> savedCriteria;
    
    @Inject(method="init", at=@At("HEAD"))
    public void saveCriteria(Map<String, AdvancementCriterion> criteria, String[][] requirements, CallbackInfo ci) {
        savedCriteria = criteria;
    }
    
    @Override
    public AdvancementCriterion getCriterion(String name) {
        return savedCriteria.get(name);
    }
}
