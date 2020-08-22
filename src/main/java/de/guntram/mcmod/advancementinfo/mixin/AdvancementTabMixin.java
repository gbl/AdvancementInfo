/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.mixin;

import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import static de.guntram.mcmod.advancementinfo.AdvancementInfo.AI_spaceX;
import static de.guntram.mcmod.advancementinfo.AdvancementInfo.AI_spaceY;
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
    
    @Shadow @Final AdvancementsScreen screen;

    // space of the whole internal advancements widget
    @ModifyConstant(method="render", constant=@Constant(intValue = 234), require = 1)
    private int getAdvTreeXSize(int orig) { return screen.width - AI_spaceX*2 - 2*9 - AdvancementInfo.AI_infoWidth; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 113), require = 1)
    private int getAdvTreeYSize(int orig) { return screen.height - AI_spaceY*2 - 3*9; }

    // origin of the shown tree within the scrollable space
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 117), require = 1)
    private int getAdvTreeXOrig(int orig) { return screen.width/2 - AI_spaceX - AdvancementInfo.AI_infoWidth/2; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 56), require = 1)
    private int getAdvTreeYOrig(int orig) { return screen.height/2 - AI_spaceY; }
    
    @ModifyConstant(method="move", constant=@Constant(intValue = 234), require = 2)
    private int getMoveXCenter(int orig) { return screen.width - AI_spaceX*2 - 2*9 - AdvancementInfo.AI_infoWidth; }

    @ModifyConstant(method="move", constant=@Constant(intValue = 113), require = 2)
    private int getMoveYCenter(int orig) { return screen.height - AI_spaceY*2 - 3*9; }
    
    // need to repeat the texture inside the scrollable space more
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 15), require = 1)
    private int getXTextureRepeats(int orig) { return (screen.width-AI_spaceX*2 - AdvancementInfo.AI_infoWidth) / 16 + 1; }

    @ModifyConstant(method="render", constant=@Constant(intValue = 8), require = 1)
    private int getYTextureRepeats(int orig) { return (screen.height-AI_spaceY*2) / 16 + 1; }
    
    // area that can show a tooltip
    @ModifyConstant(method="drawWidgetTooltip", constant=@Constant(intValue = 234), require = 2)
    private int getTooltipXSize(int orig) { return screen.width - AI_spaceX*2 - 2*9 - AdvancementInfo.AI_infoWidth; }

    @ModifyConstant(method="drawWidgetTooltip", constant=@Constant(intValue = 113), require = 2)
    private int getTooltipYSize(int orig) { return screen.height - AI_spaceY*2 - 3*9; }
    
    @Inject(method="drawWidgetTooltip", at=@At("HEAD"))
    private void forgetMouseOver(MatrixStack stack, int i, int j, int y, int k, CallbackInfo ci) {
        AdvancementInfo.mouseOver = null;
    }
}
