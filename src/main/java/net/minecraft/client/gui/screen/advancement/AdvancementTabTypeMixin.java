/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.minecraft.client.gui.screen.advancement;


import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author gbl
 */

@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {
    
    @Inject(method="getTabX", at=@At("HEAD"), cancellable = true) 
    public void getAdjustedTabX(int index, CallbackInfoReturnable cir) {
        if ((AdvancementTabType)(Object)this == AdvancementTabType.RIGHT) {
            cir.setReturnValue(MinecraftClient.getInstance().currentScreen.width - AdvancementInfo.AI_spaceX*2 - 4);
            // System.out.println("X pos is "+cir.getReturnValueI());
            cir.cancel();
        }
    }

    @Inject(method="getTabY", at=@At("HEAD"), cancellable = true) 
    public void getAdjustedTabY(int index, CallbackInfoReturnable cir) {
        if ((AdvancementTabType)(Object)this == AdvancementTabType.BELOW) {
            cir.setReturnValue(MinecraftClient.getInstance().currentScreen.height - AdvancementInfo.AI_spaceY*2 - 4);
            // System.out.println("Y pos is "+cir.getReturnValueI());
            cir.cancel();
        }
    }
}
