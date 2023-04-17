package com.lothrazar.enchantingrunes;

import java.util.Arrays;
import java.util.List;
import com.lothrazar.library.config.ConfigTemplate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigRegistryRunes extends ConfigTemplate {

  private static final ForgeConfigSpec CONFIG;
  public static ConfigValue<List<? extends String>> THEWORDS;
  static {
    final ForgeConfigSpec.Builder BUILDER = builder();
    BUILDER.comment("General settings").push(ModMainRunes.MODID);
    String[] deflist = new String[] { "minecraft:sharpness->aei", "minecraft:sharpness->aeo", "minecraft:sharpness->au", "minecraft:smite->ou", "minecraft:unbreaking->eu"
        //more
        , "minecraft:protection->aiot", "minecraft:depth_strider->aiut", "minecraft:feather_falling->eeot", "minecraft:aqua_affinity->iiut"
        ///
        , "cyclic:reach->aeiou", "cyclic:traveler->aeiot", "cyclic:beekeeper->aeyyt", "cyclic:step->ioio", "minecraft:blast_protection->uyy",
        "minecraft:binding_curse->ttt", "minecraft:projectile_protection->eee", "minecraft:respiration->iy", "minecraft:soul_speed->ey", "minecraft:frost_walker->aou"
        //
        , "minecraft:mending->iei", "minecraft:looting->aot", "minecraft:looting->eut", "minecraft:silk_touch->aeit", "minecraft:sweeping->ty", "minecraft:efficiency->tay"
        //
        , "cyclic:auto_smelt->eta", "cyclic:beheading->aey", "cyclic:life_leech->uit", "cyclic:disarm->tao", "cyclic:ender->et", "cyclic:growth->toy", "cyclic:experience_boost->aoi", "cyclic:venom->iou"
        //
        , "cyclic:magnet->tat", "ensorcellation:soulbound->iia", "ensorcellation:magic_edge->aiy", "ensorcellation:frost_aspect->tuy"
        //
        , "minecraft:vanishing_curse->ooo", "ensorcellation:curse_mercy->aaa", "flowingagony:cutting_watermelon_dream->uty", "flowingagony:morirs_deathwish->uiuy"
        //MGX 
        , "flowingagony:nimble_finger->oit", "flowingagony:morirs_lifebound->ato", "flowingagony:trickster->tty", "flowingagony:fresh_revenge->eeti", "flowingagony:paper_brain->eoiy"
        //
        , "flowingagony:last_sweet_dream->tiy", "flowingagony:carefully_identified->tyi", "flowingagony:shock_therapy->aiiy"
    };
    //array of strings 
    THEWORDS = BUILDER.comment("All rune words found within recipes.  WHen crafting runes with a tool, if no runewords are found then a random enchantment might be chosen").defineList("rune_words", Arrays.asList(deflist),
        it -> it instanceof String);
    // "minecraft:sharpness->aeiouyt"
    BUILDER.pop(); // one pop for every push
    CONFIG = BUILDER.build();
  }

  public ConfigRegistryRunes() {
    CONFIG.setConfig(setup(ModMainRunes.MODID));
  }
}
