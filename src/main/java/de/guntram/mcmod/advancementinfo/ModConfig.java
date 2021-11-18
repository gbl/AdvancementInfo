package de.guntram.mcmod.advancementinfo;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static de.guntram.mcmod.advancementinfo.AdvancementInfo.config;

@Config(name = AdvancementInfo.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.ColorPicker
    public int colorHave = 0x00aa00;
    @ConfigEntry.ColorPicker
    public int colorHaveNot = 0xaa0000;
    public int marginX = 30;
    public int marginY = 30;

    @ConfigEntry.Gui.CollapsibleObject
    public InfoWidth infoWidth = new InfoWidth();

    public static class InfoWidth {
        int min = 120;
        int max = 300;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 50)
        int percent = 30;

        private int clamp(int totalWidth) {
            int wantWidth = (int) (totalWidth*percent/100f);
            return Math.max(Math.min(wantWidth, max), min);
        }

        public int calculate(int screenWidth) {
            if(min > max) max = min;
            if(percent == 0 || max == 0) {
                return 0;
            }
            return clamp(screenWidth - config.marginX*2);
        }
    }
}
