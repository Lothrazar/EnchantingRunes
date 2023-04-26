package com.lothrazar.enchantingrunes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.enchantingrunes.event.RuneEvents;
import com.lothrazar.enchantingrunes.runes.RuneType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMainRunes.MODID)
public class ModMainRunes {

  public static final String MODID = "enchantingrunes";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMainRunes() {
    new ConfigRegistryRunes();
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    RegistryRunes.BLOCKS.register(bus);
    RegistryRunes.ITEMS.register(bus);
    bus.addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new RuneEvents());
    RuneType.initWords();
  }
}
