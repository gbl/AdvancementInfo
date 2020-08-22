/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.mixin;

import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import de.guntram.mcmod.advancementinfo.AdvancementProgressSupplier;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author gbl
 */

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin implements AdvancementProgressSupplier {
    
    @Shadow private AdvancementProgress progress;
    
    @Inject(method="drawTooltip", at=@At("HEAD")) 
    public void rememberTooltip(MatrixStack stack, int i, int j, float f, int y, int k, CallbackInfo ci) {
        AdvancementInfo.mouseOver = (AdvancementWidget)(Object)this;
    }
    
    @Override
    public AdvancementProgress getProgress() {
        return this.progress;
    }
}
