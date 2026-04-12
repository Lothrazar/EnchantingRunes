package com.lothrazar.enchantingrunes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.enchantingrunes.event.RuneEvents;
import com.lothrazar.enchantingrunes.runes.RuneType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ModMainRunes.MODID)
public class ModMainRunes {

  public static final String MODID = "enchantingrunes";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMainRunes(IEventBus bus, ModContainer container) {
    container.registerConfig(ModConfig.Type.COMMON, ConfigRegistryRunes.CONFIG);
    RegistryRunes.BLOCKS.register(bus);
    RegistryRunes.ITEMS.register(bus);
    RegistryRunes.TABS.register(bus);
    bus.addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    NeoForge.EVENT_BUS.register(new RuneEvents());
    RuneType.initWords();
  }
}
