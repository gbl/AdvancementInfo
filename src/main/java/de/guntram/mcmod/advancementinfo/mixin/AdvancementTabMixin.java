/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.mixin;

import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import static de.guntram.mcmod.advancementinfo.AdvancementInfo.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author gbl
 */
@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {
    
    @Shadow @Final
    private AdvancementsScreen screen;
    private int currentInfoWidth;

    @Inject(method="render", at = @At("HEAD"))
    private void updateLayout(DrawContext context, int x, int y, CallbackInfo ci) {
        if(screen != null) {
            currentInfoWidth = config.infoWidth.calculate(screen.width);
        }
    }

    // space of the whole internal advancements widget
    @ModifyConstant(method="render", constant=@Constant(intValue = 234), require = 1)
    private int getAdvTreeXSize(int orig) { return screen.width - config.marginX*2 - 2*9 - currentInfoWidth; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 113), require = 1)
    private int getAdvTreeYSize(int orig) { return screen.height - config.marginY*2 - 3*9; }

    // origin of the shown tree within the scrollable space
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 117), require = 1)
    private int getAdvTreeXOrig(int orig) { return screen.width/2 - config.marginX - currentInfoWidth/2; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 56), require = 1)
    private int getAdvTreeYOrig(int orig) { return screen.height/2 - config.marginY; }
    
    @ModifyConstant(method="move", constant=@Constant(intValue = 234), require = 2)
    private int getMoveXCenter(int orig) { return screen.width - config.marginX*2 - 2*9 - currentInfoWidth; }

    @ModifyConstant(method="move", constant=@Constant(intValue = 113), require = 2)
    private int getMoveYCenter(int orig) { return screen.height - config.marginY*2 - 3*9; }
    
    // need to repeat the texture inside the scrollable space more
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 15), require = 1)
    private int getXTextureRepeats(int orig) { return (screen.width-config.marginX*2 - currentInfoWidth) / 16 + 1; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 8), require = 1)
    private int getYTextureRepeats(int orig) { return (screen.height-config.marginY*2) / 16 + 1; }
    
    // area that can show a tooltip
    @ModifyConstant(method="drawWidgetTooltip", constant=@Constant(intValue = 234), require = 2)
    private int getTooltipXSize(int orig) { return screen.width - config.marginX*2 - 2*9 - currentInfoWidth; }

    @ModifyConstant(method="drawWidgetTooltip", constant=@Constant(intValue = 113), require = 2)
    private int getTooltipYSize(int orig) { return screen.height - config.marginY*2 - 3*9; }
    
    @Inject(method="drawWidgetTooltip", at=@At("HEAD"))
    private void forgetMouseOver(DrawContext context, int mouseX, int mouseY, int x, int y, CallbackInfo ci) {
        AdvancementInfo.mouseOver = null;
    }
}
