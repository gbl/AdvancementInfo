package de.guntram.mcmod.advancementinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class AdvancementInfo implements ClientModInitializer
{
    static final String MODID="advancementinfo";
    static final String VERSION="@VERSION@";
    
    static final public int AI_spaceX = 30;
    static final public int AI_spaceY = 30;
    static final public int AI_infoWidth = 120;
    
    static public AdvancementWidget mouseOver, mouseClicked;
    static public List<AdvancementStep> cachedClickList;

    public static List<AdvancementStep> getSteps(AdvancementProgressSupplier widget) {
        List<AdvancementStep> result = new ArrayList<>();
        addStep(result, widget.getProgress().getUnobtainedCriteria(), false);
        addStep(result, widget.getProgress().getObtainedCriteria(), true);
        return result;        
    }
    
    private static void addStep(List<AdvancementStep> result, Iterable<String> criteria, boolean obtained) {
        final String[] prefixes = new String[] { "item.minecraft", "block.minecraft", "entity.minecraft", "container", "effect.minecraft", "biome.minecraft" };
        // criteria is actually a List<> .. but play nice
        ArrayList<String> sorted=new ArrayList<>();
        for (String s:criteria) {
            sorted.add(s);
        } 
        Collections.sort(sorted);
        for (String s: sorted) {
            Text translation = null;
            String key = s;
            if (key.startsWith("minecraft:")) {
                key=key.substring(10);
            }
            for (String prefix: prefixes) {
                if (I18n.hasTranslation(prefix+"."+key)) {
                    translation = new TranslatableText(prefix+"."+key);
                    break;
                }
            }
            if (translation == null) {
                translation = new LiteralText(key);
            }
            result.add(new AdvancementStep(translation, obtained));
        }
    }
    
    
    @Override
    public void onInitializeClient()
    {
    }
}
