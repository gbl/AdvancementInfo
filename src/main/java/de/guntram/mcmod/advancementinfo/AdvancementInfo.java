package de.guntram.mcmod.advancementinfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.guntram.mcmod.advancementinfo.accessors.AdvancementProgressAccessor;
import de.guntram.mcmod.advancementinfo.accessors.AdvancementWidgetAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;

public class AdvancementInfo implements ClientModInitializer
{
    static final String MODID="advancementinfo";
    static final String VERSION="@VERSION@";
    
    static final public int AI_spaceX = 30;
    static final public int AI_spaceY = 30;
    static final public int AI_infoWidth = 120;
    
    static public AdvancementWidget mouseOver, mouseClicked;
    static public List<AdvancementStep> cachedClickList;
    public static boolean showAll;

    public static List<AdvancementStep> getSteps(AdvancementWidgetAccessor widget) {
        List<AdvancementStep> result = new ArrayList<>();
        addStep(result, widget.getProgress(), widget.getProgress().getUnobtainedCriteria(), false);
        addStep(result, widget.getProgress(), widget.getProgress().getObtainedCriteria(), true);
        return result;        
    }
    
    private static void addStep(List<AdvancementStep> result, AdvancementProgress progress, Iterable<String> criteria, boolean obtained) {
        final String[] prefixes = new String[] { "item.minecraft", "block.minecraft", "entity.minecraft", "container", "effect.minecraft", "biome.minecraft" };
        // criteria is actually a List<> .. but play nice
        ArrayList<String> sorted=new ArrayList<>();
        for (String s:criteria) {
            sorted.add(s);
        }
        ArrayList<String> details = null;
        Collections.sort(sorted);
        for (String s: sorted) {
            String translation = null;
            String key = s;
            if (key.startsWith("minecraft:")) {
                key=key.substring(10);
            }
            if (key.startsWith("textures/entity/")) {
                String entityAppearance = key.substring(16);
                int dotPos;
                if ((dotPos = entityAppearance.indexOf("."))>0) {
                    entityAppearance = entityAppearance.substring(0, dotPos);
                }
                translation =  entityAppearance;
            }
            if (translation == null) {
                for (String prefix: prefixes) {
                    if (I18n.hasTranslation(prefix+"."+key)) {
                        translation = I18n.translate(prefix+"."+key);
                        break;
                    }
                }
            }
            if (translation == null) {
                CriterionConditions conditions = ((AdvancementProgressAccessor)(progress)).getCriterion(s).getConditions();
                JsonObject o = conditions.toJson(AdvancementEntityPredicateSerializer.INSTANCE);
                JsonObject effects = o.getAsJsonObject("effects");
                if (effects != null) {
                    details = new ArrayList<>(effects.entrySet().size());
                    for (Map.Entry<String, JsonElement> entry: effects.entrySet()) {
                        details.add(I18n.translate("effect."+entry.getKey().replace(':', '.')));
                    }
                }
                translation = key;
            }
            result.add(new AdvancementStep(translation, obtained, details));
        }
    }

    @Override
    public void onInitializeClient()
    {
        showAll = true;
    }
}
