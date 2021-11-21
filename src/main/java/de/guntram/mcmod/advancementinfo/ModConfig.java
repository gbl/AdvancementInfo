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

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        this.validate();
    }

    public void validate() {
        infoWidth.min = Math.max(100, infoWidth.min);
        if (infoWidth.min > infoWidth.max) {
            infoWidth.max = infoWidth.min;
        }
    }

    public static class InfoWidth {
        public int min = 120;
        public int max = 300;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 50)
        public int percent = 30;

        private int clamp(int totalWidth) {
            int wantWidth = (int) (totalWidth * percent / 100f);
            return Math.max(Math.min(wantWidth, max), min);
        }

        public int calculate(int screenWidth) {
            if (percent == 0 || max == 0) {
                return 0;
            }
            return clamp(screenWidth - config.marginX * 2);
        }
    }
}
