package com.lothrazar.enchantingrunes.event;

import com.lothrazar.enchantingrunes.ModMainRunes;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuneEvents {

  @SubscribeEvent
  public static void onLootLoad(LootTableLoadEvent event) {
    if (event.getName().equals(new ResourceLocation("minecraft", "granite"))) {
      event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(ModMainRunes.MODID, "granite"))).build());
    }
  }
}
