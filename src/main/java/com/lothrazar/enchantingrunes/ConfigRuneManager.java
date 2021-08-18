package com.lothrazar.enchantingrunes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.util.Arrays;
import java.util.List;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigRuneManager {

  private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static ConfigValue<List<? extends String>> THEWORDS;
  static {
    initConfig();
  }

  private static void initConfig() {
    CFG.comment("General settings").push(ModMainRunes.MODID);
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
    THEWORDS = CFG.comment("All rune words found within recipes.  WHen crafting runes with a tool, if no runewords are found then a random enchantment might be chosen").defineList("rune_words", Arrays.asList(deflist),
        it -> it instanceof String);
    // "minecraft:sharpness->aeiouyt"
    CFG.pop(); // one pop for every push
    COMMON_CONFIG = CFG.build();
  }

  public static void setup() {
    final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(ModMainRunes.MODID + ".toml"))
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }
}
