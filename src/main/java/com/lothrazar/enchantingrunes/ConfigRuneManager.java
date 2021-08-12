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
        //
        , "minecraft:mending->iei", "minecraft:looting->aot", "minecraft:looting->eut", "minecraft:silk_touch->eta", "minecraft:sweeping->ty", "minecraft:efficiency->tay"
        //
        , "cyclic:auto_smelt->eta", "cyclic:beheading->ey", "cyclic:life_leech->uit", "cyclic:disarm->tao", "cyclic:ender->et", "cyclic:growth->toy", "cyclic:experience_boost->aoi", "cyclic:venom->iou"
        //
        , "cyclic:magnet->tat", "ensorcellation:soulbound->iia", "ensorcellation:magic_edge->aiy", "ensorcellation:frost_aspect->tuy"
        //
        , "minecraft:vanishing_curse->ooo", "ensorcellation:curse_mercy->aaa"
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
