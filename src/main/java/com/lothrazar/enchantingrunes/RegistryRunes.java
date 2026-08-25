package com.lothrazar.enchantingrunes;

import com.lothrazar.enchantingrunes.item.KnifeItem;
import com.lothrazar.enchantingrunes.item.RuneItem;
import com.lothrazar.library.block.BlockLayering;
import com.lothrazar.library.item.BlockItemFlib;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRunes {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModMainRunes.MODID);
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModMainRunes.MODID);
  public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModMainRunes.MODID);

  public static final DeferredHolder<Item, RuneItem> RUNE_A = ITEMS.registerItem("rune_a", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_E = ITEMS.registerItem("rune_e", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_I = ITEMS.registerItem("rune_i", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_O = ITEMS.registerItem("rune_o", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_U = ITEMS.registerItem("rune_u", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_Y = ITEMS.registerItem("rune_y", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_TH = ITEMS.registerItem("rune_th", props -> new RuneItem(props.rarity(Rarity.EPIC)));
  public static final DeferredHolder<Item, RuneItem> RUNE_BLANK = ITEMS.registerItem("rune_blank", props -> new RuneItem(props));
  //stone
  public static final DeferredHolder<Item, Item> BLADE_MASON = ITEMS.registerItem("masonry_blade", props -> new KnifeItem(props));
  public static final DeferredHolder<Block, BlockLayering> STONE_LAYERS = BLOCKS.registerBlock("stone_layer", props -> new BlockLayering(Blocks.STONE, props));
  public static final DeferredHolder<Item, Item> STONE_LAYERS_I = ITEMS.registerItem("stone_layer", props -> new BlockItemFlib(STONE_LAYERS.get(), props.useBlockDescriptionPrefix()));

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("tab", () -> CreativeModeTab.builder()
      .icon(() -> new ItemStack(RUNE_TH.get()))
      .title(Component.translatable("itemGroup." + ModMainRunes.MODID))
      .displayItems((enabledFlags, populator) -> {
        for (var entry : ITEMS.getEntries()) {
          populator.accept(entry.get());
        }
      }).build());
}
