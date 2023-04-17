package com.lothrazar.enchantingrunes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.enchantingrunes.event.RuneEvents;
import com.lothrazar.enchantingrunes.runes.RuneType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMainRunes.MODID)
public class ModMainRunes {

  public static final String MODID = "enchantingrunes";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMainRunes() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    RegistryRunes.BLOCKS.register(eventBus);
    RegistryRunes.ITEMS.register(eventBus);
    //  RuneRegistry.TILE_ENTITIES.register(eventBus);
    new ConfigRegistryRunes();
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
  }

  private void setup(final FMLCommonSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new RuneEvents());
    RuneType.initWords();
  }

  private void setupClient(final FMLClientSetupEvent event) {
    //for client side only setup
  }
}
