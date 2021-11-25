/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.minecraft.client.gui.screen.advancement;


import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author gbl
 */

@SuppressWarnings("public-target")
@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {
    @Dynamic
    @Inject(method="getTabX", at=@At("HEAD"), cancellable = true)
    public void getAdjustedTabX(int index, CallbackInfoReturnable<Integer> cir) {
        if(this.equals(AdvancementTabType.RIGHT)) {
            cir.setReturnValue(MinecraftClient.getInstance().currentScreen.width - AdvancementInfo.config.marginX*2 - 4);
            cir.cancel();
        }
    }

    @Dynamic
    @Inject(method="getTabY", at=@At("HEAD"), cancellable = true) 
    public void getAdjustedTabY(int index, CallbackInfoReturnable<Integer> cir) {
        if(this.equals(AdvancementTabType.BELOW)) {
            cir.setReturnValue(MinecraftClient.getInstance().currentScreen.height - AdvancementInfo.config.marginY*2 - 4);
            cir.cancel();
        }
    }
}
