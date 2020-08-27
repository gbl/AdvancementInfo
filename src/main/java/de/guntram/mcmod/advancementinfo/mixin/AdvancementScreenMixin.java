/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.advancementinfo.mixin;

import de.guntram.mcmod.advancementinfo.AdvancementInfo;
import static de.guntram.mcmod.advancementinfo.AdvancementInfo.AI_spaceX;
import static de.guntram.mcmod.advancementinfo.AdvancementInfo.AI_spaceY;
import de.guntram.mcmod.advancementinfo.AdvancementStep;
import de.guntram.mcmod.advancementinfo.IteratorReceiver;
import de.guntram.mcmod.advancementinfo.accessors.AdvancementWidgetAccessor;
import java.util.List;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author gbl
 */
@Mixin(AdvancementsScreen.class)
public class AdvancementScreenMixin extends Screen {
    
    public AdvancementScreenMixin() { super(null); }
    
    private int scrollPos;
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 252), require=1)
    private int getRenderLeft(int orig) { return width - AI_spaceX*2; }
    
    @ModifyConstant(method="render", constant=@Constant(intValue = 140), require=1)
    private int getRenderTop(int orig) { return height - AI_spaceY*2; }

    @ModifyConstant(method="mouseClicked", constant=@Constant(intValue = 252), require=1)
    private int getMouseLeft(int orig) { return width - AI_spaceX*2; }
    
    @ModifyConstant(method="mouseClicked", constant=@Constant(intValue = 140), require=1)
    private int getMouseTop(int orig) { return height - AI_spaceY*2; }

    @ModifyConstant(method="drawAdvancementTree", constant=@Constant(intValue = 234), require = 1)
    private int getAdvTreeXSize(int orig) { return width - AI_spaceX*2 - 2*9 - AdvancementInfo.AI_infoWidth; }

    @ModifyConstant(method="drawAdvancementTree", constant=@Constant(intValue = 113), require = 1)
    private int getAdvTreeYSize(int orig) { return height - AI_spaceY*2 - 3*9; }

    /* Make it so that drawWidgets only draws the left top corner ... */
    @ModifyConstant(method="drawWidgets", constant=@Constant(intValue = 252), require=1)
    private int getWidgetsLeft(int orig) // { return width - AI_spaceX*2; }
    { return 126; }
    
    @ModifyConstant(method="drawWidgets", constant=@Constant(intValue = 140), require=1)
    private int getWidgetsTop(int orig) // { return height - AI_spaceY*2; }
    { return 70; }
    
    @Inject(method="render",
            at=@At(value="INVOKE",
                    target="net/minecraft/client/gui/screen/advancement/AdvancementsScreen.drawWidgets(Lnet/minecraft/client/util/math/MatrixStack;II)V"))
    public void renderRightFrameBackground(MatrixStack stack, int x, int y, float delta, CallbackInfo ci) {
        fill(stack, 
                width-AI_spaceX-AdvancementInfo.AI_infoWidth+4, AI_spaceY+4, 
                width-AI_spaceX-4, height-AI_spaceY-4, 0xffc0c0c0);
    }
    
    @Inject(method="drawWidgets",
            at=@At(value="INVOKE", 
                    target="net/minecraft/client/gui/screen/advancement/AdvancementsScreen.drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    public void renderFrames(MatrixStack stack, int x, int y, CallbackInfo ci)
    {
        int iw = AdvancementInfo.AI_infoWidth;

        drawTexture(stack, width-AI_spaceX-126 - iw,                   y, 127,  0, 126, 70);
        drawTexture(stack,                   x,                                height-AI_spaceY-70,   0, 71, 126, 70);
        drawTexture(stack, width-AI_spaceX-126 - iw, height-AI_spaceY-70, 127, 71, 126, 70);
        
        iterate(126, width-AI_spaceX-126 - iw, 200, (pos, len) -> drawTexture(stack, pos, y, 15, 0, len, 70));
        iterate(126, width-AI_spaceX-126 - iw, 200, (pos, len) -> drawTexture(stack, pos, height-AI_spaceY-70, 15, 71, len, 70));
        iterate(70, height-AI_spaceY-70, 100, (pos, len) -> drawTexture(stack, x, pos, 0, 25, 70, len));
        iterate(70, height-AI_spaceY-70, 100, (pos, len) -> drawTexture(stack, width-AI_spaceX-126 - iw, pos, 127, 25, 126, len));


        drawTexture(stack, width-AI_spaceX - iw  ,                   y,   0     ,  0, iw/2,   70);
        drawTexture(stack, width-AI_spaceX - iw/2,                   y, 252-iw/2,  0, iw/2+1, 70);
        drawTexture(stack, width-AI_spaceX - iw  , height-AI_spaceY-70,   0     , 71, iw/2,   70);
        drawTexture(stack, width-AI_spaceX - iw/2, height-AI_spaceY-70, 252-iw/2, 71, iw/2+1, 70);
        iterate(70, height-AI_spaceY-70, 100, (pos, len) -> drawTexture(stack, width-AI_spaceX - iw, pos,          0, 25, iw/2, len));
        iterate(70, height-AI_spaceY-70, 100, (pos, len) -> drawTexture(stack, width-AI_spaceX - iw/2, pos, 252-iw/2, 25, iw/2, len));
    }

    private void iterate(int start, int end, int maxstep, IteratorReceiver func) {
        int size;
        for (int i=start; i<end; i+=maxstep) {
            size=maxstep;
            if (i+size > end) {
                size = end - i;
            }
            func.accept(i, size);
        }
    }
    
    @Inject(method="drawWidgets", at=@At("RETURN"))
    public void renderRightFrameTitle(MatrixStack stack, int x, int y, CallbackInfo ci) {
        textRenderer.draw(stack, I18n.translate("advancementinfo.infopane"), width-AI_spaceX-AdvancementInfo.AI_infoWidth+8, y+6, 4210752);

        if (AdvancementInfo.mouseClicked != null) {
            renderCriteria(stack, AdvancementInfo.mouseClicked);
        } else if (AdvancementInfo.mouseOver != null) {
            renderCriteria(stack, AdvancementInfo.mouseOver);                    
        }
    }
    
    @Inject(method="mouseClicked", at=@At("HEAD"))
    public void rememberClickedWidget(double x, double y, int button, CallbackInfoReturnable cir) {
        AdvancementInfo.mouseClicked = AdvancementInfo.mouseOver;
        scrollPos = 0;
        if (AdvancementInfo.mouseClicked != null) {
            AdvancementInfo.cachedClickList = AdvancementInfo.getSteps((AdvancementWidgetAccessor) AdvancementInfo.mouseClicked);
        } else {
            AdvancementInfo.cachedClickList = null;
        }
    }
    
    @Inject(method="onRootAdded", at=@At("HEAD"))
    public void debugRootAdded(Advancement root, CallbackInfo ci) {
        // System.out.println("root added to screen; display="+root.getDisplay()+", id="+root.getId().toString());
    }
    
    // @Inject(method="mouseScrolled", at=@At("HEAD"), cancellable = true)
    @Override
    public boolean mouseScrolled(double X, double Y, double amount /*, CallbackInfoReturnable cir */) {
        if (amount > 0 && scrollPos > 0) {
            scrollPos--;
        } else if (amount < 0 && AdvancementInfo.cachedClickList != null 
                && scrollPos < AdvancementInfo.cachedClickList.size() - (height-2*AI_spaceY)/textRenderer.fontHeight + 5) {
            scrollPos++;
        }
        return false;
    }
    
    private void renderCriteria(MatrixStack stack, AdvancementWidget widget) {
        int y = AI_spaceY + 20;
        int skip;
        List<AdvancementStep> list;
        if (widget == AdvancementInfo.mouseClicked) {
            list = AdvancementInfo.cachedClickList;
            skip = scrollPos;
        } else {
            list = AdvancementInfo.getSteps((AdvancementWidgetAccessor) widget);
            skip = 0;
        }
        for (AdvancementStep entry: list) {
            if (skip-- > 0) {
                continue;
            }
            textRenderer.draw(stack, 
                    textRenderer.trimToWidth(entry.getName(), AdvancementInfo.AI_infoWidth-24),
                    width-AI_spaceX-AdvancementInfo.AI_infoWidth+12, y, 
                    entry.getObtained() ? 0x00ff00 : 0xff0000);
            y+=textRenderer.fontHeight;
            if (y > height - AI_spaceY - textRenderer.fontHeight) {
                break;
            }
            
            if (list.size() == 1 && entry.getDetails() != null) {
                for (String detail: entry.getDetails()) {
                    textRenderer.draw(stack, 
                            textRenderer.trimToWidth(detail, AdvancementInfo.AI_infoWidth-34),
                            width-AI_spaceX-AdvancementInfo.AI_infoWidth+22, y, 
                            0x000000);
                    y+=textRenderer.fontHeight;
                }
            }
        }
    }
}
